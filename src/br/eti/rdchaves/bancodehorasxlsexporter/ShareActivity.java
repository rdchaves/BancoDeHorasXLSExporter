package br.eti.rdchaves.bancodehorasxlsexporter;

import java.io.File;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.List;

import rodrigo.chaves.hoursbankconverter.reader.impl.LineSplitReader;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

public class ShareActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Get intent, action and MIME type
		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();
		Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);

		if (uri != null) {
			if (Intent.ACTION_SEND.equals(action) && (type != null) && "text/csv".equals(type)) {
				try {
					File csv = new File(URI.create(uri.getPath()));
					List<Date> checkpoints = new LineSplitReader().read(new StringReader(getCsvContent()), ",");
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
					throw new RuntimeException(e);
				}
			} else {
				Toast.makeText(getApplicationContext(), "Other files: " + type, Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(getApplicationContext(), "Arquivo CSV não recebido", Toast.LENGTH_LONG).show();
		}
	}

	private String getCsvContent() {
		// TODO Auto-generated method stub
		return null;
	}
}
