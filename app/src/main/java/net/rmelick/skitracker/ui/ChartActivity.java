package net.rmelick.skitracker.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import net.rmelick.skitracker.R;

public class ChartActivity extends ActionBarActivity {
  private static final String EXTRA_SKI_DAY_ID = "net.rmelick.skitracker.ski_day_id";

  public static Intent getIntent(Context packageContext, long skiDayId) {
    Intent i = new Intent(packageContext, ChartActivity.class);
    i.putExtra(EXTRA_SKI_DAY_ID, skiDayId);
    return i;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chart);
    if (savedInstanceState == null) {
      long skiDayId = getIntent().getLongExtra(EXTRA_SKI_DAY_ID, -1);
      getSupportFragmentManager().beginTransaction()
          .add(R.id.chart_container, ChartFragment.newInstance(skiDayId))
          .commit();
    }
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_chart, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
