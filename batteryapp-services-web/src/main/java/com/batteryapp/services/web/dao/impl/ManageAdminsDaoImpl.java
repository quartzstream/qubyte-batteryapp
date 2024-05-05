package com.batteryapp.services.web.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.batteryapp.services.web.dao.ManageAdminsDao;
import com.batteryapp.services.web.domain.ViewSuperAdminRequestDomain;
import com.batteryapp.services.web.entity.UserMaster;
import org.qubyte.base.dao.impl.JPADAOIMPL;
import org.qubyte.base.domain.QueryParamHolder;
import org.qubyte.base.utils.QubytePasswordEncoder;
import org.qubyte.base.utils.QueryUtils;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
@Repository
public class ManageAdminsDaoImpl extends JPADAOIMPL implements ManageAdminsDao {

	@Autowired
	private QubytePasswordEncoder qubytePasswordEncoder;
	
	@Override
	public List<UserMaster> isMobileExist(String mobileNo) {
		
		return getEntityManager().createQuery("from UserMaster where mobileNo = :mobileNo ", UserMaster.class)
				.setParameter("mobileNo", mobileNo).getResultList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public QueryParamHolder getSuperAdminDetail(ViewSuperAdminRequestDomain viewDomain,
			QueryParamHolder paramHolder) {
		
		QueryParamHolder queryParamHolder = paramHolder;
		
		Map paramMap = new HashMap();
		
		 String query ="SELECT UM.USER_CD,UM.USER_FIRST_NAME,UM.USER_MIDDLE_NAME,"
		 		+ "	UM.USER_LAST_NAME,UM.EMAIL_ID,UM.ENCRYPTED_PASSWORD,UM.MOBILE_NO,"
		 		+ "	UM.MAPPED_ORG_ID,UM.USER_TYPE,UM.DOB,UM.GENDER,UM.CREATE_DT,COUNT(*) OVER() AS TOTAL_RECORD FROM USER_MST UM WHERE UM.USER_TYPE='SA'";

			if (StringUtils.isNotBlank(viewDomain.getColumn()) && StringUtils.isNotBlank(viewDomain.getValue())) {
				
				query += " AND " + viewDomain.getColumn() + " = :columnVal ";
				paramMap.put("columnVal", viewDomain.getValue());

			}
	        
	        StringBuffer queryBuffer = new StringBuffer(query);
	        queryParamHolder.setQueryBuffer(queryBuffer);
	        queryParamHolder.setQueryParameter(paramMap);
	        queryParamHolder = QueryUtils.appendSortingOrder(queryParamHolder);
	        queryParamHolder = paginatedQuery(queryParamHolder);
	        
	        List outputList = new ArrayList();
	        List<?> rtnList = queryParamHolder.getAaData();//qry.getResultList();

			int totalRecord = 0;
			
			for(int i=0; i<rtnList.size(); i++) {
				Map<String, Object> rtnmap = new HashMap<String, Object>();
				Object[] obj = (Object[]) rtnList.get(i);

				rtnmap.put("user_cd", obj[0]);
				rtnmap.put("first_name", obj[1]);
				rtnmap.put("middle_name", obj[2]);
				rtnmap.put("last_lame", obj[3]);
				rtnmap.put("email_ld", obj[4]);
				String encryptedPassword = (String) obj[5];
				rtnmap.put("encrypted_password", encryptedPassword);
				rtnmap.put("password", qubytePasswordEncoder.decrypt(encryptedPassword));
				rtnmap.put("mobile_no", obj[6]);
	            rtnmap.put("mapped_org_id", obj[7]);
	            rtnmap.put("user_type", obj[8]);
	            rtnmap.put("dob", obj[9]);
	            rtnmap.put("gender", obj[10]);
	            rtnmap.put("create_dt", obj[11]);
	            
	            if(obj[12] != null) {
	            	totalRecord = Integer.parseInt(obj[12].toString());
	            }
	            
				outputList.add(rtnmap);
			}
			
			paramHolder.setITotalRecords(totalRecord);
	    	queryParamHolder.setAaData(outputList);

	        return queryParamHolder;
	}


}
