package com.batteryapp.services.web.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.qubyte.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
@Data
@Table(name = "USER_MST")
@Entity
@EqualsAndHashCode(callSuper = false)
public class UserMaster extends BaseEntity<Long> implements Serializable {

	private static final long serialVersionUID = -2444945478063915241L;

	@Id
	@GeneratedValue(generator = "userMstUserId")
	@SequenceGenerator(name = "userMstUserId", sequenceName = "SEQ_USER_MST_USER_ID", allocationSize = 1)
	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "USER_CD")
	private String userCd;

	@Column(name = "USER_FIRST_NAME")
	private String userFirstName;

	@Column(name = "USER_MIDDLE_NAME")
	private String userMiddleName;

	@Column(name = "USER_LAST_NAME")
	private String userLastName;

	@Column(name = "EMAIL_ID")
	private String emailId;

	@Column(name = "DEPARTMENT")
	private String department;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_LOGIN_DATE")
	private Date lastLoginDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_LOGOUT_TIME")
	private Date lastLogoutTime;

	@Column(name = "ENCRYPTED_PASSWORD")
	private String encryptedPassword;

	@Column(name = "PASSWORD_EXPIRY_DATE")
	private Date passwordExpiryDate;

	@Column(name = "CURRENTLY_LOGGED_IN")
	private String currentlyLoggedIn;

	@Column(name = "LOGIN_FAILED_COUNTS")
	private Long loginFailedCounts;

	@Column(name = "EXPIRY_DATE")
	private Date expiryDate;

	@Column(name = "MACHINE_NAME")
	private String machineName;

	@Column(name = "MACHINE_ID")
	private String machineId;

	@Column(name = "LOCK_STATUS")
	private String lockStatus;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "STATUS_DT")
	private Date statusDt;

	@Column(name = "SECURITY_FLAG")
	private String securityFlag;

	@Column(name = "MOBILE_NO")
	private String mobileNo;

	@Column(name = "WORKFLOW_REMARKS")
	private String workflowRemarks;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOCK_STATUS_DT")
	private Date lockStatusDt;

	@Column(name = "MAPPED_ORG_ID")
	private String mappedOrgId;

	@Column(name = "USER_TYPE")
	private String userType;

	@Column(name = "PIN")
	private String pin;

	@Column(name = "DOB")
	private Date dob;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "PIN_EXPIRY_DATE")
	private Date pinExpiryDate;

	@Column(name = "SECURITY_QUES_FLAG")
	private String securityQuesFlag;

}
