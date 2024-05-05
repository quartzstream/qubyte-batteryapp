package com.batteryapp.services.web.security;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import org.qubyte.base.constants.AppConstants;
import org.qubyte.base.domain.UserInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
@Component
public class JwtTokenProvider {

	public String generateToken(Authentication authentication) {

		UserInfo userPrincipal = (UserInfo) authentication.getPrincipal();

		String authorities = null;

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + AppConstants.SecurityConstants.EXPIRATION_TIME);

		return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
				.claim("AUTHORITIES_KEY", authorities).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, AppConstants.SecurityConstants.SECRET).compact();
	}

	public String getUsernameFromJWT(String token) {

		Claims claims = Jwts.parser().setSigningKey(AppConstants.SecurityConstants.SECRET).parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}

	@SuppressWarnings({ "unused", "unchecked" })
	UsernamePasswordAuthenticationToken getAuthentication(final String token, final Authentication existingAuth,
			final UserDetails userDetails) {

		final JwtParser jwtParser = Jwts.parser().setSigningKey(AppConstants.SecurityConstants.SECRET);
		final Claims claimsJws = jwtParser.parseClaimsJws(token).getBody();

		String user = claimsJws.getSubject(); // email should be saved as

		final Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) userDetails.getAuthorities();
				

		return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
	}

	public boolean validateToken(String authToken) {

		Jwts.parser().setSigningKey(AppConstants.SecurityConstants.SECRET).parseClaimsJws(authToken);

		return true;
	}
}
