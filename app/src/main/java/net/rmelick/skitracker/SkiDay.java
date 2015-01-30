package net.rmelick.skitracker;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

/**
 * A day of skiing
 *
 * @author rmelick
 */
public class SkiDay {
  private final DateTime mStartDate;

  public SkiDay() {
    mStartDate = new DateTime();
  }

  public DateTime getStartDate() {
    return mStartDate;
  }

  public long getElapsedSeconds() {
    return Seconds.secondsBetween(mStartDate, DateTime.now()).getSeconds();
  }
}
