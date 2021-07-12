package com.greek.service.filters;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.zalando.logbook.CorrelationId;

public class CorrelationIdMDCInjectionFilter implements Filter {

	private final String USER_KEY = "username";
	private final String CORRELATION_ID = "correlationId";

	private CorrelationId correlationId;

	public CorrelationIdMDCInjectionFilter(CorrelationId correlationId) {
		this.correlationId = correlationId;
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		boolean successfulRegistration = false;
		HttpServletRequest req = (HttpServletRequest) request;
		Principal principal = req.getUserPrincipal();
		// Please note that we also could have used a cookie to
		// retrieve the user name

		if (principal != null) {
			String username = principal.getName();
			successfulRegistration = registerUsername(username);
			MDC.put(CORRELATION_ID, correlationId.generate(null));
		}

		try {
			chain.doFilter(request, response);
		} finally {
			if (successfulRegistration) {
				MDC.remove(USER_KEY);
			}
			MDC.remove(CORRELATION_ID);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

	/**
	 * Register the user in the MDC under USER_KEY.
	 * 
	 * @param username
	 * @return true id the user can be successfully registered
	 */
	private boolean registerUsername(String username) {
		if (StringUtils.isNotBlank(username)) {
			MDC.put(USER_KEY, username);
			return true;
		}
		return false;
	}

}