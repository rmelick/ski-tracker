package net.rmelick.skitracker.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import net.rmelick.skitracker.AltitudePoint;
import net.rmelick.skitracker.R;
import net.rmelick.skitracker.SkiDay;
import net.rmelick.skitracker.SkiDayManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rmelick
 */
public class ChartFragment extends Fragment {
  private static final String ARG_SKI_DAY_ID = "SKI_DAY_ID";

  private SkiDayManager mSkiDayManager;
  private SkiDay mSkiDay;
  private LineGraphSeries<DataPoint> mData;

  public static ChartFragment newInstance(long skiDayId) {
    Bundle args = new Bundle();
    args.putLong(ARG_SKI_DAY_ID, skiDayId);
    ChartFragment fragment = new ChartFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);

    mSkiDayManager = SkiDayManager.get(getActivity());

    Bundle args = getArguments();
    if (args != null) {
      long skiDayId = args.getLong(ARG_SKI_DAY_ID, -1);
      if (skiDayId != -1) {
        mSkiDay = mSkiDayManager.getSkiDay(skiDayId);
        List<AltitudePoint> altitudes = mSkiDayManager.getAltitudeHistory(skiDayId);
        mData = convertAltitudePoints(altitudes);
      }
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_chart, container, false);
    GraphView graphView = (GraphView) rootView.findViewById(R.id.graph);
    graphView.addSeries(mData);
    return rootView;
  }

  private LineGraphSeries<DataPoint> convertAltitudePoints(List<AltitudePoint> altitudePoints) {
    List<DataPoint> dataPoints = new ArrayList<DataPoint>(altitudePoints.size());
    for (AltitudePoint altitudePoint : altitudePoints) {
      dataPoints.add(new DataPoint(altitudePoint.getTime(), altitudePoint.getAltitude()));
    }
    return new LineGraphSeries<DataPoint>(dataPoints.toArray(new DataPoint[dataPoints.size()]));
  }
}
