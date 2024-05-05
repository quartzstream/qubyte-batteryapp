package org.qubyte.base.constants;

/**
 * 
 * @author Alok kumar
 * 
 */
public interface AppConstants {

	public interface SecurityConstants {

		String TOKEN_PREFIX = "Bearer ";
		String HEADER_STRING = "Authorization";
		long EXPIRATION_TIME = 86400000l; // 1 days
		String SECRET = "SecretKeyToGenJWTs";
	}

	public interface Types {

		String INVALID_TOKEN_ERROR = "Invalid Token";
		String INVALID_INPUT_ERROR = "Invalid Request";
		String SIGN_IN_ERROR = "Invalid Sign In";

	}

	public interface Common {
		String TOKEN_HEADER = "Authorization";
		String ORDERBYCOLSTRING = "orderbyCol";
	}

	public interface AquirerConstants {

		String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
		String DD_MM_YYYY = "dd-MM-yyyy";
		String DD_MM_YYYY_HH_MM_SS = "dd-MM-yyyy hh:mm:ss";
		String yyyy_MM_DD_HH_MM_SS = "yyyy-MM-dd hh:mm:ss";
		String yyyy_MM_DD_HH_MM = "yyyy-MM-dd hh:mm";
		String TOLL_SMS_DATE_FORMAT = "dd/MM/yy HH:mm";
	}
}
