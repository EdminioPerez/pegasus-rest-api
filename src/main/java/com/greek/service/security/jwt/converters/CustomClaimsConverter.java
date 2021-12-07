/* AssentSoftware (C)2021 */
package com.greek.service.security.jwt.converters;

import com.gvt.security.jwt.converters.DefaultClaimsConverter;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class CustomClaimsConverter extends DefaultClaimsConverter {

    @Override
    public Map<String, Object> convert(Map<String, Object> claims) {
        return super.convert(claims);
    }
}
