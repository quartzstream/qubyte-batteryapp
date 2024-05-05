package org.qubyte.base.requestcontext;

import org.qubyte.base.domain.RequestContextVO;
import org.qubyte.base.domain.UserInfo;
import org.qubyte.base.logger.BaseLogger;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Map;
import java.util.Random;


/**
 * @author Alok kumar
 *
 */
@Service
public class RequestContext implements IRequestInitiationContext {
	private static Logger logger = BaseLogger.getLogger(RequestContext.class);
	static protected ThreadLocal<RequestContextVO> contextThreadLocal = new ThreadLocal<RequestContextVO>();

	protected RequestContextVO getContextVO() {
		RequestContextVO contextVO = contextThreadLocal.get();
		if (contextVO == null) {
			Random randomTxnIdGenerator = new Random();
			contextVO = new RequestContextVO();
			randomTxnIdGenerator.setSeed(Calendar.getInstance().getTimeInMillis());
			contextVO.setTxnId(randomTxnIdGenerator.nextLong());
			contextThreadLocal.set(contextVO);
		}
		return contextVO;
	}

	@Override
	public void setData(UserInfo userInfo) {

		logger.info("Set Request context Data.");
		getContextVO().setUserInfo(userInfo);

	}

	@Override
	public Object setMapData(String key, Object valObject) {
		return getContextVO().getRequestParameterMap().put(key, valObject);

	}

	@Override
	public Long getTrxnId() {
		if (contextThreadLocal.get() != null) {
			return contextThreadLocal.get().getTxnId();
		}
		return null;
	}

	@Override
	public UserInfo getUserInfo() {
		if (contextThreadLocal.get() != null) {
			return contextThreadLocal.get().getUserInfo();
		}
		return null;
	}

	@Override
	public Map<String, Object> getRequestParameters() {
		if (contextThreadLocal.get() != null) {
			return contextThreadLocal.get().getRequestParameterMap();
		}
		return null;
	}

}
