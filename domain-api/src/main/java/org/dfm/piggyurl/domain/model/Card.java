package org.dfm.piggyurl.domain.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card {

  private Long id;
  private String urlOriginal;
  private String urlShort;
  private String description;
  private String createdUserName;
  private String approvedUserName;
  private String approved;
  private Long groupId;// To be Given if URL is on Tribe or Feature Team level
  private LocalDate creationDate;
  private LocalDate expirationDate;

  @Override
  public String toString() {
    return "|id-" + id + "|" + "urlOriginal-" + urlOriginal + "|" + "urlShort-" + urlShort + "|"
        + "description-" + description + "|" +
        "createdUserName-" + createdUserName + "|" + "approvedUserName-" + approvedUserName + "|"
        + "approved-" + approved + "|" + "groupId-" + groupId + "|" + "creationDate-" + creationDate
        + "|" +
        "expirationDate-" + expirationDate + "|";
  }
}
