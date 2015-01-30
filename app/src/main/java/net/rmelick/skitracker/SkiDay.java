package net.rmelick.skitracker;

import org.joda.time.DateTime;

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

}
