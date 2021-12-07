/* AssentSoftware (C)2021 */
package com.greek.service.config;

import com.gvt.security.config.DefaultJwtSecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class JwtSecurityConfiguration extends DefaultJwtSecurityConfiguration {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        //		http.cors().and().csrf().disable().authorizeRequests().anyRequest().permitAll();
    }
}
