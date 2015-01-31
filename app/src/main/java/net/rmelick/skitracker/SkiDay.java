package net.rmelick.skitracker;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

/**
 * A day of skiing
 *
 * @author rmelick
 */
public class SkiDay {
  private DateTime mStartDate;
  private long mId;

  public SkiDay() {
    mStartDate = new DateTime();
  }

  public DateTime getStartDate() {
    return mStartDate;
  }

  public void setStartDate(DateTime startDate) {
    mStartDate = startDate;
  }

  public long getId() {
    return mId;
  }

  public void setId(long id) {
    mId = id;
  }

  public long getElapsedSeconds() {
    return Seconds.secondsBetween(mStartDate, DateTime.now()).getSeconds();
  }
}
