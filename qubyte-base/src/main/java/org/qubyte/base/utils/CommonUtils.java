package org.qubyte.base.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.qubyte.base.logger.BaseLogger;
import org.slf4j.Logger;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Alok kumar
 * 
 */
@SuppressWarnings("restriction")
public class CommonUtils {

	private static Logger LOGGER = BaseLogger.getLogger(CommonUtils.class);

	private CommonUtils() {
	}

	public static boolean isBlank(String val) {
		if (val == null || val.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	public static Date updateDate(Date dt, Integer hh, Integer mm, Integer ss) {
		Date tempDate = dt;

		if (tempDate != null) {
			if (hh != null) {
				tempDate = DateUtils.setHours(tempDate, hh);
			}
			if (mm != null) {
				tempDate = DateUtils.setMinutes(tempDate, mm);
			}
			if (ss != null) {
				tempDate = DateUtils.setSeconds(tempDate, ss);
			}
		}
		return tempDate;

	}

	public static Date updateSSInDate(Date dt, Integer ss) {
		Date tempDate = dt;

		if (tempDate != null) {
			if (ss != null) {
				tempDate = DateUtils.setSeconds(tempDate, ss);
			}
		}
		return tempDate;

	}

	public static String formatDate(Date inputDate, String pattern) {
		String formattedDate = "";
		if (inputDate != null && pattern != null) {
			formattedDate = DateFormatUtils.format(inputDate, pattern);
		}
		return formattedDate;
	}

	public static Date parseDate(String date, String format) {

		Date dt = null;

		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			dt = formatter.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dt;
	}

	public static int compareOnlyDate(Date fileDate, Date currentDate) {

		Date fileDateOnly = updateDate(fileDate, 0, 0, 0);
		Date currentDateOnly = updateDate(currentDate, 0, 0, 0);

		return fileDateOnly.compareTo(currentDateOnly);
	}

	public static String[] getAuthData(String authString) {
		String decodedAuth = "";
		// Header is in the format "Basic 5tyc0uiDat4"
		// We need to extract data before decoding it back to original string
		String[] authParts = authString.split("\\s+");
		String authInfo = authParts[1];
		// Decode the data back to original string
		byte[] bytes = null;
		try {
			bytes = new BASE64Decoder().decodeBuffer(authInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		decodedAuth = new String(bytes);
		LOGGER.debug(decodedAuth);

		/**
		 * here you include your logic to validate user authentication. it can be using
		 * ldap, or token exchange mechanism or your custom authentication mechanism.
		 */

		return decodedAuth.split(":");
	}

	public static BigDecimal getBigDecimal(String value) {
		LOGGER.debug("BigDecimal: " + value);
		BigDecimal result = null;
		try {
			result = new BigDecimal(value);
		} catch (Exception ex) {
			LOGGER.debug("Exception in converting value to BigDecimal: " + ex.getMessage());
		}

		return result;
	}

	public static BigDecimal getBigDecimal(Object value) {
		LOGGER.debug("BigDecimal: " + value);
		BigDecimal result = null;
		try {
			result = new BigDecimal((String) value);
		} catch (Exception ex) {
			LOGGER.debug("Exception in converting value to BigDecimal: " + ex.getMessage());
			try {
				result = new BigDecimal(value.toString());
			} catch (Exception e) {
				LOGGER.debug("Again Exception in converting value to BigDecimal: " + ex.getMessage());
			}
		}

		return result;
	}

	public static BigDecimal getBigDecimalDefault(String value, BigDecimal defaultVal) {
		LOGGER.debug("BigDecimal: " + value);
		BigDecimal result = null;
		try {
			result = new BigDecimal(value);
		} catch (Exception ex) {
			LOGGER.debug("Exception in converting value to BigDecimal: " + ex.getMessage());
			return defaultVal;
		}

		return result;
	}

	public static Long getLong(String value) {
		// LOGGER.debug("Long: "+value);
		Long result = null;
		try {
			result = Long.parseLong(value);
		} catch (Exception ex) {
			LOGGER.debug("Exception in converting value to Long: " + ex.getMessage());
		}

		return result;
	}

	public static Long getLong(Object value) {
		LOGGER.debug("Long: " + value);
		Long result = null;
		try {
			result = Long.parseLong((String) value);
		} catch (Exception ex) {
			LOGGER.debug("Exception in converting value to Long: " + ex.getMessage());
			try {
				Double d = Double.parseDouble(value.toString());
				result = d.longValue();
			} catch (Exception e) {
				LOGGER.debug("Again Exception in converting value to Long: " + ex.getMessage());
			}
		}

		return result;
	}

	public static Date getDate(String value, String format) {
		LOGGER.debug("Date: " + value);
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date result = null;
		try {
			result = dateFormat.parse(value);
		} catch (Exception ex) {
			LOGGER.debug("Exception in converting to Date: " + ex.getMessage());
		}

		return result;
	}

	public static BigDecimal getBigDecimal(String value, BigDecimal zero) {
		LOGGER.debug("BigDecimal: " + value);
		BigDecimal result = zero;
		try {
			result = new BigDecimal(value);
		} catch (Exception ex) {
			LOGGER.debug("Exception in converting value to BigDecimal: " + ex.getMessage());
		}

		return result;
	}

	public static boolean checkRegex(String regex, String text) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);

		return matcher.matches();
	}

	public static String twoDecimal(String value) {
		try {
			DecimalFormat df = new DecimalFormat("#.##");
			return df.format(Double.parseDouble(value));
		} catch (Exception e) {
			LOGGER.debug("Exception in converting value to  2 decimal " + e.getMessage());
		}
		return value;
	}

	public static String fourDecimal(String value) {
		try {
			DecimalFormat df = new DecimalFormat("#.####");
			return df.format(Double.parseDouble(value));
		} catch (Exception e) {
			LOGGER.debug("Exception in converting value to  4 decimal " + e.getMessage());
		}
		return value;
	}

	public static String convertBigDecimalToString(BigDecimal bigDecimal, String defaultVal) {
		return bigDecimal == null ? defaultVal : bigDecimal.toString();
	}

	public static String convertToFirstLetterCap(String capitalString) {
		try {
			if (StringUtils.isNotBlank(capitalString)) {
				capitalString = (capitalString.substring(0, 1).toUpperCase()
						+ capitalString.substring(1).toLowerCase());
			}
		} catch (Exception ex) {
			LOGGER.debug("Exception in convertToFirstLetterCap " + ex.getMessage());
		}
		return capitalString;
	}

	public static String getString(Object value, String fallBack) {
		if (value == null) {
			return fallBack;
		}

		return String.valueOf(value);

	}

	public static String getString(Object value) {
		if (value instanceof Double) {
			value = getLong(value);
		}

		return getString(value, null);
	}

	public static String getString(Double value) {
		return getString(value, null);
	}

	public static String getString(Long value) {
		return getString(value, null);
	}

	public static Date getDateFromString(String dateHiphenMonthFormat, String autoRenewDt) {
		try {
			Double dateD = Double.parseDouble(autoRenewDt);
			return DateUtil.getJavaDate(dateD);
		} catch (Exception e) {
			try {
				return DateTimeUtils.getDateFromString(dateHiphenMonthFormat, autoRenewDt);
			} catch (Exception ex) {
				LOGGER.error("Error in parsing date");
			}
		}

		return null;
	}

	public static Date getDateFromDouble(String dateHiphenMonthFormat, Double autoRenewDt) {
		try {
			return DateUtil.getJavaDate(autoRenewDt);
		} catch (Exception e) {
			try {
				return DateTimeUtils.getDateFromString(dateHiphenMonthFormat, autoRenewDt.toString());
			} catch (Exception ex) {
				LOGGER.error("Error in parsing date");
			}
		}

		return null;
	}

	public static boolean isBlank(Object obj) {
		try {
			if (obj == null) {
				return true;
			}
			return StringUtils.isBlank((String) obj);
		} catch (Exception ex) {
			return false;
		}
	}

	public static int getInteger(String errorCode, int defaultVal) {
		try {
			return Integer.parseInt(errorCode);
		} catch (Exception e) {
			LOGGER.info("Error in parsing to Integer: " + errorCode);
			return defaultVal;
		}
	}

	public static Date getDate(Object date, String format) {
		if (date instanceof Double) {
			return getDateFromDouble(format, (Double) date);
		}

		return getDateFromString(format, getString(date));
	}

	@SuppressWarnings("rawtypes")
	public static boolean checkBlankRow(List list) {
		if (list == null) {
			return false;
		}
		for (int i = 0; i < list.size(); i++) {
			String data = CommonUtils.getString(list.get(i));
			if (data != null && !data.trim().isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public static String removeSingleQuote(String val) {
		if (val == null || val.length() < 1) {
			return val;
		}
		if (val.startsWith("'")) {
			return val.substring(1);
		}

		return val;
	}

	public static String removeChassisSpecialCharacters(String val) {
		if (val == null || val.length() < 1) {
			return val;
		}

		// val = val.replaceAll("[/*~ ]", "");
		val = val.replaceAll("[ ]", "");

		return val;
	}

	public static String removeChassisSpecialCharactersForVrn(String val) {
		if (val == null || val.length() < 1) {
			return val;
		}

		val = val.replaceAll("[/*~ ]", "");
		// val = val.replaceAll("[ ]", "");

		return val;
	}

	public static void main(String args[]) {
		System.out.print(removeChassisSpecialCharacters("12avAcx///////    c/c*j~U~j&JH#"));
	}

	public static Date parseAndFormatDate(Date date, String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String dateStr = sdf.format(date);
		Date dt = null;
		try {
			dt = sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dt;
	}

	public static boolean isCurrentDate(Date date) {

		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		String dateStr = sdf.format(currentDate);

		try {
			currentDate = sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return currentDate.equals(date);

	}

	public static boolean isNotBlank(String value) {

		return !isBlank(value);
	}

	public static Integer getInteger(String object) {
		try {
			return Integer.parseInt(object);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date addDaysInCurrentDay(int dormancyDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, dormancyDays);
		return calendar.getTime();
	}

	public static String getStringFromObject(Object request) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(request);
	}

	@SuppressWarnings("hiding")
	public static <T> T getObjectFromString(String response, Class<T> className) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return (T) objectMapper.readValue(response, className);
	}

	public static String getReqIdByMaxLength(int maxLength) {
		UUID uuid = UUID.randomUUID();
		String uuidAsString = uuid.toString();
		if (uuidAsString.length() > maxLength) {
			uuidAsString = uuidAsString.substring(uuidAsString.length() - maxLength);
		}
		uuidAsString = uuidAsString.replaceAll("-", "");
		return uuidAsString;
	}

	public static String getMasked(String value) {
		if (value != null && value.length() > 4) {
			String subString = value.substring(value.length() - 4);
			return "XXXX" + subString;
		}

		return value;
	}
}
