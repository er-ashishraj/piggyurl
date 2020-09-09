package org.dfm.piggyurl;

import org.dfm.piggyurl.domain.PiggyurlDomain;
import org.dfm.piggyurl.domain.exception.PiggyurlNotFoundException;
import org.dfm.piggyurl.domain.model.Piggyurl;
import org.dfm.piggyurl.domain.model.PiggyurlInfo;
import org.dfm.piggyurl.domain.port.ObtainPiggyurl;
import org.dfm.piggyurl.domain.port.RequestPiggyurl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class AcceptanceTest {

    @Test
    @DisplayName("should be able to get piggyurls when asked for piggyurls from hard coded piggyurls")
    public void getPiggyurlsFromHardCoded() {
  /*
      RequestPiggyurl    - left side port
      PiggyurlDomain     - hexagon (domain)
      ObtainPiggyurl     - right side port
   */
        RequestPiggyurl requestPiggyurl = new PiggyurlDomain(); // the piggyurl is hard coded
        PiggyurlInfo piggyurlInfo = requestPiggyurl.getPiggyurls();
        assertThat(piggyurlInfo).isNotNull();
        assertThat(piggyurlInfo.getPiggyurls()).isNotEmpty().extracting("description")
                .contains(
                        "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)");
    }

    @Test
    @DisplayName("should be able to get piggyurls when asked for piggyurls from stub")
    public void getPiggyurlsFromMockedStub(@Mock ObtainPiggyurl obtainPiggyurl) {
        // Stub
        Piggyurl piggyurl = Piggyurl.builder().code("A").description(
                "I want to sleep\r\nSwat the flies\r\nSoftly, please.\r\n\r\n-- Masaoka Shiki (1867-1902)")
                .build();
        Mockito.lenient().when(obtainPiggyurl.getAllPiggyurls()).thenReturn(List.of(piggyurl));
        // hexagon
        RequestPiggyurl requestPiggyurl = new PiggyurlDomain(obtainPiggyurl);
        PiggyurlInfo piggyurlInfo = requestPiggyurl.getPiggyurls();
        assertThat(piggyurlInfo).isNotNull();
        assertThat(piggyurlInfo.getPiggyurls()).isNotEmpty().extracting("description")
                .contains(
                        "I want to sleep\r\nSwat the flies\r\nSoftly, please.\r\n\r\n-- Masaoka Shiki (1867-1902)");
    }

    @Test
    @DisplayName("should be able to get piggyurl when asked for piggyurl by id from stub")
    public void getPiggyurlByIdFromMockedStub(@Mock ObtainPiggyurl obtainPiggyurl) {
        // Given
        // Stub
        String code = "A";
        String description = "I want to sleep\\r\\nSwat the flies\\r\\nSoftly, please.\\r\\n\\r\\n-- Masaoka Shiki (1867-1902)";
        Piggyurl expectedPiggyurl = Piggyurl.builder().code(code).description(description).build();
        Mockito.lenient().when(obtainPiggyurl.getPiggyurlByCode(code))
                .thenReturn(Optional.of(expectedPiggyurl));
        // When
        RequestPiggyurl requestPiggyurl = new PiggyurlDomain(obtainPiggyurl);
        Piggyurl actualPiggyurl = requestPiggyurl.getPiggyurlByCode(code);
        assertThat(actualPiggyurl).isNotNull().isEqualTo(expectedPiggyurl);
    }

    @Test
    @DisplayName("should throw exception when asked for piggyurl by id that does not exists from stub")
    public void getExceptionWhenAskedPiggyurlByIdThatDoesNotExist(@Mock ObtainPiggyurl obtainPiggyurl) {
        // Given
        // Stub
        String code = "B";
        Mockito.lenient().when(obtainPiggyurl.getPiggyurlByCode(code)).thenReturn(Optional.empty());
        // When
        RequestPiggyurl requestPiggyurl = new PiggyurlDomain(obtainPiggyurl);
        // Then
        assertThatThrownBy(() -> requestPiggyurl.getPiggyurlByCode(code)).isInstanceOf(
                PiggyurlNotFoundException.class)
                .hasMessageContaining("Piggyurl with code " + code + " does not exist");
    }
}
