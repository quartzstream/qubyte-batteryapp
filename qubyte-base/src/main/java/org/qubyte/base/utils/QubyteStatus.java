package org.qubyte.base.utils;

import org.springframework.lang.Nullable;

public enum QubyteStatus {

	STATUS_SUCCESS("success"),
	STATUS_FAILD("faild"),
	CONTINUE(100, "Continue"),
	SWITCHING_PROTOCOLS(101, "Switching Protocols"),
	PROCESSING(102, "Processing"),
	CHECKPOINT(103, "Checkpoint"),
	//OK(200, "OK"),
	SUCCESS(200, "Success"),
	CREATED(201, "Created"),
	ACCEPTED(202, "Accepted"),
	NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),
	NO_CONTENT(204, "No Content"),
	RESET_CONTENT(205, "Reset Content"),
	PARTIAL_CONTENT(206, "Partial Content"),
	MULTI_STATUS(207, "Multi-Status"),
	ALREADY_REPORTED(208, "Already Reported"),
	IM_USED(226, "IM Used"),
	MULTIPLE_CHOICES(300, "Multiple Choices"),
	MOVED_PERMANENTLY(301, "Moved Permanently"),
	FOUND(302, "Found"),
	@Deprecated
	MOVED_TEMPORARILY(302, "Moved Temporarily"),
	SEE_OTHER(303, "See Other"),
	NOT_MODIFIED(304, "Not Modified"),
	@Deprecated
	USE_PROXY(305, "Use Proxy"),
	TEMPORARY_REDIRECT(307, "Temporary Redirect"),
	PERMANENT_REDIRECT(308, "Permanent Redirect"),
	BAD_REQUEST(400, "Bad Request"),
	UNAUTHORIZED(401, "Unauthorized"),
	PAYMENT_REQUIRED(402, "Payment Required"),
	FORBIDDEN(403, "Forbidden"),
	NOT_FOUND(404, "Not Found"),
	METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
	NOT_ACCEPTABLE(406, "Not Acceptable"),
	PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
	REQUEST_TIMEOUT(408, "Request Timeout"),
	CONFLICT(409, "Conflict"),
	GONE(410, "Gone"),
	LENGTH_REQUIRED(411, "Length Required"),
	PRECONDITION_FAILED(412, "Precondition Failed"),
	PAYLOAD_TOO_LARGE(413, "Payload Too Large"),
	@Deprecated
	REQUEST_ENTITY_TOO_LARGE(413, "Request Entity Too Large"),
	URI_TOO_LONG(414, "URI Too Long"),
	@Deprecated
	REQUEST_URI_TOO_LONG(414, "Request-URI Too Long"),
	UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
	REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested range not satisfiable"),
	EXPECTATION_FAILED(417, "Expectation Failed"),
	I_AM_A_TEAPOT(418, "I'm a teapot"),
	@Deprecated
	INSUFFICIENT_SPACE_ON_RESOURCE(419, "Insufficient Space On Resource"),
	@Deprecated
	METHOD_FAILURE(420, "Method Failure"),
	@Deprecated
	DESTINATION_LOCKED(421, "Destination Locked"),
	UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),
	LOCKED(423, "Locked"),
	FAILED_DEPENDENCY(424, "Failed Dependency"),
	TOO_EARLY(425, "Too Early"),
	UPGRADE_REQUIRED(426, "Upgrade Required"),
	PRECONDITION_REQUIRED(428, "Precondition Required"),
	TOO_MANY_REQUESTS(429, "Too Many Requests"),
	REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large"),
	UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons"),
	INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
	NOT_IMPLEMENTED(501, "Not Implemented"),
	BAD_GATEWAY(502, "Bad Gateway"),
	SERVICE_UNAVAILABLE(503, "Service Unavailable"),
	GATEWAY_TIMEOUT(504, "Gateway Timeout"),
	HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version not supported"),
	VARIANT_ALSO_NEGOTIATES(506, "Variant Also Negotiates"),
	INSUFFICIENT_STORAGE(507, "Insufficient Storage"),
	LOOP_DETECTED(508, "Loop Detected"),
	BANDWIDTH_LIMIT_EXCEEDED(509, "Bandwidth Limit Exceeded"),
	NOT_EXTENDED(510, "Not Extended"),
	NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required"),
	
	INVALID_LOGIN(701,"Invalid Login Credentials"),
	SIGN_IN_ERROR(702,"Invalid sign in"),
	INVALID_TOKEN_ERROR(703,"Invalid token"),
	USER_DOES_NOT_EXISTS_ERROR(704,"User doesn't exist"),
	INVALID_CREDENTIALS_ERROR(705,"Invalid Credentials! Please try again"),
	USER_ACCOUNT_EXPIRED(706,"Your account has been expired! Please contact administrator"),
	USER_ACCOUNT_LOCKED(707,"Your account has been locked! Please contact administrator"),
	USER_CREDENTIALS_EXPIRED(708,"Your credentials has been expired!! Please contact administrator"),
	USER_DISABLED(709,"User is disabled! Please contact administrator"),
	USER_CREDENTIALS_CHANGE_REQUIRED(710,"You need to change your credentials"),
	USER_NO_ROLE(711,"User does not have role"),
	INTERNAL_ERROR(712,"Internal error occurred"),
	INVALID_DATETIME(713,"Invalid date time in request."),
	EXIST_MOBILE(714,"Mobile number already exist."),
	USER_TYPE_ALLOW_SA(715,"User type only use SA."),
	USER_TYPE_ALLOW_VA(715,"User type only use VA."),
	USER_TYPE_ALLOW_SE(715,"User type only use SE."),
	RECORD_NOT_FOUND(700,"Record not found."),
	MEDNATROY_FIELD(716,"Please inter mednatory field."),
	SEARCH_COLUMN(717,"Only Search column use is: "),
	DIFFERENT_USER_TYPE(718,"You are not authorized for this API."),
	INVALID_USERCODE(719,"Invalid usercode in request."),
	INVALID_NEW_PASSWORD(720,"Invalid new password format in request."),
	INVALID_PASSWORD(720,"New password does not match. Enter new password again here.");
	
	
	private int code;
	private String message;


	QubyteStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	QubyteStatus(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static String getMessageFromCode(int code){
		 if (code+"" != null) {
		      for (QubyteStatus b : QubyteStatus.values()) {
		        if (code == b.code) {
		          return b.message;
		        }
		      }
		    }
		return null;
	}

	/**
	 * Return the HTTP status series of this status code.
	 * @see Series
	 */
	public Series series() {
		return Series.valueOf(this);
	}

	/**
	 * Whether this status code is in the HTTP series
	 * .
	 * This is a shortcut for checking the value of {@link #series()}.
	 * @since 4.0
	 * @see #series()
	 */
	public boolean is1xxInformational() {
		return (series() == Series.INFORMATIONAL);
	}

	/**
	 * Whether this status code is in the HTTP series
	 * .
	 * This is a shortcut for checking the value of {@link #series()}.
	 * @since 4.0
	 * @see #series()
	 */
	public boolean is2xxSuccessful() {
		return (series() == Series.SUCCESSFUL);
	}

	/**
	 * Whether this status code is in the HTTP series
	 * .
	 * This is a shortcut for checking the value of {@link #series()}.
	 * @since 4.0
	 * @see #series()
	 */
	public boolean is3xxRedirection() {
		return (series() == Series.REDIRECTION);
	}

	/**
	 * Whether this status code is in the HTTP series
	 * .
	 * This is a shortcut for checking the value of {@link #series()}.
	 * @since 4.0
	 * @see #series()
	 */
	public boolean is4xxClientError() {
		return (series() == Series.CLIENT_ERROR);
	}

	/**
	 * Whether this status code is in the HTTP series
	 * .
	 * This is a shortcut for checking the value of {@link #series()}.
	 * @since 4.0
	 * @see #series()
	 */
	public boolean is5xxServerError() {
		return (series() == Series.SERVER_ERROR);
	}

	/**
	 * Whether this status code is in the HTTP series
	 *  or
	 * .
	 * This is a shortcut for checking the value of {@link #series()}.
	 * @since 5.0
	 * @see #is4xxClientError()
	 * @see #is5xxServerError()
	 */
	public boolean isError() {
		return (is4xxClientError() || is5xxServerError());
	}

	/**
	 * Return a string representation of this status code.
	 */
	@Override
	public String toString() {
		return this.code + " " + name();
	}


	/**
	 * Return the enum constant of this type with the specified numeric value.
	 * @param code the numeric value of the enum to be returned
	 * @return the enum constant with the specified numeric value
	 * @throws IllegalArgumentException if this enum has no constant for the specified numeric value
	 */
	public static QubyteStatus valueOf(int code) {
		QubyteStatus status = resolve(code);
		if (status == null) {
			throw new IllegalArgumentException("No matching constant for [" + code + "]");
		}
		return status;
	}

	/**
	 * Resolve the given status code to an {@code HttpStatus}, if possible.
	 * @param code the HTTP status code (potentially non-standard)
	 * @return the corresponding {@code HttpStatus}, or {@code null} if not found
	 * @since 5.0
	 */
	@Nullable
	public static QubyteStatus resolve(int code) {
		for (QubyteStatus status : values()) {
			if (status.code == code) {
				return status;
			}
		}
		return null;
	}


	/**
	 * Enumeration of HTTP status series.
	 * <p>Retrievable via {@link QubyteStatus#series()}.
	 */
	public enum Series {

		INFORMATIONAL(1),
		SUCCESSFUL(2),
		REDIRECTION(3),
		CLIENT_ERROR(4),
		SERVER_ERROR(5);

		private final int value;

		Series(int value) {
			this.value = value;
		}

		/**
		 * Return the integer value of this status series. Ranges from 1 to 5.
		 */
		public int value() {
			return this.value;
		}

		/**
		 * Return the enum constant of this type with the corresponding series.
		 * @param status a standard HTTP status enum value
		 * @return the enum constant of this type with the corresponding series
		 * @throws IllegalArgumentException if this enum has no corresponding constant
		 */
		public static Series valueOf(QubyteStatus status) {
			return valueOf(status.code);
		}

		/**
		 * Return the enum constant of this type with the corresponding series.
		 * @param code the HTTP status code (potentially non-standard)
		 * @return the enum constant of this type with the corresponding series
		 * @throws IllegalArgumentException if this enum has no corresponding constant
		 */
		public static Series valueOf(int code) {
			Series series = resolve(code);
			if (series == null) {
				throw new IllegalArgumentException("No matching constant for [" + code + "]");
			}
			return series;
		}

		/**
		 * Resolve the given status code to an {@code HttpStatus.Series}, if possible.
		 * @param code the HTTP status code (potentially non-standard)
		 * @return the corresponding {@code Series}, or {@code null} if not found
		 * @since 5.1.3
		 */
		@Nullable
		public static Series resolve(int code) {
			int seriesCode = code / 100;
			for (Series series : values()) {
				if (series.value == seriesCode) {
					return series;
				}
			}
			return null;
		}
	}

}
