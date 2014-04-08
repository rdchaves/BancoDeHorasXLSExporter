package br.eti.rdchaves.bancodehorasxlsexporter.control.activity;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.widget.Toast;
import br.eti.rdchaves.bancodehorasxlsexporter.R;
import br.eti.rdchaves.bancodehorasxlsexporter.business.converter.impl.ExcelConverter;
import br.eti.rdchaves.bancodehorasxlsexporter.business.exception.CSVReadFailException;
import br.eti.rdchaves.bancodehorasxlsexporter.business.reader.impl.LineSplitReader;

public class ShareActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		finish();

		// Get intent, action and MIME type
		Intent sourceIntent = getIntent();
		String action = sourceIntent.getAction();
		String type = sourceIntent.getType();
		Uri uri = sourceIntent.getParcelableExtra(Intent.EXTRA_STREAM);

		if (uri != null) {
			if (Intent.ACTION_SEND.equals(action) && (type != null) && getString(R.string.csv_mime_type).equals(type)) {
				try {

					File targetFile = convertFile(uri);
					targetFile.deleteOnExit();

					Intent targetIntent = new Intent(Intent.ACTION_SEND);
					targetIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(targetFile));
					targetIntent.putExtra(Intent.EXTRA_SUBJECT, "[Banco de Horas] ");
					targetIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
					targetIntent.setType(getString(R.string.excel_mime_type));
					startActivity(Intent.createChooser(targetIntent, getString(R.string.message_intent_chooser)));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			} else {
				Toast.makeText(getApplicationContext(), getString(R.string.message_incorrect_mime_type, type),
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(getApplicationContext(), getString(R.string.message_no_csv_on_intent), Toast.LENGTH_LONG)
					.show();
		}
	}

	private File convertFile(Uri uri) throws FileNotFoundException, Exception {
		Reader reader = null;
		File targetFile = null;
		try {
			ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
			FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
			reader = new FileReader(fileDescriptor);
			List<Date> checkpoints = new LineSplitReader().read(reader, ",");
			targetFile = new File(Environment.getExternalStorageDirectory(), uri.getLastPathSegment().replace(".csv",
					".xls"));
			new ExcelConverter<Date>().convert(targetFile, checkpoints);
		} catch (CSVReadFailException e) {
			Toast.makeText(getApplicationContext(), getString(R.string.message_read_file_error), Toast.LENGTH_LONG)
					.show();
			throw e;
		} finally {
			IOUtils.closeQuietly(reader);
		}
		return targetFile;
	}

	/** Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}
}
