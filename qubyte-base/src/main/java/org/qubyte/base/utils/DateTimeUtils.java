package org.qubyte.base.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.qubyte.base.constants.AppConstants;
import org.qubyte.base.logger.BaseLogger;
import org.slf4j.Logger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.commons.lang3.StringUtils;


/**
 * 
 * @author Alok kumar
 * 
 */
public class DateTimeUtils {

	private static final Logger LOGGER = BaseLogger.getLogger(DateTimeUtils.class);

	public interface DateFormats {
		// By default public static final
		String DATE_TIME_SLASH_FORMAT = "dd/MM/yyyy HH:mm:ss";
		String DATE_SLASH_FORMAT = "dd/MM/yyyy";
		String DATE_TIME_HIPHEN_FORMAT = "dd-MM-yyyy HH:mm:ss";
		String DATE_HIPHEN_FORMAT = "dd-MM-yyyy";
		String DATE_TIME_FORMAT_FILENAME = "ddMMyyyy_HHmmss";
		String DATE_TIME_HIPHEN_FORMAT_MILISECOND = "yyyy-mm-dd hh:mm:ss.SSS"; // UPTO 0 TO 999 MILISECOND
		String DATE_HIPHEN_MONTH_FORMAT = "dd-MMM-yyyy HH:mm:ss";

		// String DD_MM_YYYY = "dd-MM-yyyy";
		// String DD_MM_YYYY_HH_MM_SS= "dd-MM-yyyy hh:mm:ss";
		// public static final String yyyy_MM_DD_HH_MM_SS = "yyyy-MM-dd hh:mm:ss";
		// public static final String yyyy_MM_DD_HH_MM = "yyyy-MM-dd hh:mm";
		// public static final String TOLL_SMS_DATE_FORMAT = "dd/MM/yy HH:mm";
		// public static final String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
		// public static final String ISO_DATE_TIME_FORMAT_MINUTE =
		// "yyyy-MM-dd'T'hh:mm:ss";
		// public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd'T'hh:mm:ss";
		// public static final String PASS_PAYMENT_STATUS_INITIATED="Initiated";
		// public static final String PASS_PAYMENT_STATUS_COMPLETE="Success";
		String ISO_DATE_TIME_FORMAT_MS = "yyyy-MM-dd'T'HH:mm:ss.SSS";
		String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"; // 03-02-2020T15:47:45.000
		String ISO_RA_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"; // 03-02-2020 15:47:45
		String ISO_DATE_FORMAT = "yyyy-MM-dd";
	}

	public static Timestamp getTimeStampFromISOFormat(String value) throws ParseException {
		String dateFormat = AppConstants.AquirerConstants.ISO_DATE_TIME_FORMAT;
		return getTimeStamp(dateFormat, value);
	}

	public static Timestamp getTimeStamp(String dateFormat, String value) throws ParseException {

		if (StringUtils.isBlank(value)) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);
		Date date = sdf.parse(value);
		Timestamp timestamp = new Timestamp(date.getTime());

		return timestamp;
	}

	public static Timestamp getCurrentTimeStamp() throws ParseException {

		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());

		return timestamp;
	}

	public static int getTimeDiffInHrs(Long currentTime, Long previousTime) {

		Long timeDiff = currentTime - previousTime;
		int dayDiff = (int) (timeDiff / (1000 * 60 * 60));
		return dayDiff;
	}

	public static int getTimeDiffInDay(Long currentTime, Long previousTime) {

		Long timeDiff = currentTime - previousTime;
		int dayDiff = (int) (timeDiff / (1000 * 60 * 60 * 24)) % 365;
		return dayDiff;
	}

	public static int getTimeDiffInMin(Long currentTime, Long previousTime) {

		Long timeDiff = currentTime - previousTime;
		int dayDiff = (int) (timeDiff / (1000 * 60));
		if (dayDiff < 0) {
			dayDiff *= -1;
		}
		return dayDiff;
	}

	public static String getCurrentDateStringInISOFormat() throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.AquirerConstants.ISO_DATE_TIME_FORMAT);
		sdf.setLenient(false);
		String date = sdf.format(new Date());
		return date;
	}

	public static String getAdjustmentDateStringInISOFormat(Long adjustmilliSec) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.AquirerConstants.ISO_DATE_TIME_FORMAT);
		sdf.setLenient(false);
		String date = sdf.format(new Date((new Date().getTime() + adjustmilliSec)));
		return date;
	}

	public static Date getDateFromISOFormatString(String value) throws ParseException {
		Date date = null;
		if (StringUtils.isBlank(value)) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.AquirerConstants.ISO_DATE_TIME_FORMAT);
		sdf.setLenient(false);
		date = sdf.parse(value);

		return date;
	}

	public static Date getDateFromString(String formatOfDate, String value) throws ParseException {

		if (StringUtils.isBlank(value)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formatOfDate);
		sdf.setLenient(false);
		Date date = sdf.parse(value);
		return date;
	}

	public static Date getDateFromString(String value) throws ParseException {

		if (StringUtils.isBlank(value)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.AquirerConstants.DD_MM_YYYY);
		sdf.setLenient(false);
		Date date = sdf.parse(value);
		return date;
	}

	public static String getISOFormatString(Date value) throws ParseException {

		if (value == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.AquirerConstants.ISO_DATE_TIME_FORMAT);
		sdf.setLenient(false);
		String date = sdf.format(value);
		return date;
	}

	public static String getISOFormatString(Timestamp value) throws ParseException {

		if (value == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.AquirerConstants.ISO_DATE_TIME_FORMAT);
		sdf.setLenient(false);
		String date = sdf.format(value);
		return date;
	}

	public static String getDateString(Timestamp value) throws ParseException {

		if (value == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DateFormats.DATE_HIPHEN_FORMAT);
		sdf.setLenient(false);
		String date = sdf.format(value);
		return date;
	}

	public static String getSMSDateString(Date value) throws ParseException {
		String date = "";
		if (value == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.AquirerConstants.TOLL_SMS_DATE_FORMAT);
		sdf.setLenient(false);
		date = sdf.format(value);
		return date;
	}

	public static String getDateString(Date date, String formatOfDate) throws ParseException {

		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formatOfDate);
		sdf.setLenient(false);

		return sdf.format(date);
	}

	public static String getStringFromDate(Date value, String format) throws ParseException {

		if (value == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setLenient(false);
		return sdf.format(value);

	}

	public static Date getDateWithouTimeFromISOFormatString(String value) throws ParseException {
		if (StringUtils.isBlank(value) || value.length() < 10) {
			return null;
		}
		String valueDate = value.substring(0, 10);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		return sdf.parse(valueDate);
	}

	public static Date getNextDay(Date value) {
		if (value == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance(); // gets a calendar using the default time zone and locale.
		calendar.setTime(value);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}

	public static boolean isThisDateValid(String dateToValidate, String dateFromat) {

		if (dateToValidate == null) {
			return false;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);

		try {

			// if not valid, it will throw ParseException
			sdf.parse(dateToValidate);

		} catch (ParseException e) {
			LOGGER.info("Error in isThisDateValid:" + e.getMessage());
			return false;
		}

		return true;
	}

	public static Date getPreviousDay(Date value) {
		if (value == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance(); // gets a calendar using the default time zone and locale.
		calendar.setTime(value);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	public static String getPreviousDay(int pdays) {
		Calendar calendar = Calendar.getInstance(); // gets a calendar using the default time zone and locale.
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -pdays);

		Date dateFromString = calendar.getTime();
		try {
			return getStringFromDate(dateFromString, "dd-MM-yyyy");
		} catch (ParseException e) {
			return "";
		}
	}

	public static String getPreviousDay(String value) {
		try {
			if (value == null) {
				return null;
			}
			Date dateFromString = getDateFromString("dd/MM/yyyy", value);
			Date date = getPreviousDay(dateFromString);
			return getStringFromDate(date, "dd/MM/yyyy");
		} catch (Exception e) {
			return "";
		}
	}

	public static String changeDateFormat(String value, String initialFormat, String finalFormat) {
		try {
			if (value == null) {
				return null;
			}
			Date dateFromString = getDateFromString(initialFormat, value);
			return getStringFromDate(dateFromString, finalFormat);
		} catch (Exception e) {
			return value;
		}
	}

	public static String getISOFormatStringWithMS(Date value) {
		if (value == null) {
			return null;
		}
		try {
			DateTime dateTimeIndia = new DateTime(value);
			DateTime dateTimeUtcGmt = dateTimeIndia.withZone(DateTimeZone.forOffsetHoursMinutes(5, 30));
			// logger.info("dateTimeUtcGmt:::::"+dateTimeUtcGmt);
			return dateTimeUtcGmt.toString();
		} catch (Exception e) {

			try {
				return getISOFormatStringWithTimeZone(value);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			// logger.error("exception occurerd in getISOFormatStringWithMS" +
			// e.getMessage());
		}

		return null;
	}

	public static String getISOFormatStringWithTimeZone(Date value) throws ParseException {

		if (value == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DateFormats.ISO_DATE_TIME_FORMAT_MS);
		sdf.setLenient(false);
		String date = sdf.format(value);
		return date + "+05:30";
	}

	public static String getCurrentDate(String format) throws ParseException {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static Date addMinutesInCurrentDate(int minutes) {

		Calendar calendar = Calendar.getInstance(); // gets a calendar using the default time zone and locale.
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, minutes);
		return calendar.getTime();
	}

	public static boolean isDateFormatValid(String val, String format) {
		boolean isDateValid = true;
		try {
			if (val.length() != format.length()) {
				return false;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.setLenient(false);
			sdf.parse(val);
		} catch (Exception e) {
			LOGGER.error("isDateFormatValid : " + val + " ,format: " + format);
			isDateValid = false;
		}
		return isDateValid;
	}

	public static Date addEODTimeInDate(Date date) {
		if (date == null) {
			LOGGER.info("Date is null");
			return null;
		}
		Calendar ca = new GregorianCalendar();
		ca.setTime(date);
		ca.add(Calendar.HOUR, 23);
		ca.add(Calendar.MINUTE, 59);
		ca.add(Calendar.SECOND, 59);
		return ca.getTime();
	}

	public static Date truncDate(Date dateObj) {
		Calendar cal = Calendar.getInstance(); // locale-specific

		cal.setTime(dateObj);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	public static Date addSecondsInCurrentDate(int seconds) {

		Calendar calendar = Calendar.getInstance(); // gets a calendar using the default time zone and locale.
		calendar.setTime(new Date());
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
	}

	public static Date getEndDateOfMonth(Date date) {
		if (date == null) {
			LOGGER.info("Date is null");
			return null;
		}
		Calendar ca = new GregorianCalendar();
		ca.setTime(date);
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return ca.getTime();
	}

	public static String addMinutesInDateTime(int minutes, Date dt, String dtFormat) {

		SimpleDateFormat sdf = new SimpleDateFormat(dtFormat);
		sdf.setLenient(false);

		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.MINUTE, minutes);
		String newTime = sdf.format(cal.getTime());

		return newTime;
	}

	public static Boolean ifExistBetweenDateRange(String fromDt, String toDt, String timeToCheck, String dtFormat) {
		Boolean isExist = false;
		try {

			SimpleDateFormat sdf = new SimpleDateFormat(dtFormat);
			sdf.setLenient(false);

			Date time1 = sdf.parse(fromDt);
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(time1);

			Date time2 = sdf.parse(toDt);
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(time2);

			Date d = sdf.parse(timeToCheck);
			Calendar calendar3 = Calendar.getInstance();
			calendar3.setTime(d);

			Date x = calendar3.getTime();

			if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {

				isExist = true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		System.out.println(isExist);
		return isExist;
	}

	public static Date setTimeToDate(Date date, int hours, int minutes, int seconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hours);
		calendar.set(Calendar.MINUTE, minutes);
		calendar.set(Calendar.SECOND, seconds);
		return calendar.getTime();
	}

	public static boolean isCurrentDate(Date date) {

		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String dateStr = sdf.format(currentDate);

		try {
			currentDate = sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return currentDate.equals(date);

	}

	public static Date updateTimeToDate(Date date, int hours, int minutes, int seconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hours);
		calendar.add(Calendar.MINUTE, minutes);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
	}

}
