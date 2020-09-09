package org.dfm.piggyurl.domain.port;

import org.dfm.piggyurl.domain.model.Piggyurl;

import java.util.List;
import java.util.Optional;

public interface ObtainPiggyurl {

    default List<Piggyurl> getAllPiggyurls() {
        Piggyurl piggyurl = Piggyurl.builder().id(1L).description(
                "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)")
                .build();
        return List.of(piggyurl);
    }

    default Optional<Piggyurl> getPiggyurlByCode(String code) {
        return Optional.of(Piggyurl.builder().code("A").description(
                "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)")
                .build());
    }
}
