package org.qubyte.base.requestcontext;


import org.qubyte.base.domain.UserInfo;

import java.util.Map;

/**
 * @author Alok kumar
 *
 */
public interface IRequestInitiationContext {

	Long getTrxnId();

	UserInfo getUserInfo();

	Map<String, Object> getRequestParameters();
	
	void setData(UserInfo userInfo);
	Object setMapData(String key,Object valObject);
}
