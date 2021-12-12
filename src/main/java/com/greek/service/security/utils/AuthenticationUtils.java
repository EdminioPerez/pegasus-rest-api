/* AssentSoftware (C)2021 */
package com.greek.service.security.utils;

import com.greek.main.hibernate.model.Organizacion;
import com.gvt.core.exceptions.LogicException;
import com.gvt.security.jwt.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class AuthenticationUtils {

    private AuthenticationUtils() {
        // Utility class
    }

    public static Organizacion getCurrentGroup() {
        Organizacion currentOrganization = new Organizacion();
        currentOrganization.setId(JwtUtils.getRootCenterId());

        return currentOrganization;
    }

    public static String getCurrentUserCountryCode() {
        return JwtUtils.getCountryCode();
    }

    public static void checkValidOrganizationIdInToken(Long idToSearch) {
        for (Long centerId : JwtUtils.getCentersIds()) {
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

        while (currentOrganization.getOrganizacion() != null
                && currentOrganization.getOrganizacion().getId() != null) {
            currentOrganization = currentOrganization.getOrganizacion();
        }

        if (currentOrganization.getId().longValue() != JwtUtils.getRootCenterId().longValue()) {
            throw new LogicException("Token integrity violation", "error.security.token.integrity");
        }

        return currentOrganization;
    }
}
