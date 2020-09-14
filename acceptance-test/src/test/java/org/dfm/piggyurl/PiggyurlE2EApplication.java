package org.dfm.piggyurl;

import org.dfm.piggyurl.domain.PiggyurlDomain;
import org.dfm.piggyurl.domain.port.ObtainCard;
import org.dfm.piggyurl.domain.port.ObtainPiggyurl;
import org.dfm.piggyurl.domain.port.ObtainUser;
import org.dfm.piggyurl.domain.port.RequestCard;
import org.dfm.piggyurl.domain.port.RequestPiggyurl;
import org.dfm.piggyurl.domain.port.RequestUser;
import org.dfm.piggyurl.repository.config.JpaAdapterConfig;
import org.springframework.beans.factory.annotation.Qualifier;
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
        @Qualifier("requestPiggyurl")
        public RequestPiggyurl getRequestPiggyurl(final ObtainPiggyurl obtainPiggyurl) {
            return new PiggyurlDomain(obtainPiggyurl);
        }

        @Bean
        @Qualifier("requestUser")
        public RequestUser getRequestUser(final ObtainUser obtainUser) {
            return new PiggyurlDomain(obtainUser);
        }

        @Bean
        @Qualifier("requestCard")
        public RequestCard getRequestCard(final ObtainUser obtainUser, final ObtainCard obtainCard) {
            return new PiggyurlDomain(obtainUser, obtainCard);
        }
    }
}
