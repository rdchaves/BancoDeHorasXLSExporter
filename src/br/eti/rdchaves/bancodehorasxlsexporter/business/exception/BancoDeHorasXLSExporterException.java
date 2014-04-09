package br.eti.rdchaves.bancodehorasxlsexporter.business.exception;

import br.eti.rdchaves.bancodehorasxlsexporter.application.BancoDeHorasXLSExporterApp;

public class BancoDeHorasXLSExporterException extends Exception {

	private static final long serialVersionUID = -1746192960217924882L;

	public BancoDeHorasXLSExporterException() {
		super();
	}

	public BancoDeHorasXLSExporterException(Throwable throwable) {
		super(throwable);
	}

	public BancoDeHorasXLSExporterException(int resId, Throwable throwable) {
		super(getMessage(resId), throwable);
	}

	public BancoDeHorasXLSExporterException(int resId) {
		super(getMessage(resId));
	}
	
	private static String getMessage(int resId) {
		return BancoDeHorasXLSExporterApp.getContext().getString(resId);
	}

}