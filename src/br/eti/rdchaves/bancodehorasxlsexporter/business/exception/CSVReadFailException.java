package br.eti.rdchaves.bancodehorasxlsexporter.business.exception;


public class CSVReadFailException extends BancoDeHorasXLSExporterException {

	private static final long serialVersionUID = 3783585288071324575L;

	public CSVReadFailException() {
		super();
	}

	public CSVReadFailException(int resId, Throwable throwable) {
		super(resId, throwable);
	}

	public CSVReadFailException(int resId) {
		super(resId);
	}

	public CSVReadFailException(Throwable throwable) {
		super(throwable);
	}

}
