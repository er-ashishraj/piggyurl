package org.dfm.piggyurl.boot.config;

import org.dfm.piggyurl.domain.PiggyurlDomain;
import org.dfm.piggyurl.domain.port.ObtainPiggyurl;
import org.dfm.piggyurl.domain.port.RequestPiggyurl;
import org.dfm.piggyurl.repository.config.JpaAdapterConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(JpaAdapterConfig.class)
public class BootstrapConfig {

    @Bean
    public RequestPiggyurl getRequestPiggyurl(ObtainPiggyurl obtainPiggyurl) {
        return new PiggyurlDomain(obtainPiggyurl);
    }
}
