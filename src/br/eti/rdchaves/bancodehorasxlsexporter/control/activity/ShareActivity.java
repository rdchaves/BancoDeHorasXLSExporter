package br.eti.rdchaves.bancodehorasxlsexporter.control.activity;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;
import br.eti.rdchaves.bancodehorasxlsexporter.R;
import br.eti.rdchaves.bancodehorasxlsexporter.business.converter.impl.ExcelConverter;
import br.eti.rdchaves.bancodehorasxlsexporter.business.exception.CSVReadFailException;
import br.eti.rdchaves.bancodehorasxlsexporter.business.reader.impl.LineSplitReader;
import br.eti.rdchaves.bancodehorasxlsexporter.control.fragment.SettingsFragment;

public class ShareActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Make us non-modal, so that others can receive touch events...
		getWindow().setFlags(LayoutParams.FLAG_NOT_TOUCH_MODAL, LayoutParams.FLAG_NOT_TOUCH_MODAL);

		// ...but notify us that it happened.
		getWindow().setFlags(LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

		setContentView(R.layout.activity_share);

		convertAndShare();

		finish();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// If we've received a touch notification that the user has touched
		// outside the app, does nothing.
		if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
			return true;
		}

		// Delegate everything else to Activity.
		return super.onTouchEvent(event);
	}

	private void convertAndShare() {
		// Get intent, action and MIME type
		Intent sourceIntent = getIntent();
		String action = sourceIntent.getAction();
		String type = sourceIntent.getType();
		Uri sourceUri = sourceIntent.getParcelableExtra(Intent.EXTRA_STREAM);

		Context context = getApplicationContext();
		if (sourceUri != null) {
			String csvMimeType = getString(R.string.csv_mime_type);
			if (Intent.ACTION_SEND.equals(action) && (type != null) && csvMimeType.equals(type)) {
				try {

					File targetFile = convertFile(sourceUri);

					String excelMimeType = getString(R.string.excel_mime_type);
					Uri targetUri = FileProvider.getUriForFile(context, getString(R.string.provider_authorities),
							targetFile);
					Intent targetIntent = new Intent(getTargetAction());
					if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(
							SettingsFragment.KEY_SEND_BOTH_FILES, false)) {
						ArrayList<Parcelable> files = new ArrayList<Parcelable>();
						files.add(sourceUri);
						files.add(targetUri);
						targetIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
						targetIntent.setType(csvMimeType + "," + excelMimeType);
					} else {
						targetIntent.putExtra(Intent.EXTRA_STREAM, targetUri);
						targetIntent.setType(excelMimeType);
					}
					targetIntent.putExtra(Intent.EXTRA_SUBJECT, targetFile.getName());
					targetIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
					startActivity(targetIntent);
				} catch (CSVReadFailException e) {
					Log.e(getClass().getName(), e.getMessage(), e);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			} else {
				Toast.makeText(context, getString(R.string.message_incorrect_mime_type, type), Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			Toast.makeText(context, getString(R.string.message_no_csv_on_intent), Toast.LENGTH_LONG).show();
		}
	}

	private String getTargetAction() {
		
		String action = null;
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String sendOperation = getString(R.string.value_settings_operation_send);
		String operation = preferences.getString(SettingsFragment.KEY_OPERATION, sendOperation);
		if (sendOperation.equals(operation)) {
			if (preferences.getBoolean(SettingsFragment.KEY_SEND_BOTH_FILES, false)) {
				action = Intent.ACTION_SEND_MULTIPLE;
			} else {
				action = Intent.ACTION_SEND;
			}
		} else if (getString(R.string.value_settings_operation_edit).equals(operation)){
			action = Intent.ACTION_EDIT;
		} else {
			action = Intent.ACTION_VIEW;
		}
		return action;
	}

	private File convertFile(Uri uri) throws FileNotFoundException, CSVReadFailException, Exception {
		Reader reader = null;
		File targetFile = null;
		Context context = getApplicationContext();
		try {

			ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
			FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
			reader = new FileReader(fileDescriptor);
			List<Date> checkpoints = new LineSplitReader().read(context, reader, ",");
			File dir = getDir();
			dir.mkdirs();
			targetFile = new File(dir, uri.getLastPathSegment().replace(".csv", ".xls"));
			new ExcelConverter<Date>().convert(targetFile, checkpoints);
		} catch (CSVReadFailException e) {
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
			throw e;
		} finally {
			IOUtils.closeQuietly(reader);
		}
		return targetFile;
	}

	private File getDir() throws XmlPullParserException, IOException {

		return new File(getCacheDir(), getString(R.string.provider_shared_directory));
	}

}
