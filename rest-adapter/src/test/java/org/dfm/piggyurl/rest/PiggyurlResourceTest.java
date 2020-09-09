package org.dfm.piggyurl.rest;

import org.dfm.piggyurl.domain.exception.PiggyurlNotFoundException;
import org.dfm.piggyurl.domain.model.Piggyurl;
import org.dfm.piggyurl.domain.model.PiggyurlInfo;
import org.dfm.piggyurl.domain.port.RequestPiggyurl;
import org.dfm.piggyurl.rest.exception.PiggyurlExceptionResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = PiggyurlRestAdapterApplication.class, webEnvironment = RANDOM_PORT)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class PiggyurlResourceTest {

    private static final String LOCALHOST = "http://localhost:";
    private static final String API_URI = "/api/v1/piggyurls";
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private RequestPiggyurl requestPiggyurl;

    @Test
    @DisplayName("should start the rest adapter application")
    public void startup() {
        assertThat(Boolean.TRUE).isTrue();
    }

    @Test
    @DisplayName("should give piggyurls when asked for piggyurls with the support of domain stub")
    public void obtainPiggyurlsFromDomainStub() {
        // Given
        Piggyurl piggyurl = Piggyurl.builder().id(1L).description("Johnny Johnny Yes Papa !!").build();
        Mockito.lenient().when(requestPiggyurl.getPiggyurls())
                .thenReturn(PiggyurlInfo.builder().piggyurls(List.of(piggyurl)).build());
        // When
        String url = LOCALHOST + port + API_URI;
        ResponseEntity<PiggyurlInfo> responseEntity = restTemplate.getForEntity(url, PiggyurlInfo.class);
        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getPiggyurls()).isNotEmpty().extracting("description")
                .contains("Johnny Johnny Yes Papa !!");
    }

    @Test
    @DisplayName("should give the piggyurl when asked for an piggyurl by code with the support of domain stub")
    public void obtainPiggyurlByCodeFromDomainStub() {
        // Given
        String code = "A";
        String description = "Johnny Johnny Yes Papa !!";
        Piggyurl piggyurl = Piggyurl.builder().code(code).description(description).build();
        Mockito.lenient().when(requestPiggyurl.getPiggyurlByCode(code)).thenReturn(piggyurl);
        // When
        String url = LOCALHOST + port + API_URI + "/" + code;
        ResponseEntity<Piggyurl> responseEntity = restTemplate.getForEntity(url, Piggyurl.class);
        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody()).isEqualTo(piggyurl);
    }

    @Test
    @DisplayName("should give exception when asked for an piggyurl by code that does not exists with the support of domain stub")
    public void shouldGiveExceptionWhenAskedForAnPiggyurlByCodeFromDomainStub() {
        // Given
        String code = "B";
        ;
        Mockito.lenient().when(requestPiggyurl.getPiggyurlByCode(code)).thenThrow(new
                PiggyurlNotFoundException(code));
        // When
        String url = LOCALHOST + port + API_URI + "/" + code;
        ResponseEntity<PiggyurlExceptionResponse> responseEntity = restTemplate
                .getForEntity(url, PiggyurlExceptionResponse.class);
        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody()).isEqualTo(PiggyurlExceptionResponse.builder()
                .message("Piggyurl with code " + code + " does not exist").path(API_URI + "/" + code)
                .build());
    }
}
