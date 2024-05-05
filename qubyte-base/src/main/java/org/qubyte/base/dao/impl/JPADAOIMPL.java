package org.qubyte.base.dao.impl;

import org.qubyte.base.dao.JPADAO;
import org.qubyte.base.domain.QueryParamHolder;
import org.qubyte.base.exception.SystemException;
import org.qubyte.base.logger.BaseLogger;
import org.qubyte.base.utils.QueryUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 
 * @author Alok kumar
 * 
 */
@Repository
public class JPADAOIMPL implements JPADAO {

	private static final Logger LOGGER = BaseLogger.getLogger(JPADAOIMPL.class);

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public <T> T find(Class<T> entityClass, Serializable id) {
		
		if ((entityClass == null) || (id == null)) {
			throw new SystemException("Entity class or id cannot be null");
		}
		return (T) this.em.find(entityClass, id);
	}

	@Override
	public <T> T getReference(Class<T> entityClass, Serializable id) {
		
		if ((entityClass == null) || (id == null)) {
			throw new SystemException("Entity class or id cannot be null");
		}
		return (T) getEntityManager().getReference(entityClass, id);
	}

	@Override
	public void flush() {
		
		this.em.flush();
	}

	@Override
	public <T> T update(T entity) {
		
		if (entity.getClass() != null) {
			return (T) this.em.merge(entity);
		}
		throw new SystemException(
				"Cannot update an un-persisted entity. Please persist the entity before updating it.");
	}

	@Override
	public <T> T save(T entity) {
		
		this.em.persist(entity);
		return (T) entity;
	}

	@Override
	public <T> T saveOrUpdate(T entity) {
		
		return (T) this.em.merge(entity);
	}

	@Override
	public <T> void delete(T entity) {
		
		getEntityManager().remove(entity);
		
	}

	@Override
	public EntityManager getEntityManager() {
		
		return this.em;
	}

	@Override
	public QueryParamHolder getQueryPaginatedOutput(QueryParamHolder paramHolder) {
		
		String query = paramHolder.getQueryBuffer().toString();
		Map<String, Object> paramMap = paramHolder.getQueryParameter();
		int startValue = paramHolder.getStartValue();
		//int endValue = paramHolder.getEndValue();
		int pageSize = paramHolder.getPageSize();

		Query qry = getEntityManager().createQuery(query);
		qry.setFirstResult(startValue);
		qry.setMaxResults(pageSize);

		qry = QueryUtils.setQueryParameter(qry, paramMap);

		List<?> rtnList = qry.getResultList();

		int tableSize = rtnList.size();

		paramHolder.setDatatTableSize(tableSize);
		paramHolder.setAaData(rtnList);

		return paramHolder;
	}

	@Override
	public QueryParamHolder getNativeQueryPaginatedOutput(QueryParamHolder paramHolder) {
		
		String query = paramHolder.getQueryBuffer().toString();
		Map<String, Object> paramMap = paramHolder.getQueryParameter();
		int startValue = paramHolder.getStartValue();
		//int endValue = paramHolder.getEndValue();
		int pageSize = paramHolder.getPageSize();

		Query qry = getEntityManager().createNativeQuery(query);
		qry.setFirstResult(startValue);
		qry.setMaxResults(pageSize);

		qry = QueryUtils.setQueryParameter(qry, paramMap);

		List<?> rtnList = qry.getResultList();

		int tableSize = rtnList.size();

		paramHolder.setDatatTableSize(tableSize);
		paramHolder.setAaData(rtnList);

		return paramHolder;
	}

	@Override
	public List<?> createQuery(QueryParamHolder paramHolder) {
		
		String query = paramHolder.getQuery();
		Map<String, Object> paramMap = paramHolder.getQueryParameter();

		Query qry = getEntityManager().createQuery(query);

		qry = QueryUtils.setQueryParameter(qry, paramMap);

		return qry.getResultList();
	}

	@Override
	public List<?> createNativeQuery(QueryParamHolder paramHolder) {
		
		String query = paramHolder.getQuery();
		Map<String, Object> paramMap = paramHolder.getQueryParameter();

		Query qry = getEntityManager().createNativeQuery(query);

		qry = QueryUtils.setQueryParameter(qry, paramMap);

		return qry.getResultList();
	}

	@Override
	public QueryParamHolder paginatedQuery(QueryParamHolder queryParamHolder) {
		
		QueryParamHolder paramHolder = queryParamHolder;

		Map<String, Object> dtParam = paramHolder.getDTParam();
		boolean isNative = paramHolder.isNative();

		int startValue=0;
    	int pageSize=10;
    	//int pageno;
    	//int endValue;

    	//this block of code is for pagination of results
    	//set in object
    	if(dtParam.size() > 0){
    		//sortingColumnNumber = (String) dtParam.get("iSortCol_0");
    		//columnNumber = Integer.parseInt(sortingColumnNumber);
    		//sortingOrder = (String)DTParam.get("sSortDir_0");
    		startValue = Integer.parseInt((String)dtParam.get("iDisplayStart"));
    		pageSize = Integer.parseInt((String)dtParam.get("iDisplayLength"));
    		//orderByColumn = (String) dtParam.get("orderbyCol");
    	}
   
    	//pageno = (startValue/pageSize)+1;
    	//endValue = (pageno + 0) * pageSize;

    	paramHolder.setStartValue(startValue);
    	//paramHolder.setEndValue(endValue);
    	paramHolder.setPageSize(pageSize);

    	
    	if(!isNative) {
    		paramHolder = getQueryPaginatedOutput(paramHolder);
    	} else if (isNative) {
    		paramHolder = getNativeQueryPaginatedOutput(paramHolder);
    	}
    	
    	int datasize = paramHolder.getDatatTableSize();
    	int recordTobeDisplayed = startValue + datasize;
    	paramHolder.setITotalDisplayRecords(recordTobeDisplayed);

		return paramHolder;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object getObjectById(Long id, Class clazz) {
		
		return find(clazz, id);
	}

	@Override
	public <T> List<?> saveBatch(List<?> entityList, int batchSize) {
		
		int size = entityList == null ? 0 : entityList.size();
		
		
		LOGGER.info("objectList size: {} " , size);
		for (int i = 0; i < size; i++){
            em.persist(entityList.get(i));
            if (i % batchSize == 0 && i > 0) {
                em.flush();
                em.clear();
                LOGGER.info("no of records saved : {} " , i);
            }
        }
		
		int remCount = size % batchSize;
		if(remCount >= 0 &&  size > 0 ) {
			em.flush();
            em.clear();
            LOGGER.info("no of records saved is {} " , size % batchSize);
        }
         
		return entityList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BigDecimal> getSeqInBatch(String seqName, int batchSize) {
		
		List<BigDecimal> list = null;
		try {
			String query = "SELECT "+seqName+".nextval from dual connect by level<=:batchSize";
			Query q = getEntityManager().createNativeQuery(query);
			q.setParameter("batchSize", batchSize);
			list =   (List<BigDecimal>) q.getResultList();
		} catch (Exception e) {
			LOGGER.error("Error in getSeqInBatch for seq "+seqName+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public <T> List<?> updateBatch(List<?> entityList, int batchSize) {
		
		int size = entityList == null ? 0 : entityList.size();
		
		
		LOGGER.info("objectList size: {} " , size);
		for (int i = 0; i < size; i++){
            em.merge(entityList.get(i));
            if (i % batchSize == 0 && i > 0) {
                em.flush();
                em.clear();
                LOGGER.info("no of records updated : {} " , i);
            }
        }
		
		int remCount = size % batchSize;
		if(remCount >= 0 &&  size > 0 ) {
			em.flush();
            em.clear();
            LOGGER.info("no of updated saved is {} " , size % batchSize);
        }
         
		return entityList;
	}

}
