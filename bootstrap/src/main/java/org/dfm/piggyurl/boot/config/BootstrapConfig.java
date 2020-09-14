package org.dfm.piggyurl.boot.config;

import org.dfm.piggyurl.domain.PiggyurlDomain;
import org.dfm.piggyurl.domain.port.ObtainCard;
import org.dfm.piggyurl.domain.port.ObtainPiggyurl;
import org.dfm.piggyurl.domain.port.ObtainUser;
import org.dfm.piggyurl.domain.port.RequestCard;
import org.dfm.piggyurl.domain.port.RequestPiggyurl;
import org.dfm.piggyurl.domain.port.RequestUser;
import org.dfm.piggyurl.repository.config.JpaAdapterConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(JpaAdapterConfig.class)
public class BootstrapConfig {

  @Bean
  @Qualifier("requestPiggyurl")
  public RequestPiggyurl getRequestPiggyurl(ObtainPiggyurl obtainPiggyurl) {
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
