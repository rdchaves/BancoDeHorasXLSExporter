package br.eti.rdchaves.bancodehorasxlsexporter.business.reader;

import java.util.List;

public interface Reader<T> {

	List<T> read(java.io.Reader reader, String separator) throws Exception;
}
