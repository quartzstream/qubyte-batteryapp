package org.qubyte.base.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.TreeSet;

/**
 * 
 * @author Alok kumar
 * 
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfo extends User implements UserDetails {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String userFullName;
	private Long userId;
	private String encodePassword;
	private Date lastLoginDateTime;
	private Date currentLoginDateTime;
	private Boolean isCredentialChangeRequired;
	private String userMailId;
	private String mappedOrgId;
	private String userType;
	private String pin;
	private String mobileNo;
	private Date pinExpiryDate;
	private String securityQuesFlag;
	private String prepaidPostpaidFlag;

	public UserInfo(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public UserInfo(String username) {

		super(username, "default", (Collection<? extends GrantedAuthority>) (new TreeSet<SimpleGrantedAuthority>()));
	}
	
}
