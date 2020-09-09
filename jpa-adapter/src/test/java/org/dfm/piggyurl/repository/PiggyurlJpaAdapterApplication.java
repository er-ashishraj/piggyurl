package org.dfm.piggyurl.repository;

import org.dfm.piggyurl.domain.port.ObtainPiggyurl;
import org.dfm.piggyurl.repository.dao.PiggyurlDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PiggyurlJpaAdapterApplication {

    public static void main(String[] args) {
        SpringApplication.run(PiggyurlJpaAdapterApplication.class, args);
    }

    @TestConfiguration
    static class PiggyurlJpaTestConfig {

        @Bean
        public ObtainPiggyurl getObtainPiggyurlRepository(PiggyurlDao piggyurlDao) {
            return new PiggyurlRepository(piggyurlDao);
        }
    }
}
