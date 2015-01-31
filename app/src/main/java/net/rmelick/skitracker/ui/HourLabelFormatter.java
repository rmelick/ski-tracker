package net.rmelick.skitracker.ui;

import android.content.Context;
import android.text.format.DateFormat;

import com.jjoe64.graphview.DefaultLabelFormatter;

import org.joda.time.DateTime;


/**
 * @author rmelick
 */
public class HourLabelFormatter extends DefaultLabelFormatter {
  private final Context mContext;
  private final boolean mFormatX;
  private final boolean mFormatY;

  public HourLabelFormatter(Context context, boolean formatX, boolean formatY) {
    mContext = context;
    mFormatX = formatX;
    mFormatY = formatY;
  }

  @Override
  public String formatLabel(double value, boolean isValueX) {
    if (isValueX && mFormatX) {
      return formatHours(value);
    } else if (!isValueX && mFormatY) {
      return formatHours(value);
    } else {
      return super.formatLabel(value, isValueX);
    }
  }

  private String formatHours(double value) {
    return DateFormat.getTimeFormat(mContext).format(new DateTime((long) value).toDate());
  }
}
