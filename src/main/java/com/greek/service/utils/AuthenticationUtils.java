package com.greek.service.utils;

import com.greek.main.hibernate.model.Organizacion;
import com.gvt.core.exceptions.LogicException;
import com.gvt.security.utils.JwtUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class AuthenticationUtils {

	private AuthenticationUtils() {
		// Utility class
	}

	public static Organizacion getCurrentGroup() {
		Organizacion currentOrganization = new Organizacion();
		currentOrganization.setId(JwtUtils.getJwtAuthenticationDetails().getRootCenterId());

		return currentOrganization;
	}

	public static String getCurrentUserCountryCode() {
		return JwtUtils.getJwtAuthenticationDetails().getCountryCode();
	}

	public static void checkValidOrganizationIdInToken(Long idToSearch) {
		for (Long centerId : JwtUtils.getJwtAuthenticationDetails().getCentersIds()) {
			log.trace("Comparing idToSearch:{} with {}", idToSearch, centerId);
			if (idToSearch.equals(centerId)) {
				return;
			}
		}

		throw new LogicException("Token integrity violation", "error.security.token.integrity");
	}

	// This method must be called from inside a transaction
	// TODO This method could be moved to the gateway using a cache mechanism
	public static Organizacion belongsToRootOrganizationInToken(Organizacion organization) {
		Organizacion currentOrganization = organization;

		while (currentOrganization.getOrganizacion() != null && currentOrganization.getOrganizacion().getId() != null) {
			currentOrganization = currentOrganization.getOrganizacion();
		}

		if (currentOrganization.getId().longValue() != JwtUtils.getJwtAuthenticationDetails().getRootCenterId()
				.longValue()) {
			throw new LogicException("Token integrity violation", "error.security.token.integrity");
		}

		return currentOrganization;
	}

}
