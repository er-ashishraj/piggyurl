package org.dfm.piggyurl.repository.config;

import org.dfm.piggyurl.domain.port.ObtainCard;
import org.dfm.piggyurl.domain.port.ObtainPiggyurl;
import org.dfm.piggyurl.domain.port.ObtainUser;
import org.dfm.piggyurl.repository.CardRepository;
import org.dfm.piggyurl.repository.PiggyurlRepository;
import org.dfm.piggyurl.repository.UserRepository;
import org.dfm.piggyurl.repository.dao.CardDao;
import org.dfm.piggyurl.repository.dao.CardUpdateDao;
import org.dfm.piggyurl.repository.dao.GroupDao;
import org.dfm.piggyurl.repository.dao.PiggyurlDao;
import org.dfm.piggyurl.repository.dao.UserDao;
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

  @Bean
  public ObtainUser getUserRepository(final UserDao userDao, final GroupDao groupDao) {
    return new UserRepository(userDao, groupDao);
  }

  @Bean
  public ObtainCard getCardRepository(final UserDao userDao, final GroupDao groupDao,
      final CardDao cardDao, final CardUpdateDao cardUpdateDao) {
    return new CardRepository(userDao, groupDao, cardDao, cardUpdateDao);
  }
}
