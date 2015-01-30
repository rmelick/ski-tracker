package net.rmelick.skitracker;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rmelick
 */
public class AltitudeManager implements SensorEventListener {
  private static AltitudeManager sAltitudeManager;
  private static final double FEET_PER_METER = 3.28084;

  private Context mAppContext;
  private SensorManager mSensorManager;
  private Sensor mPressureSensor;
  private boolean mIsRunning;
  private final List<NewAltitudeCallback> mCallbacks;

  private AltitudeManager(Context appContext) {
    mAppContext = appContext;
    mSensorManager = (SensorManager) appContext.getSystemService(Context.SENSOR_SERVICE);
    mPressureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
    mCallbacks = new ArrayList<NewAltitudeCallback>(0);
    mIsRunning = false;
  }

  public static AltitudeManager get(Context c) {
    if (sAltitudeManager == null) {
      sAltitudeManager = new AltitudeManager(c.getApplicationContext());
    }
    return sAltitudeManager;
  }

  public void startPressureUpdates() {
    mSensorManager.registerListener(this, mPressureSensor, SensorManager.SENSOR_DELAY_UI);
    mIsRunning = true;

  }

  public void stopPressureUpdates() {
    mSensorManager.unregisterListener(this);
    mIsRunning = false;
  }

  public boolean isRunning() {
    return mIsRunning;
  }

  public void registerCallback(NewAltitudeCallback callback) {
    mCallbacks.add(callback);
  }

  @Override
  public void onSensorChanged(SensorEvent event) {
    float millibars = event.values[0];
    float altitudeInMeters = SensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE, millibars);
    double altitudeInFeet = altitudeInMeters * FEET_PER_METER;
    for (NewAltitudeCallback callback : mCallbacks) {
      callback.newAltitude(altitudeInFeet);
    }

  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
    // don't care
  }

  public static interface NewAltitudeCallback {
    public void newAltitude(double altitude);
  }
}
