package net.rmelick.skitracker.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import net.rmelick.skitracker.R;


public class SkiActivity extends ActionBarActivity {
  public static final String EXTRA_SKI_DAY_ID = "net.rmelick.skitracker.ski_day_id";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ski);
    if (savedInstanceState == null) {
      long existingSkiDayId = getIntent().getLongExtra(EXTRA_SKI_DAY_ID, -1);
      SkiFragment skiFragment = existingSkiDayId == -1 ? new SkiFragment() : SkiFragment.newInstance(existingSkiDayId);
      getSupportFragmentManager().beginTransaction()
          .add(R.id.container, skiFragment)
          .commit();
    }
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_ski, menu);
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
