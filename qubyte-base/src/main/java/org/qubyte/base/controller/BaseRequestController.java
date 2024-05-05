package org.qubyte.base.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.qubyte.base.logger.BaseLogger;
import org.slf4j.Logger;

/**
 * 
 * @author Alok kumar
 * 
 */
public class BaseRequestController {

	static Logger logger = BaseLogger.getLogger(BaseRequestController.class);

	protected <T extends Object> void printRequestAsJSON(T t) {
		ObjectMapper objm = new ObjectMapper();
		String jsonStr = "";
		try {
			jsonStr = objm.writeValueAsString(t);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
			// e.printStackTrace();
		}
		logger.info("JSON String of Request Message::- " + jsonStr);
	}

	protected <T extends Object> void printResponseAsJSON(T t) {
		ObjectMapper objm = new ObjectMapper();
		String jsonStr = "";
		try {
			jsonStr = objm.writeValueAsString(t);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
			// e.printStackTrace();
		}
		logger.info("JSON String of Response Message::- " + jsonStr);
	}
}
