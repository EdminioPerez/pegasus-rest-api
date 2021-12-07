/* AssentSoftware (C)2021 */
package com.greek.service.security.jwt.converters;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.gvt.security.jwt.converters.DefaultClaimsConverter;

@Component
public class CustomClaimsConverter extends DefaultClaimsConverter {

    @Override
    public Map<String, Object> convert(Map<String, Object> claims) {
        return super.convert(claims);
    }
}
