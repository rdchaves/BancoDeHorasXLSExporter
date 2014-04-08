package br.eti.rdchaves.bancodehorasxlsexporter.business.reader.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import br.eti.rdchaves.bancodehorasxlsexporter.business.exception.CSVReadFailException;
import br.eti.rdchaves.bancodehorasxlsexporter.business.reader.Reader;

public class LineSplitReader implements Reader<Date> {

	private static final String FORMATO_DATA = "dd/MM/yyyy HH:mm:ss";
	
	@Override
	public List<Date> read(java.io.Reader reader, String separator) throws Exception {
		List<Date> dates = new ArrayList<Date>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DATA);
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
		} catch (Exception e) {
			throw new CSVReadFailException("Fail to read file: wrong format", e);
		} finally {
			IOUtils.closeQuietly(reader);
		}
		return dates;
	}
}
