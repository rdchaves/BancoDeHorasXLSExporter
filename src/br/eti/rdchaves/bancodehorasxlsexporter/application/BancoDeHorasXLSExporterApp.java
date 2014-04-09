package br.eti.rdchaves.bancodehorasxlsexporter.application;

import android.app.Application;
import android.content.Context;

public class BancoDeHorasXLSExporterApp extends Application {

	private static Context context;
	
	@Override
	public void onCreate() {
		super.onCreate();
		BancoDeHorasXLSExporterApp.context = getApplicationContext();
	}

	public static Context getContext() {
		return BancoDeHorasXLSExporterApp.context;
	}
	
}
