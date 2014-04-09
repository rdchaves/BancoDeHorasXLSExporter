package br.eti.rdchaves.bancodehorasxlsexporter.business.reader;

import java.util.List;

import android.content.Context;

public interface Reader<T> {

	List<T> read(Context context, java.io.Reader reader, String separator) throws Exception;
}
