package org.dfm.piggyurl.rest;

import org.dfm.piggyurl.domain.port.RequestCard;
import org.dfm.piggyurl.domain.port.RequestPiggyurl;
import org.dfm.piggyurl.domain.port.RequestUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.dfm.piggyurl")
public class PiggyurlRestAdapterApplication {

    @MockBean
    @Qualifier("requestPiggyurl")
    private RequestPiggyurl requestPiggyurl;
    @MockBean
    @Qualifier("requestUser")
    private RequestUser requestUser;
    @MockBean
    @Qualifier("requestCard")
    private RequestCard requestCard;

    public static void main(String[] args) {
        SpringApplication.run(PiggyurlRestAdapterApplication.class, args);
    }
}
