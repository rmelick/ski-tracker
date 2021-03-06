package net.rmelick.skitracker.ui;


import android.app.Fragment;
import android.os.Bundle;
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
  private static final String ARG_SKI_DAY_ID = "SKI_DAY_ID";

  private Button mStartButton;
  private Button mStopButton;
  private Button mViewChartButton;
  private TextView mAltitudeTextView;
  private TextView mElapsedTimeTextView;
  private double mCurrentAltitude;
  private AltitudeManager mAltitudeManager;
  private SkiDayManager mSkiDayManager;
  private SkiDay mSkiDay;

  public static SkiFragment newInstance(long skiDayId) {
    Bundle args = new Bundle();
    args.putLong(ARG_SKI_DAY_ID, skiDayId);
    SkiFragment skiFragment = new SkiFragment();
    skiFragment.setArguments(args);
    return skiFragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    mAltitudeManager = AltitudeManager.get(getActivity());
    mAltitudeManager.registerCallback(new AltitudeManager.NewAltitudeCallback() {
      @Override
      public void newAltitude(double altitude) {
        if (!mSkiDayManager.isTrackingSkiDay(mSkiDay)) {
          return;
        }
        mCurrentAltitude = altitude;
        updateUI();
      }
    });
    mSkiDayManager = SkiDayManager.get(getActivity());

    // if a ski day id was passed in, query the db
    Bundle args = getArguments();
    if (args != null && args.getLong(ARG_SKI_DAY_ID, -1) != -1) {
      mSkiDay = mSkiDayManager.getSkiDay(args.getLong(ARG_SKI_DAY_ID, -1));
      mCurrentAltitude = mSkiDayManager.getLastAltitudeForSkiDay(mSkiDay.getId());
    }
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
        if (mSkiDay == null) {
          mSkiDay = mSkiDayManager.startNewSkiDay();
        } else {
          mSkiDayManager.startTrackingSkiDay(mSkiDay);
        }
        updateUI();
      }
    });

    mStopButton = (Button) rootView.findViewById(R.id.stop_skiButton);
    mStopButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mSkiDayManager.stopTrackingSkiDay();
        updateUI();
      }
    });

    mViewChartButton = (Button) rootView.findViewById(R.id.view_chartButton);
    mViewChartButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mSkiDay != null) {
          startActivity(ChartActivity.getIntent(getActivity(), mSkiDay.getId()));
        }
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
    boolean currentlyTracking = mSkiDayManager.isTrackingSkiDay(mSkiDay);
    mStartButton.setEnabled(!currentlyTracking);
    mStopButton.setEnabled(currentlyTracking);
  }
}
