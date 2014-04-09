package br.eti.rdchaves.bancodehorasxlsexporter.business.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;

public class Month {

	private Date value;
	private List<Day> days = new ArrayList<Day>();

	public Month(Date value) {
		this.value = DateUtils.truncate(value, Calendar.MONTH);
	}
	
	public Date getValue() {
		return value;
	}

	public void setValue(Date value) {
		this.value = value;
	}

	public List<Day> getDays() {
		return days;
	}

	public void setDays(List<Day> days) {
		this.days = days;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return value.equals(((Month) obj).getValue());
	}

	@Override
	public String toString() {
		return new SimpleDateFormat("MM-yyyy", Locale.getDefault()).format(this.value);
	}
}