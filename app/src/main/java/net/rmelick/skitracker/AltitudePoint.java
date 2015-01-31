package net.rmelick.skitracker;

/**
 * @author rmelick
 */
public class AltitudePoint {
  private final double mAltitude;
  private final long mTime;

  public AltitudePoint(double altitude, long time) {
    mAltitude = altitude;
    mTime = time;
  }

  public double getAltitude() {
    return mAltitude;
  }

  public long getTime() {
    return mTime;
  }
}
