package net.rmelick.skitracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

  public SkiDatabaseHelper(Context context) {
    super(context, DB_NAME, null, VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    // Create the skiDay table
    db.execSQL("create table skiDay (_id integer primary key autoincrement, start_date integer)");
    // Create the location table
    db.execSQL("create table location (timestamp integer, latitude real, longitude real, altitude real," +
        " provider varchar(100), ru_id integer references run(_id))");
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
}
