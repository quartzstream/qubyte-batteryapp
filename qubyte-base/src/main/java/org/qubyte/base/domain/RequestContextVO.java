package org.qubyte.base.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Alok kumar
 * 
 */

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RequestContextVO {
	
	private Long txnId;
	private UserInfo userInfo;
	private Map<String,Object> requestParameterMap= new HashMap<String,Object>();
	
}
