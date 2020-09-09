package org.dfm.piggyurl;

import org.dfm.piggyurl.domain.PiggyurlDomain;
import org.dfm.piggyurl.domain.port.ObtainPiggyurl;
import org.dfm.piggyurl.domain.port.RequestPiggyurl;
import org.dfm.piggyurl.repository.config.JpaAdapterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class PiggyurlE2EApplication {

    public static void main(String[] args) {
        SpringApplication.run(PiggyurlE2EApplication.class);
    }

    @TestConfiguration
    @Import(JpaAdapterConfig.class)
    static class PiggyurlConfig {

        @Bean
        public RequestPiggyurl getRequestPiggyurl(final ObtainPiggyurl obtainPiggyurl) {
            return new PiggyurlDomain(obtainPiggyurl);
        }
    }
}
