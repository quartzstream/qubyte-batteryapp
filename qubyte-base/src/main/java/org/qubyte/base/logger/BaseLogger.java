package org.qubyte.base.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Alok kumar
 * 
 */

public final class BaseLogger {
	private BaseLogger() {
	}

	public static Logger getLogger(Class<?> clazz) {
		Logger logger = null;
		if (logger == null) {
			logger = LoggerFactory.getLogger(clazz);
		}

		return logger;
	}
}
