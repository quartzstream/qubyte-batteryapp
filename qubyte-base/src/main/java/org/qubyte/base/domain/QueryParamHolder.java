package org.qubyte.base.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Alok kumar
 *
 */

@Data
public class QueryParamHolder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7815488401847437374L;
	
	private String query;
	private StringBuffer queryBuffer;
	private Map<String, Object> DTParam;
	private Map<String, Object> queryParameter;
	private int startValue;
	private int endValue;
	private int pageSize;
	private boolean isNative;
	private Map<String, Object> paramMap;
	private List<?> aaData;
	private int iTotalRecords;
	private int iTotalDisplayRecords;
	private int datatTableSize;
	private String orderByColumn;
	private String sortingOrder;
	
	//DT Param
	private String iDisplayStart;
	private String iSortCol_0;
	private String sSortDir_0;
	private String iDisplayLength;
}
