package org.dfm.piggyurl.cucumber;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.cucumber.java8.HookNoArgsBody;
import io.cucumber.spring.CucumberContextConfiguration;
import org.dfm.piggyurl.PiggyurlE2EApplication;
import org.dfm.piggyurl.domain.model.Piggyurl;
import org.dfm.piggyurl.domain.model.PiggyurlInfo;
import org.dfm.piggyurl.repository.dao.PiggyurlDao;
import org.dfm.piggyurl.repository.entity.PiggyurlEntity;
import org.dfm.piggyurl.rest.exception.PiggyurlExceptionResponse;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PiggyurlE2EApplication.class, webEnvironment = RANDOM_PORT)
@CucumberContextConfiguration
public class PiggyurlStepDef implements En {

    private static final String LOCALHOST = "http://localhost:";
    private static final String API_URI = "/api/v1/piggyurls";
    @LocalServerPort
    private int port;
    private ResponseEntity responseEntity;

    public PiggyurlStepDef(TestRestTemplate restTemplate, PiggyurlDao piggyurlDao) {

        DataTableType(
                (Map<String, String> row) -> Piggyurl.builder().code(row.get("code"))
                        .description(row.get("description")).build());
        DataTableType(
                (Map<String, String> row) -> PiggyurlEntity.builder().code(row.get("code"))
                        .description(row.get("description"))
                        .build());

        Before((HookNoArgsBody) piggyurlDao::deleteAll);
        After((HookNoArgsBody) piggyurlDao::deleteAll);

        Given("the following piggyurls exists in the library", (DataTable dataTable) -> {
            List<PiggyurlEntity> poems = dataTable.asList(PiggyurlEntity.class);
            piggyurlDao.saveAll(poems);
        });

        When("user requests for all piggyurls", () -> {
            String url = LOCALHOST + port + API_URI;
            responseEntity = restTemplate.getForEntity(url, PiggyurlInfo.class);
        });

        When("user requests for piggyurls by code {string}", (String code) -> {
            String url = LOCALHOST + port + API_URI + "/" + code;
            responseEntity = restTemplate.getForEntity(url, Piggyurl.class);
        });

        When("user requests for piggyurls by id {string} that does not exists", (String code) -> {
            String url = LOCALHOST + port + API_URI + "/" + code;
            responseEntity = restTemplate.getForEntity(url, PiggyurlExceptionResponse.class);
        });

        Then("the user gets an exception {string}", (String exception) -> {
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            Object body = responseEntity.getBody();
            assertThat(body).isNotNull();
            assertThat(body).isInstanceOf(PiggyurlExceptionResponse.class);
            assertThat(((PiggyurlExceptionResponse) body).getMessage()).isEqualTo(exception);
        });

        Then("the user gets the following piggyurls", (DataTable dataTable) -> {
            List<Piggyurl> expectedPiggyurls = dataTable.asList(Piggyurl.class);
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            Object body = responseEntity.getBody();
            assertThat(body).isNotNull();
            if (body instanceof PiggyurlInfo) {
                assertThat(((PiggyurlInfo) body).getPiggyurls()).isNotEmpty().extracting("description")
                        .containsAll(expectedPiggyurls.stream().map(Piggyurl::getDescription)
                                .collect(Collectors.toList()));
            } else if (body instanceof Piggyurl) {
                assertThat(body).isNotNull().extracting("description")
                        .isEqualTo(expectedPiggyurls.get(0).getDescription());
            }
        });
    }
}


