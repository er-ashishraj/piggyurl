package org.dfm.piggyurl.rest;

import org.dfm.piggyurl.domain.port.RequestPiggyurl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.dfm.piggyurl")
public class PiggyurlRestAdapterApplication {

    @MockBean
    private RequestPiggyurl requestPiggyurl;

    public static void main(String[] args) {
        SpringApplication.run(PiggyurlRestAdapterApplication.class, args);
    }
}
