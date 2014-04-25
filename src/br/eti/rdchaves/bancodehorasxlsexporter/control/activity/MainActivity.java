package br.eti.rdchaves.bancodehorasxlsexporter.control.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import br.eti.rdchaves.bancodehorasxlsexporter.R;
import br.eti.rdchaves.bancodehorasxlsexporter.control.fragment.HelpFragment;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		navigationDrawer = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
		navigationDrawer.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
		
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		int subTitleResId = R.string.empty;
		
		// update the main content by replacing fragments
		switch (position) {
		case 0:
			getFragmentManager().beginTransaction().replace(R.id.container, new SettingsFragment()).commit();
			subTitleResId = R.string.title_settings;
			break;
		case 1:
			getFragmentManager().beginTransaction()
					.replace(R.id.container, new HelpFragment()).commit();
			subTitleResId = R.string.title_help;
			break;
		}
		restoreActionBar(subTitleResId);
	}

	private void restoreActionBar(int subTitleResId) {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setSubtitle(subTitleResId);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		Toast.makeText(getApplicationContext(), "onOptionsItemSelected", Toast.LENGTH_SHORT).show();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
