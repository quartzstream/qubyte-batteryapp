package org.qubyte.base.utils;


import org.qubyte.base.domain.QueryParamHolder;
import javax.persistence.Query;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Alok kumar
 *
 */
public class QueryUtils {
	/**
	 *
	 * 
	 * 
	 * @param queryPara, dtPara, key
	 * 
	 * @return parammap
	 * 
	 */

	public static Map<String, Object> setSortingOrder(Map<String, Object> queryPara, Map<String, Object> dtPara,
			String key) {

		String sortingOrder = "asc";

		String orderByColumn = "";

		if (dtPara.size() > 0) {

			sortingOrder = (String) dtPara.get("sSortDir_0");

			orderByColumn = (String) dtPara.get("orderbyCol");

		}

		queryPara.put(key, orderByColumn + " " + sortingOrder);

		return queryPara;

	}

	/**
	 * 
	 * @param queryParamHolder dtPara, key
	 * 
	 * @return query
	 * 
	 */

	public static QueryParamHolder appendSortingOrder(QueryParamHolder queryParamHolder) {

		StringBuffer query = queryParamHolder.getQueryBuffer();

		String sortingOrder = "desc";
		String orderByColumn = "";

		if (queryParamHolder.getSortingOrder() != null) {

			sortingOrder = (String) queryParamHolder.getSortingOrder();
		}

		if (queryParamHolder.getOrderByColumn() != null) {

			orderByColumn = (String) queryParamHolder.getOrderByColumn();
		}

		query.append(" Order By ").append(orderByColumn + " ").append(sortingOrder);

		queryParamHolder.setQueryBuffer(query);

		return queryParamHolder;

	}

	/**
	 * @param queryPara
	 * @param query
	 * @return
	 */
	public static Query setQueryParameter(Query query, Map<String, Object> queryPara) {

		Iterator<Entry<String, Object>> entries = queryPara.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, Object> entry = entries.next();
			query.setParameter((String) entry.getKey(), entry.getValue());
		}

		return query;
	}

}
