package net.rmelick.skitracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.joda.time.DateTime;

/**
 * Manage the database of ski days and the details of each day
 *
 * @author rmelick
 */
public class SkiDatabaseHelper extends SQLiteOpenHelper {
  private static final String DB_NAME = "ski.sqlite";
  private static final int VERSION = 1;

  // Ski Day table
  private static final String TABLE_SKI_DAY = "skiDay";
  private static final String COLUMN_SKI_START_DATE = "start_date";

  // Location table
  private static final String TABLE_LOCATION = "location";
  private static final String COLUMN_LOCATION_LATITUDE = "latitude";
  private static final String COLUMN_LOCATION_LONGITUDE = "longitude";
  private static final String COLUMN_LOCATION_ALTITUDE = "altitude";
  private static final String COLUMN_LOCATION_TIMESTAMP = "timestamp";
  private static final String COLUMN_LOCATION_PROVIDER = "provider";
  private static final String COLUMN_LOCATION_SKI_DAY_ID = "ski_day_id";

  public SkiDatabaseHelper(Context context) {
    super(context, DB_NAME, null, VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    // Create the skiDay table
    db.execSQL("create table skiDay (_id integer primary key autoincrement, start_date integer)");
    // Create the location table
    db.execSQL("create table location (timestamp integer, latitude real, longitude real, altitude real," +
        " provider varchar(100), ski_day_id integer references skiDay(_id))");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // Handle schema changes when upgrading
  }

  /**
   * @return id of the skiDay
   */
  public long insertSkiDay(SkiDay skiDay) {
    ContentValues cv = new ContentValues();
    cv.put(COLUMN_SKI_START_DATE, skiDay.getStartDate().getMillis());
    return getWritableDatabase().insert(TABLE_SKI_DAY, null, cv);
  }

  public long insertAltitude(long skiDayId, double altitude) {
    ContentValues cv = new ContentValues();
    cv.put(COLUMN_LOCATION_LATITUDE, 0.0);
    cv.put(COLUMN_LOCATION_LONGITUDE, 0.0);
    cv.put(COLUMN_LOCATION_ALTITUDE, altitude);
    cv.put(COLUMN_LOCATION_TIMESTAMP, DateTime.now().getMillis());
    cv.put(COLUMN_LOCATION_PROVIDER, "pressureSensor");
    cv.put(COLUMN_LOCATION_SKI_DAY_ID, skiDayId);
    return getWritableDatabase().insert(TABLE_LOCATION, null, cv);
  }
}
