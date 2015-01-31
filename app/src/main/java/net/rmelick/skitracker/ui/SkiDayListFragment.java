package net.rmelick.skitracker.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import net.rmelick.skitracker.R;
import net.rmelick.skitracker.SkiDatabaseHelper;
import net.rmelick.skitracker.SkiDay;
import net.rmelick.skitracker.SkiDayManager;


/**
 * @author rmelick
 */
public class SkiDayListFragment extends ListFragment {
  private static final int REQUEST_NEW_SKI_DAY = 0;
  private SkiDatabaseHelper.SkiDayCursor mCursor;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    // query list of saved ski days
    mCursor = SkiDayManager.get(getActivity()).querySkiDays();
    // adapter points at the cursor
    SkiDayCursorAdapter adapter = new SkiDayCursorAdapter(getActivity(), mCursor);
    setListAdapter(adapter);
  }

  @Override
  public void onDestroy() {
    mCursor.close();
    super.onDestroy();
  }

  /** We don't need this since our ListActivity inflates the menu already
  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_ski_day_list, menu);
  }**/

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_item_new_ski_day:
        Intent i = new Intent(getActivity(), SkiActivity.class);
        startActivityForResult(i, REQUEST_NEW_SKI_DAY);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (REQUEST_NEW_SKI_DAY == requestCode) {
      mCursor.requery();
      ((SkiDayCursorAdapter)getListAdapter()).notifyDataSetChanged();
    }
  }

  private static class SkiDayCursorAdapter extends CursorAdapter {
    private final SkiDatabaseHelper.SkiDayCursor mSkiDayCursor;

    public SkiDayCursorAdapter(Context context, SkiDatabaseHelper.SkiDayCursor cursor) {
      super(context, cursor, 0);
      mSkiDayCursor = cursor;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
      // layout inflater gets a row view
      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      return inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
      // Get ski day for current row
      SkiDay skiDay = mSkiDayCursor.getSkiDay();

      // Set up text views (start date)
      TextView startDateTextView = (TextView)view;
      String cellText = context.getString(R.string.cell_text, skiDay.getStartDate());
      startDateTextView.setText(cellText);
    }
  }
}
