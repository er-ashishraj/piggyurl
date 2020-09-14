package org.dfm.piggyurl.rest.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dfm.piggyurl.domain.common.ShortUrlLevel;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardRequest {

  private String urlOriginal;
  private String description;
  private ShortUrlLevel shortUrlLevel;
  private LocalDate expirationDate;
}
