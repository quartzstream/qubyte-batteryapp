package org.qubyte.base.dao;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.qubyte.base.domain.QueryParamHolder;



/**
 * 
 * @author Alok kumar
 * 
 */
public interface JPADAO {

	<T> T find(Class<T> entityClass, Serializable id);

	<T> T getReference(Class<T> entityClass, Serializable id);

	void flush();

	<T> T update(T entity);

	<T> T save(T entity);

	<T> T saveOrUpdate(T entity);

	<T> void delete(T entity);

	EntityManager getEntityManager();

	QueryParamHolder getQueryPaginatedOutput(QueryParamHolder paramHolder);

	QueryParamHolder getNativeQueryPaginatedOutput(QueryParamHolder paramHolder);

	List<?> createQuery(QueryParamHolder paramHolder);

	List<?> createNativeQuery(QueryParamHolder paramHolder);

	QueryParamHolder paginatedQuery(QueryParamHolder paramHolder);

	@SuppressWarnings("rawtypes")
	Object getObjectById(Long id, Class clazz);

	<T> List<?> saveBatch(List<?> entityList, int batchSize);

	List<BigDecimal> getSeqInBatch(String seqName, int batchSize);

	<T> List<?> updateBatch(List<?> entityList, int batchSize);

}
