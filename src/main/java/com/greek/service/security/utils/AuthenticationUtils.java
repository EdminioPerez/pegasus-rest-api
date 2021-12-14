/* AssentSoftware (C)2021 */
package com.greek.service.security.utils;

import com.greek.main.hibernate.model.Organizacion;
import com.gvt.core.exceptions.LogicException;
import com.gvt.security.jwt.utils.JwtUtils;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class AuthenticationUtils {

    private AuthenticationUtils() {
        // Utility class
    }

    public static Organizacion getCurrentGroup() {
        var currentOrganization = new Organizacion();
        currentOrganization.setId(JwtUtils.getRootCenterId());

        return currentOrganization;
    }

    public static String getCurrentUserCountryCode() {
        return JwtUtils.getCountryCode();
    }

    public static void checkValidOrganizationIdInToken(Long idToSearch) {
        boolean idToSearchExists =
                Arrays.stream(JwtUtils.getCentersIds()).anyMatch(idToSearch::equals);

        if (!idToSearchExists) {
            throw new LogicException("Token integrity violation", "error.security.token.integrity");
        }
    }

    // This method must be called from inside a transaction
    // TODO This method could be moved to the gateway using a cache mechanism
    public static Organizacion belongsToRootOrganizationInToken(Organizacion organization) {
        var currentOrganization = organization;

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
