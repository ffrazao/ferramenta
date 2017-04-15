package br.com.ferramenta;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.Interval;
import org.joda.time.Period;

public class UtilitarioData {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

	private static final SimpleDateFormat DATE_FORMAT_JAVASCRIPT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	private static volatile UtilitarioData instance;

	private static final SimpleDateFormat MILISEGUNDOS_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

	private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");

	public static final UtilitarioData getInstance() {
		if (instance == null) {
			synchronized (UtilitarioData.class) {
				instance = new UtilitarioData();
			}
		}
		return instance;
	}

	private UtilitarioData() {
	}

	public synchronized String formataData(Calendar date) {
		if (date == null) {
			return null;
		}
		return DATE_FORMAT.format(date.getTime());
	}

	public synchronized String formataData(Date date) {
		if (date == null) {
			return null;
		}
		return DATE_FORMAT.format(date);
	}

	public synchronized Calendar formataData(String date) throws ParseException {
		if (date == null || date.trim().length() == 0) {
			return null;
		}
		Calendar result = Calendar.getInstance();
		result.setTime(DATE_FORMAT.parse(date));
		return result;
	}

	public synchronized String formataDataHora(Calendar date) {
		if (date == null) {
			return null;
		}
		return DATE_TIME_FORMAT.format(date.getTime());
	}

	public synchronized Calendar formataDataHora(String date) throws ParseException {
		if (date == null || date.trim().length() == 0) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DATE_TIME_FORMAT.parse(date));
		return calendar;
	}

	public synchronized Calendar formataDataJavascript(String date) throws ParseException {
		if (date == null || date.trim().length() == 0) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DATE_FORMAT_JAVASCRIPT.parse(date));
		return calendar;
	}

	public synchronized String formataMilisegundos(Calendar date) {
		if (date == null) {
			return null;
		}
		return MILISEGUNDOS_FORMAT.format(date.getTime());
	}

	public synchronized Calendar formataMilisegundos(String date) throws ParseException {
		if (date == null || date.trim().length() == 0) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(MILISEGUNDOS_FORMAT.parse(date));
		return calendar;
	}

	public synchronized String formataTimestamp(Calendar date) {
		if (date == null) {
			return null;
		}
		return TIMESTAMP_FORMAT.format(date.getTime());
	}

	public synchronized Calendar formataTimestamp(String date) throws ParseException {
		if (date == null || date.trim().length() == 0) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(TIMESTAMP_FORMAT.parse(date));
		return calendar;
	}

	public int qtdAnosEntre(Calendar inicio, Calendar fim) {
		if (inicio == null || fim == null || inicio.getTimeInMillis() >= fim.getTimeInMillis()) {
			return 0;
		}
		Interval interval = new Interval(inicio.getTimeInMillis(), fim.getTimeInMillis());
		Period period = interval.toPeriod();
		return period.getYears();
	}

	public synchronized Calendar sqlTimestampToCalendar(Timestamp date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		return calendar;
	}

	public Calendar ajustaInicioDia(Calendar data) {
		if (data == null) {
			return null;
		}
		Calendar result = new GregorianCalendar(data.get(Calendar.YEAR), data.get(Calendar.MONTH), data.get(Calendar.DATE));
		return result;
	}

	public Calendar ajustaFinalDia(Calendar data) {
		if (data == null) {
			return null;
		}
		Calendar result = new GregorianCalendar(data.get(Calendar.YEAR), data.get(Calendar.MONTH), data.get(Calendar.DATE), 23, 59, 59);
		return result;
	}

	public Object stringParaData(Object string) throws ParseException {
		if ((string == null) || !(string instanceof String)) {
			return string;
		}
		Object result = null;
		try {
			result = formataMilisegundos((String) string);
		} catch (ParseException e) {
			try {
				result = formataDataJavascript((String) string);
			} catch (ParseException e1) {
				try {
					result = formataTimestamp((String) string);
				} catch (ParseException e2) {
					try {
						result = formataMilisegundos((String) string);
					} catch (ParseException e3) {
						try {
							result = formataDataHora((String) string);
						} catch (ParseException e4) {
							try {
								result = formataData((String) string);
							} catch (ParseException e5) {
								result = string;
							}
						}
					}
				}
			}
		}
		return result;
	}

}