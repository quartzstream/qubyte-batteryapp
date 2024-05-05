package org.qubyte.base.utils;


import org.qubyte.base.domain.QueryParamHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class DataTableUtils {

	private DataTableUtils(){
	}
	
	public static void setDtMap(HttpServletRequest request, QueryParamHolder queryParamHolder) {
		
		Map<String, Object> dtParam = new HashMap<String, Object>();
		
		String offset = request.getParameter("iDisplayStart");
		String sortingColumn = request.getParameter("iSortCol_0");
		String sortingOrder= request.getParameter("sSortDir_0"); 
		String rownum = request.getParameter("iDisplayLength"); 
		String orderByCol = request.getParameter("mDataProp_"+queryParamHolder.getISortCol_0());
		
		dtParam.put("iSortCol_0", sortingColumn);
		dtParam.put("iDisplayStart", offset);
		dtParam.put("sSortDir_0", sortingOrder);
		dtParam.put("iDisplayLength", rownum);
		dtParam.put("orderbyCol", orderByCol);
		
		//queryParamHolder.setOrderByColumn(orderByCol);
		queryParamHolder.setSortingOrder(sortingOrder);
		
		queryParamHolder.setDTParam(dtParam);
	}
}
