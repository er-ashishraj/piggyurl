package org.dfm.piggyurl.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.dfm.piggyurl")
public class PiggyurlApplication {

    public static void main(String[] args) {
        SpringApplication.run(PiggyurlApplication.class, args);
    }
}
