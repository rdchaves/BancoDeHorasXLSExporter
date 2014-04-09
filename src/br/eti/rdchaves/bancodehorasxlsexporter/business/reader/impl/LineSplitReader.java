package br.eti.rdchaves.bancodehorasxlsexporter.business.reader.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import android.content.Context;
import br.eti.rdchaves.bancodehorasxlsexporter.R;
import br.eti.rdchaves.bancodehorasxlsexporter.business.exception.CSVReadFailException;
import br.eti.rdchaves.bancodehorasxlsexporter.business.reader.Reader;

public class LineSplitReader implements Reader<Date> {

	private static final String FORMATO_DATA = "dd/MM/yyyy HH:mm:ss";
	
	@Override
	public List<Date> read(Context context, java.io.Reader reader, String separator) throws CSVReadFailException {
		List<Date> dates = new ArrayList<Date>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DATA, Locale.getDefault());
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			LineIterator it = IOUtils.lineIterator(reader);
			while(it.hasNext()) {
				String[] line = it.nextLine().split(separator);
				for (int i = 0; i < line.length; i++) {
					if (i < (line.length - 1)) {
						try {
							dates.add(sdf.parse(line[i]));
						} catch (ParseException e) {
							break;
						}
					}
				}
			}
			if (dates.size() == 0) {
				throw new CSVReadFailException(R.string.message_empty_file_error);
			}
		} catch (CSVReadFailException e) {
			throw e;
		} catch (Exception e) {
			throw new CSVReadFailException(R.string.message_read_file_error, e);
		} finally {
			IOUtils.closeQuietly(reader);
		}
		return dates;
	}
}
