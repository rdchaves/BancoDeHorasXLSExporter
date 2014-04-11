package br.eti.rdchaves.bancodehorasxlsexporter.control.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import br.eti.rdchaves.bancodehorasxlsexporter.R;
import br.eti.rdchaves.bancodehorasxlsexporter.control.fragment.NavigationDrawerFragment;
import br.eti.rdchaves.bancodehorasxlsexporter.control.fragment.SettingsFragment;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment navigationDrawer;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		navigationDrawer = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
		title = getTitle();

		// Set up the drawer.
		navigationDrawer.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
		
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		switch (position) {
		case 0:
			getFragmentManager().beginTransaction()
					.replace(R.id.container, PlaceholderFragment.newInstance(position + 1)).commit();
			break;
		case 1:
			getFragmentManager().beginTransaction().replace(R.id.container, new SettingsFragment()).commit();
			title = getString(R.string.title_settings);
			break;

		}
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			title = getString(R.string.app_name);
			break;
		case 2:
			title = getString(R.string.title_settings);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(title);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!navigationDrawer.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		int id = item.getItemId();
		Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			TextView textView = (TextView) rootView.findViewById(R.id.section_label);
			textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
		}
	}

}
