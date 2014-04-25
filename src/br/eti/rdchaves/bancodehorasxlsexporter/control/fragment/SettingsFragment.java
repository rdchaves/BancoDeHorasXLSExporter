package br.eti.rdchaves.bancodehorasxlsexporter.control.fragment;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import br.eti.rdchaves.bancodehorasxlsexporter.R;

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

	public static final String KEY_OPERATION = "operation";
	public static final String KEY_SEND_BOTH_FILES = "send_both_files";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		Preference preference = findPreference(key);
		if (KEY_OPERATION.equals(key)) {
			String operation = sharedPreferences.getString(key, "teste");
			if (getString(R.string.value_settings_operation_edit).equals(operation)) {
				preference.setSummary(R.string.summary_operation_edit);
			} else if (getString(R.string.value_settings_operation_send).equals(operation)) {
				preference.setSummary(R.string.summary_operation_send);
			} else {
				preference.setSummary(R.string.summary_operation_view);
			}
		} else if (KEY_SEND_BOTH_FILES.equals(key)) {
			if (sharedPreferences.getBoolean(key, false)) {
				preference.setSummary(R.string.summary_send_both_file);
			} else {
				preference.setSummary(R.string.summary_send_only_xls);
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
		
		final ListPreference operation = (ListPreference) findPreference(KEY_OPERATION);
		operation.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				CheckBoxPreference sendBoth = (CheckBoxPreference) findPreference(KEY_SEND_BOTH_FILES);
				final String val = newValue.toString();
				
				String sendOperationValue = getString(R.string.value_settings_operation_send);
				if (sendOperationValue.equals(val)) {
					sendBoth.setEnabled(true);
				} else {
					sendBoth.setChecked(false);
					sendBoth.setEnabled(false);
				}
				return true;
			}
		});
	}

	@Override
	public void onPause() {
		getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
		findPreference(KEY_OPERATION).setOnPreferenceChangeListener(null);
		super.onPause();
	}
	
}
