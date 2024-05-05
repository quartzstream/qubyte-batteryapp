package com.batteryapp.services.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.batteryapp.services.web.service.AuthenticationService;
import com.batteryapp.services.web.utils.ApiEndPoint;
import org.qubyte.base.requestcontext.IRequestInitiationContext;
import org.qubyte.base.requestcontext.RequestContext;
import org.qubyte.base.utils.QubytePasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
@Configuration
@EnableWebSecurity
public class WebSecurityconfig extends WebSecurityConfigurerAdapter {
	
	@Value("${password.validationPolicy}")
	private String validationPolicy;

	@Autowired
	private final AuthenticationService authenticationService;

	public WebSecurityconfig(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		
				.antMatchers(ApiEndPoint.BASE_URL.BASE + ApiEndPoint.URL.LOGIN_AUTH
							).permitAll()
				
				.antMatchers("/test").hasAuthority("Add_Site_Engineer")
				.anyRequest().authenticated()
				.and().exceptionHandling()
				.authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add our custom JWT security filter
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	}

	/*
	 * @Override public void configure(WebSecurity web) throws Exception {
	 * web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui",
	 * "/swagger-resources/**", "/configuration/**", "/swagger-ui.html",
	 * "/webjars/**"); }
	 */

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationService).passwordEncoder(passwordEncoder());
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public QubytePasswordEncoder passwordEncoder() {
		return new QubytePasswordEncoder();
	}
	
	@Bean
    public IRequestInitiationContext requestInitiationContext() {
        return new RequestContext();
    }

}
