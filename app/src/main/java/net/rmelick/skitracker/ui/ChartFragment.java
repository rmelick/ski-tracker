package net.rmelick.skitracker.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import net.rmelick.skitracker.AltitudePoint;
import net.rmelick.skitracker.R;
import net.rmelick.skitracker.SkiDay;
import net.rmelick.skitracker.SkiDayManager;

import java.util.ArrayList;
import java.util.Arrays;
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
    graphView.getGridLabelRenderer().setLabelFormatter(new HourLabelFormatter(getActivity(), true, false));
    //graphView.getGridLabelRenderer().setNumHorizontalLabels(5);
    //graphView.getGridLabelRenderer().setNumVerticalLabels(5);
    return rootView;
  }

  private LineGraphSeries<DataPoint> convertAltitudePoints(List<AltitudePoint> altitudePoints) {
    List<DataPoint> dataPoints = new ArrayList<DataPoint>(altitudePoints.size());
    for (AltitudePoint altitudePoint : altitudePoints) {
      dataPoints.add(new DataPoint(altitudePoint.getTime(), altitudePoint.getAltitude()));
    }
    return new LineGraphSeries<DataPoint>(dataPoints.toArray(new DataPoint[dataPoints.size()]));
  }

  private static final DataPoint[] TEST_ARRAY = new DataPoint[]{
      new DataPoint(1422730849477.0, 27.77508326423645), new DataPoint(1422730849490.0, 27.77508326423645), new DataPoint(1422730849673.0, 27.77508326423645), new DataPoint(1422730849684.0, 27.77508326423645), new DataPoint(1422730849693.0, 27.77508326423645), new DataPoint(1422730849868.0, 27.77508326423645), new DataPoint(1422730849883.0, 28.043820435028078), new DataPoint(1422730849893.0, 28.043820435028078), new DataPoint(1422730850063.0, 28.043820435028078), new DataPoint(1422730850073.0, 28.043820435028078), new DataPoint(1422730850084.0, 28.043820435028078), new DataPoint(1422730850258.0, 28.043820435028078), new DataPoint(1422730850270.0, 28.043820435028078), new DataPoint(1422730850280.0, 28.043820435028078), new DataPoint(1422730850453.0, 28.043820435028078), new DataPoint(1422730850464.0, 28.043820435028078), new DataPoint(1422730850475.0, 28.043820435028078), new DataPoint(1422730850648.0, 28.043820435028078), new DataPoint(1422730850658.0, 28.043820435028078), new DataPoint(1422730850666.0, 28.043820435028078), new DataPoint(1422730850843.0, 28.043820435028078), new DataPoint(1422730850855.0, 28.043820435028078), new DataPoint(1422730850865.0, 28.043820435028078), new DataPoint(1422730851038.0, 28.043820435028078), new DataPoint(1422730851048.0, 28.043820435028078), new DataPoint(1422730851057.0, 28.043820435028078), new DataPoint(1422730851234.0, 28.043820435028078), new DataPoint(1422730851248.0, 28.043820435028078), new DataPoint(1422730851257.0, 28.043820435028078), new DataPoint(1422730851429.0, 28.043820435028078), new DataPoint(1422730851441.0, 28.043820435028078), new DataPoint(1422730851451.0, 28.043820435028078), new DataPoint(1422730851624.0, 27.77508326423645), new DataPoint(1422730851635.0, 27.77508326423645), new DataPoint(1422730851645.0, 27.77508326423645), new DataPoint(1422730851819.0, 28.043820435028078), new DataPoint(1422730851831.0, 28.043820435028078), new DataPoint(1422730851841.0, 28.043820435028078), new DataPoint(1422730852014.0, 28.043820435028078), new DataPoint(1422730852025.0, 28.043820435028078), new DataPoint(1422730852034.0, 28.043820435028078), new DataPoint(1422730852216.0, 28.321224528198243), new DataPoint(1422730852228.0, 28.321224528198243), new DataPoint(1422730852239.0, 28.321224528198243), new DataPoint(1422730852408.0, 28.321224528198243), new DataPoint(1422730852432.0, 28.321224528198243), new DataPoint(1422730852455.0, 28.321224528198243), new DataPoint(1422730852602.0, 28.321224528198243), new DataPoint(1422730852618.0, 28.321224528198243), new DataPoint(1422730852627.0, 28.589958570137025), new DataPoint(1422730852796.0, 28.321224528198243), new DataPoint(1422730852810.0, 28.589958570137025), new DataPoint(1422730852829.0, 28.589958570137025), new DataPoint(1422730852992.0, 28.589958570137025), new DataPoint(1422730853012.0, 28.321224528198243), new DataPoint(1422730853031.0, 28.321224528198243), new DataPoint(1422730853186.0, 28.321224528198243), new DataPoint(1422730853202.0, 28.321224528198243), new DataPoint(1422730853217.0, 28.043820435028078), new DataPoint(1422730853384.0, 28.043820435028078), new DataPoint(1422730853408.0, 28.321224528198243), new DataPoint(1422730853428.0, 28.321224528198243), new DataPoint(1422730853576.0, 28.321224528198243), new DataPoint(1422730853585.0, 28.321224528198243), new DataPoint(1422730853594.0, 28.321224528198243), new DataPoint(1422730853772.0, 28.321224528198243), new DataPoint(1422730853792.0, 28.321224528198243), new DataPoint(1422730853812.0, 28.043820435028078), new DataPoint(1422730853966.0, 28.321224528198243), new DataPoint(1422730853976.0, 28.321224528198243), new DataPoint(1422730853991.0, 28.321224528198243), new DataPoint(1422730854163.0, 28.321224528198243), new DataPoint(1422730854187.0, 28.321224528198243), new DataPoint(1422730854207.0, 28.321224528198243), new DataPoint(1422730854357.0, 28.321224528198243), new DataPoint(1422730854366.0, 28.321224528198243), new DataPoint(1422730854375.0, 28.589958570137025), new DataPoint(1422730854553.0, 28.589958570137025), new DataPoint(1422730854576.0, 28.321224528198243), new DataPoint(1422730854596.0, 28.321224528198243), new DataPoint(1422730854747.0, 28.321224528198243), new DataPoint(1422730854757.0, 28.321224528198243), new DataPoint(1422730854766.0, 28.321224528198243), new DataPoint(1422730854944.0, 28.321224528198243), new DataPoint(1422730855054.0, 28.043820435028078), new DataPoint(1422730855063.0, 28.043820435028078), new DataPoint(1422730855138.0, 28.043820435028078), new DataPoint(1422730855147.0, 28.321224528198243), new DataPoint(1422730855157.0, 28.321224528198243), new DataPoint(1422730855333.0, 28.043820435028078), new DataPoint(1422730855343.0, 28.043820435028078), new DataPoint(1422730855366.0, 28.043820435028078), new DataPoint(1422730855531.0, 28.043820435028078), new DataPoint(1422730855557.0, 27.77508326423645), new DataPoint(1422730855576.0, 27.77508326423645), new DataPoint(1422730855723.0, 28.043820435028078), new DataPoint(1422730855732.0, 28.043820435028078), new DataPoint(1422730855741.0, 28.043820435028078), new DataPoint(1422730855921.0, 28.043820435028078), new DataPoint(1422730855945.0, 28.043820435028078), new DataPoint(1422730855973.0, 28.043820435028078), new DataPoint(1422730856113.0, 28.043820435028078), new DataPoint(1422730856122.0, 28.043820435028078), new DataPoint(1422730856132.0, 28.043820435028078), new DataPoint(1422730856308.0, 28.043820435028078), new DataPoint(1422730856322.0, 28.043820435028078), new DataPoint(1422730856336.0, 28.043820435028078), new DataPoint(1422730856503.0, 28.043820435028078), new DataPoint(1422730856513.0, 28.043820435028078), new DataPoint(1422730856547.0, 28.043820435028078), new DataPoint(1422730856699.0, 28.043820435028078), new DataPoint(1422730856718.0, 28.043820435028078), new DataPoint(1422730856731.0, 28.043820435028078), new DataPoint(1422730856898.0, 28.043820435028078), new DataPoint(1422730856921.0, 27.77508326423645), new DataPoint(1422730856941.0, 28.043820435028078), new DataPoint(1422730857089.0, 27.77508326423645), new DataPoint(1422730857099.0, 27.77508326423645), new DataPoint(1422730857107.0, 27.77508326423645), new DataPoint(1422730857287.0, 27.77508326423645), new DataPoint(1422730857310.0, 27.77508326423645), new DataPoint(1422730857339.0, 27.77508326423645), new DataPoint(1422730857479.0, 27.77508326423645), new DataPoint(1422730857488.0, 27.77508326423645), new DataPoint(1422730857497.0, 27.77508326423645), new DataPoint(1422730857676.0, 27.77508326423645), new DataPoint(1422730857699.0, 27.77508326423645), new DataPoint(1422730857719.0, 27.77508326423645), new DataPoint(1422730857869.0, 27.77508326423645), new DataPoint(1422730857878.0, 27.77508326423645), new DataPoint(1422730857887.0, 27.77508326423645), new DataPoint(1422730858069.0, 27.77508326423645), new DataPoint(1422730858099.0, 27.77508326423645), new DataPoint(1422730858118.0, 27.77508326423645), new DataPoint(1422730858260.0, 28.043820435028078), new DataPoint(1422730858269.0, 28.043820435028078), new DataPoint(1422730858278.0, 28.043820435028078), new DataPoint(1422730858477.0, 27.77508326423645), new DataPoint(1422730858499.0, 28.043820435028078), new DataPoint(1422730858508.0, 27.77508326423645), new DataPoint(1422730858650.0, 27.77508326423645), new DataPoint(1422730858659.0, 27.77508326423645), new DataPoint(1422730858668.0, 27.77508326423645), new DataPoint(1422730858846.0, 27.77508326423645), new DataPoint(1422730858859.0, 28.043820435028078), new DataPoint(1422730858881.0, 27.77508326423645), new DataPoint(1422730859040.0, 28.043820435028078), new DataPoint(1422730859049.0, 28.043820435028078), new DataPoint(1422730859058.0, 28.043820435028078), new DataPoint(1422730859236.0, 28.043820435028078), new DataPoint(1422730859258.0, 28.043820435028078), new DataPoint(1422730859278.0, 28.043820435028078), new DataPoint(1422730859429.0, 28.043820435028078), new DataPoint(1422730859446.0, 28.043820435028078), new DataPoint(1422730859455.0, 28.043820435028078), new DataPoint(1422730859627.0, 28.043820435028078), new DataPoint(1422730859643.0, 28.043820435028078), new DataPoint(1422730859660.0, 27.77508326423645), new DataPoint(1422730859820.0, 27.77508326423645), new DataPoint(1422730859829.0, 28.043820435028078), new DataPoint(1422730859838.0, 28.043820435028078), new DataPoint(1422730860018.0, 27.77508326423645), new DataPoint(1422730860053.0, 27.77508326423645), new DataPoint(1422730860076.0, 27.77508326423645), new DataPoint(1422730860210.0, 27.77508326423645), new DataPoint(1422730860220.0, 27.77508326423645), new DataPoint(1422730860229.0, 27.77508326423645), new DataPoint(1422730860406.0, 27.77508326423645), new DataPoint(1422730860418.0, 27.77508326423645), new DataPoint(1422730860436.0, 27.77508326423645), new DataPoint(1422730860600.0, 27.77508326423645), new DataPoint(1422730860610.0, 28.043820435028078), new DataPoint(1422730860619.0, 27.77508326423645), new DataPoint(1422730860797.0, 28.043820435028078), new DataPoint(1422730860819.0, 28.043820435028078), new DataPoint(1422730860846.0, 27.77508326423645), new DataPoint(1422730860992.0, 27.77508326423645), new DataPoint(1422730861002.0, 27.77508326423645), new DataPoint(1422730861011.0, 27.77508326423645), new DataPoint(1422730861188.0, 28.043820435028078), new DataPoint(1422730861212.0, 28.043820435028078), new DataPoint(1422730861233.0, 28.043820435028078), new DataPoint(1422730861402.0, 28.043820435028078), new DataPoint(1422730861424.0, 28.043820435028078), new DataPoint(1422730861433.0, 28.043820435028078), new DataPoint(1422730861579.0, 28.043820435028078), new DataPoint(1422730861606.0, 28.043820435028078), new DataPoint(1422730861626.0, 28.043820435028078), new DataPoint(1422730861771.0, 28.043820435028078), new DataPoint(1422730861781.0, 28.043820435028078), new DataPoint(1422730861798.0, 28.043820435028078), new DataPoint(1422730861968.0, 27.77508326423645), new DataPoint(1422730861983.0, 27.77508326423645), new DataPoint(1422730862007.0, 27.77508326423645), new DataPoint(1422730862161.0, 27.77508326423645), new DataPoint(1422730862171.0, 28.043820435028078), new DataPoint(1422730862180.0, 27.77508326423645), new DataPoint(1422732715027.0, 28.589958570137025), new DataPoint(1422732715046.0, 28.589958570137025), new DataPoint(1422732715056.0, 28.589958570137025), new DataPoint(1422732715221.0, 28.86736266330719), new DataPoint(1422732715232.0, 28.589958570137025), new DataPoint(1422732715242.0, 28.589958570137025), new DataPoint(1422732715416.0, 28.589958570137025), new DataPoint(1422732715425.0, 28.589958570137025), new DataPoint(1422732715434.0, 28.589958570137025), new DataPoint(1422732715611.0, 28.589958570137025), new DataPoint(1422732715622.0, 28.589958570137025), new DataPoint(1422732715631.0, 28.589958570137025), new DataPoint(1422732715805.0, 28.589958570137025), new DataPoint(1422732715816.0, 28.589958570137025), new DataPoint(1422732715827.0, 28.589958570137025), new DataPoint(1422732716000.0, 28.589958570137025), new DataPoint(1422732716014.0, 28.589958570137025), new DataPoint(1422732716024.0, 28.321224528198243), new DataPoint(1422732716194.0, 28.321224528198243), new DataPoint(1422732716206.0, 28.321224528198243), new DataPoint(1422732716216.0, 28.589958570137025), new DataPoint(1422732716390.0, 28.589958570137025), new DataPoint(1422732716403.0, 28.589958570137025), new DataPoint(1422732716412.0, 28.589958570137025), new DataPoint(1422732716584.0, 28.321224528198243), new DataPoint(1422732716600.0, 28.321224528198243), new DataPoint(1422732716608.0, 28.589958570137025), new DataPoint(1422732716779.0, 28.589958570137025), new DataPoint(1422732716790.0, 28.589958570137025), new DataPoint(1422732716801.0, 28.589958570137025), new DataPoint(1422732716974.0, 28.321224528198243), new DataPoint(1422732716985.0, 28.589958570137025), new DataPoint(1422732716995.0, 28.589958570137025), new DataPoint(1422732717168.0, 28.589958570137025), new DataPoint(1422732717179.0, 28.589958570137025), new DataPoint(1422732717196.0, 28.589958570137025), new DataPoint(1422732717362.0, 28.589958570137025), new DataPoint(1422732717372.0, 28.86736266330719), new DataPoint(1422732717380.0, 28.86736266330719), new DataPoint(1422732717557.0, 28.86736266330719), new DataPoint(1422732717567.0, 28.589958570137025), new DataPoint(1422732717576.0, 28.86736266330719), new DataPoint(1422732717755.0, 28.86736266330719), new DataPoint(1422732717767.0, 28.589958570137025), new DataPoint(1422732717779.0, 28.589958570137025), new DataPoint(1422732717947.0, 28.86736266330719), new DataPoint(1422732717962.0, 28.86736266330719), new DataPoint(1422732717972.0, 28.86736266330719), new DataPoint(1422732718144.0, 28.86736266330719), new DataPoint(1422732718166.0, 28.86736266330719), new DataPoint(1422732718177.0, 28.86736266330719), new DataPoint(1422732718341.0, 28.86736266330719), new DataPoint(1422732718363.0, 28.589958570137025), new DataPoint(1422732718385.0, 28.86736266330719), new DataPoint(1422732718531.0, 28.86736266330719), new DataPoint(1422732718541.0, 28.86736266330719), new DataPoint(1422732718549.0, 28.86736266330719), new DataPoint(1422732718726.0, 28.86736266330719), new DataPoint(1422732718745.0, 28.86736266330719), new DataPoint(1422732718764.0, 28.86736266330719), new DataPoint(1422732718920.0, 28.86736266330719), new DataPoint(1422732718929.0, 28.86736266330719), new DataPoint(1422732718937.0, 29.136099834098815), new DataPoint(1422732719146.0, 29.136099834098815), new DataPoint(1422732719162.0, 29.136099834098815), new DataPoint(1422732719173.0, 29.136099834098815), new DataPoint(1422732719312.0, 29.41350392726898), new DataPoint(1422732719343.0, 29.41350392726898), new DataPoint(1422732719364.0, 29.41350392726898), new DataPoint(1422732719504.0, 29.41350392726898), new DataPoint(1422732719515.0, 29.41350392726898), new DataPoint(1422732719523.0, 29.41350392726898), new DataPoint(1422732719703.0, 29.41350392726898), new DataPoint(1422732719727.0, 29.41350392726898), new DataPoint(1422732719746.0, 29.41350392726898), new DataPoint(1422732719893.0, 29.682237969207762), new DataPoint(1422732719902.0, 29.682237969207762), new DataPoint(1422732719912.0, 29.682237969207762), new DataPoint(1422732720088.0, 29.682237969207762), new DataPoint(1422732720105.0, 29.682237969207762), new DataPoint(1422732720121.0, 29.682237969207762), new DataPoint(1422732720282.0, 29.682237969207762), new DataPoint(1422732720292.0, 29.682237969207762), new DataPoint(1422732720301.0, 29.95964206237793), new DataPoint(1422732720481.0, 29.95964206237793), new DataPoint(1422732720505.0, 29.682237969207762), new DataPoint(1422732720524.0, 29.682237969207762), new DataPoint(1422732720672.0, 29.95964206237793), new DataPoint(1422732720681.0, 29.95964206237793), new DataPoint(1422732720698.0, 29.95964206237793), new DataPoint(1422732720867.0, 29.95964206237793), new DataPoint(1422732720889.0, 29.95964206237793), new DataPoint(1422732720923.0, 29.95964206237793), new DataPoint(1422732721061.0, 29.682237969207762), new DataPoint(1422732721070.0, 29.682237969207762), new DataPoint(1422732721079.0, 29.682237969207762), new DataPoint(1422732721256.0, 29.682237969207762), new DataPoint(1422732721272.0, 29.41350392726898), new DataPoint(1422732721292.0, 29.41350392726898), new DataPoint(1422732721449.0, 29.41350392726898), new DataPoint(1422732721459.0, 29.41350392726898), new DataPoint(1422732721468.0, 29.41350392726898), new DataPoint(1422732721646.0, 29.136099834098815), new DataPoint(1422732721695.0, 29.41350392726898), new DataPoint(1422732721716.0, 29.41350392726898), new DataPoint(1422732721839.0, 29.41350392726898), new DataPoint(1422732721849.0, 29.41350392726898), new DataPoint(1422732721858.0, 29.41350392726898), new DataPoint(1422732722034.0, 29.41350392726898), new DataPoint(1422732722059.0, 29.41350392726898), new DataPoint(1422732722079.0, 29.41350392726898), new DataPoint(1422732722228.0, 29.41350392726898), new DataPoint(1422732722238.0, 29.682237969207762), new DataPoint(1422732722247.0, 29.41350392726898), new DataPoint(1422732722425.0, 29.682237969207762), new DataPoint(1422732722443.0, 29.41350392726898), new DataPoint(1422732722463.0, 29.41350392726898), new DataPoint(1422732722617.0, 29.41350392726898), new DataPoint(1422732722634.0, 29.41350392726898), new DataPoint(1422732722642.0, 29.41350392726898), new DataPoint(1422732722812.0, 29.41350392726898), new DataPoint(1422732722822.0, 29.41350392726898), new DataPoint(1422732722831.0, 29.41350392726898)
  };

  private static final DataPoint[] TEST_ARRAY_2 = new DataPoint[]{
      new DataPoint(15.0, 27.77508326423645), new DataPoint(16.0, 27.77508326423645), new DataPoint(17.0, 27.77508326423645), new DataPoint(18.0, 27.77508326423645), new DataPoint(19.0, 27.77508326423645), new DataPoint(20.0, 27.77508326423646)
  };

  private PointsGraphSeries<DataPoint> getTestData() {
    DataPoint[] points = new DataPoint[10000];
    for (int i = 0; i < 10000; i++) {
      points[i] = new DataPoint(i, i);
    }

    PointsGraphSeries<DataPoint> series = new PointsGraphSeries<>(TEST_ARRAY);
    return series;
  }
}
