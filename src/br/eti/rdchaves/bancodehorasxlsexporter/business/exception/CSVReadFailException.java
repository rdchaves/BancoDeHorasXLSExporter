package br.eti.rdchaves.bancodehorasxlsexporter.business.exception;

public class CSVReadFailException extends Exception {

	private static final long serialVersionUID = 3783585288071324575L;

	public CSVReadFailException() {
		super();
	}

	public CSVReadFailException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public CSVReadFailException(String detailMessage) {
		super(detailMessage);
	}

	public CSVReadFailException(Throwable throwable) {
		super(throwable);
	}

}
