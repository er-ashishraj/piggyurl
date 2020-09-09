package org.dfm.piggyurl.repository.config;

import org.dfm.piggyurl.domain.port.ObtainPiggyurl;
import org.dfm.piggyurl.repository.PiggyurlRepository;
import org.dfm.piggyurl.repository.dao.PiggyurlDao;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("org.dfm.piggyurl.repository.entity")
@EnableJpaRepositories("org.dfm.piggyurl.repository.dao")
public class JpaAdapterConfig {

    @Bean
    public ObtainPiggyurl getPiggyurlRepository(PiggyurlDao piggyurlDao) {
        return new PiggyurlRepository(piggyurlDao);
    }
}
