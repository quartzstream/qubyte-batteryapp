package org.qubyte.base.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

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
public abstract class BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = -2444945478063915241L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DT")
	private Date createDt;

	@Column(name = "CREATE_USER")
	private String createUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIF_DT")
	private Date modifDt;

	@Column(name = "MODIF_USER")
	private String modifUser;

	@Column(name = "VERIFIED_USER")
	private String verifiedUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VERIFIED_DT")
	private Date verifiedDt;

	@Column(name = "VERSION_ID")
	private Long versionId;

	@Column(name = "RECORD_ACTION_CODE")
	private String recordActionCode;

}
