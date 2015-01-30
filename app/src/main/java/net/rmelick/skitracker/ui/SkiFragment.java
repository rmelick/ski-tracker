package net.rmelick.skitracker.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.rmelick.skitracker.AltitudeManager;
import net.rmelick.skitracker.R;
import net.rmelick.skitracker.SkiDay;
import net.rmelick.skitracker.SkiDayManager;

/**
 * @author rmelick
 */
public class SkiFragment extends Fragment {
  private Button mStartButton;
  private Button mStopButton;
  private TextView mAltitudeTextView;
  private TextView mElapsedTimeTextView;
  private double mCurrentAltitude;
  private AltitudeManager mAltitudeManager;
  private SkiDayManager mSkiDayManager;
  private SkiDay mSkiDay;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    mAltitudeManager = AltitudeManager.get(getActivity());
    mAltitudeManager.registerCallback(new AltitudeManager.NewAltitudeCallback() {
      @Override
      public void newAltitude(double altitude) {
        mCurrentAltitude = altitude;
        updateUI();
      }
    });
    mSkiDayManager = SkiDayManager.get(getActivity());
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_ski, container, false);

    mAltitudeTextView = (TextView) rootView.findViewById(R.id.current_altitudeTextView);
    mElapsedTimeTextView = (TextView) rootView.findViewById(R.id.elapsed_timeTextView);

    mStartButton = (Button) rootView.findViewById(R.id.start_skiButton);
    mStartButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mSkiDay = mSkiDayManager.startNewSkiDay();
      }
    });

    mStopButton = (Button) rootView.findViewById(R.id.stop_skiButton);
    mStopButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mSkiDayManager.stopTrackingSkiDay();
      }
    });

    updateUI();

    return rootView;
  }

  private void updateUI() {
    mAltitudeTextView.setText(String.format("%.5g", mCurrentAltitude));
    if (mSkiDay != null) {
      mElapsedTimeTextView.setText(DateUtils.formatElapsedTime(mSkiDay.getElapsedSeconds()));
    }
  }
}
