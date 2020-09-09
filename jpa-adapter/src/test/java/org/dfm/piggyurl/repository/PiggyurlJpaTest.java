package org.dfm.piggyurl.repository;

import org.dfm.piggyurl.domain.model.Piggyurl;
import org.dfm.piggyurl.domain.port.ObtainPiggyurl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PiggyurlJpaTest {

    @Autowired
    private ObtainPiggyurl obtainPiggyurl;

    @Test
    @DisplayName("should start the application")
    public void startup() {
        assertThat(Boolean.TRUE).isTrue();
    }

    @Sql(scripts = {"/sql/data.sql"})
    @Test
    @DisplayName("given piggyurls exist in database when asked should return all piggyurls from database")
    public void shouldGiveMePiggyurlsWhenAskedGivenPiggyurlExistsInDatabase() {
        // Given from @Sql
        // When
        List<Piggyurl> piggyurls = obtainPiggyurl.getAllPiggyurls();
        // Then
        assertThat(piggyurls).isNotNull().extracting("description").contains("Twinkle twinkle little star");
    }

    @Test
    @DisplayName("given no piggyurls exists in database when asked should return empty")
    public void shouldGiveNoPiggyurlWhenAskedGivenPiggyurlsDoNotExistInDatabase() {
        // When
        List<Piggyurl> piggyurls = obtainPiggyurl.getAllPiggyurls();
        // Then
        assertThat(piggyurls).isNotNull().isEmpty();
    }

    @Sql(scripts = {"/sql/data.sql"})
    @Test
    @DisplayName("given piggyurls exists in database when asked for piggyurl by id should return the piggyurl")
    public void shouldGiveThePiggyurlWhenAskedByIdGivenThatPiggyurlByThatIdExistsInDatabase() {
        // Given from @Sql
        // When
        Optional<Piggyurl> piggyurl = obtainPiggyurl.getPiggyurlByCode("A");
        // Then
        assertThat(piggyurl).isNotNull().isNotEmpty().get().isEqualTo(Piggyurl.builder().id(1L).code("A").description("Twinkle twinkle little star").build());
    }

    @Sql(scripts = {"/sql/data.sql"})
    @Test
    @DisplayName("given piggyurls exists in database when asked for piggyurl by id that does not exist should give empty")
    public void shouldGiveNoPiggyurlWhenAskedByIdGivenThatPiggyurlByThatIdDoesNotExistInDatabase() {
        // Given from @Sql
        // When
        Optional<Piggyurl> piggyurl = obtainPiggyurl.getPiggyurlByCode("B");
        // Then
        assertThat(piggyurl).isEmpty();
    }
}
