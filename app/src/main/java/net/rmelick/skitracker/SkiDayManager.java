package net.rmelick.skitracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * @author rmelick
 */
public class SkiDayManager {
  private static final String TAG = SkiDayManager.class.getName();
  private static final String PREFS_FILE = "skiDays";
  private static final String PREF_CURRENT_SKI_DAY_ID = "SkiDayManager.currentSkiDayId";

  private static SkiDayManager sSkiDayManager;

  private final Context mAppContext;
  private final SkiDatabaseHelper mDatabaseHelper;
  private final SharedPreferences mSharedPreferences;
  private final AltitudeManager mAltitudeManager;

  private long mCurrentSkiDayId;

  private SkiDayManager(Context appContext) {
    mAppContext = appContext;
    mDatabaseHelper = new SkiDatabaseHelper(mAppContext);
    mSharedPreferences = mAppContext.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
    mCurrentSkiDayId = mSharedPreferences.getLong(PREF_CURRENT_SKI_DAY_ID, -1);
    mAltitudeManager = AltitudeManager.get(mAppContext);
    mAltitudeManager.registerCallback(new AltitudeManager.NewAltitudeCallback() {
      @Override
      public void newAltitude(double altitude) {
        insertAltitude(altitude);
      }
    });
  }

  public static SkiDayManager get(Context c) {
    if (sSkiDayManager == null) {
      sSkiDayManager = new SkiDayManager(c.getApplicationContext());
    }
    return sSkiDayManager;
  }

  public SkiDay startNewSkiDay() {
    // Create a new day in the db
    SkiDay skiDay = createSkiDay();
    // Start tracking it
    startTrackingSkiDay(skiDay);
    return skiDay;
  }

  public void startTrackingSkiDay(SkiDay skiDay) {
    // store the id in preferences to handle app getting killed, etc.
    mCurrentSkiDayId = skiDay.getId();
    mSharedPreferences.edit().putLong(PREF_CURRENT_SKI_DAY_ID, mCurrentSkiDayId).commit();
    // Start location updates
    mAltitudeManager.startPressureUpdates();
  }

  public void stopTrackingSkiDay() {
    mAltitudeManager.stopPressureUpdates();
    mCurrentSkiDayId = -1;
    mSharedPreferences.edit().remove(PREF_CURRENT_SKI_DAY_ID).commit();
  }

  public SkiDatabaseHelper.SkiDayCursor querySkiDays() {
    return mDatabaseHelper.querySkiDays();
  }

  public SkiDay getSkiDay(long id) {
    SkiDay skiDay = null;
    SkiDatabaseHelper.SkiDayCursor cursor = mDatabaseHelper.querySkiDay(id);
    cursor.moveToFirst();
    if (!cursor.isAfterLast()) {
      skiDay = cursor.getSkiDay();
    }
    cursor.close();
    return skiDay;
  }

  public double getLastAltitudeForSkiDay(long skiDayId) {
    double altitude = 0;
    SkiDatabaseHelper.AltitudeCursor cursor = mDatabaseHelper.queryLastAltitudeForSkiDay(skiDayId);
    cursor.moveToFirst();
    if (!cursor.isAfterLast()) {
      altitude = cursor.getAltitude();
    }
    cursor.close();
    return altitude;
  }

  public boolean isTrackingSkiDay(SkiDay skiDay) {
    return mAltitudeManager.isRunning() && skiDay != null && skiDay.getId() == mCurrentSkiDayId;
  }

  private SkiDay createSkiDay() {
    SkiDay skiDay = new SkiDay();
    skiDay.setId(mDatabaseHelper.insertSkiDay(skiDay));
    return skiDay;
  }

  private void insertAltitude(double altitude) {
    if (mCurrentSkiDayId != -1) {
      mDatabaseHelper.insertAltitude(mCurrentSkiDayId, altitude);
    } else {
      Log.e(TAG, "Altitude received with no ski day in progress; ignoring.");
    }
  }
}
