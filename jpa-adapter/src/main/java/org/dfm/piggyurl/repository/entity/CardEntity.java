package org.dfm.piggyurl.repository.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dfm.piggyurl.domain.model.Card;

@Table(name = "T_CARD")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_T_CARD")
  @Column(name = "CARD_ID")
  private Long id;

  @Column(name = "ORIGINAL_URL")
  private String urlOriginal;

  @Column(name = "SHORT_URL")
  private String urlShort;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "GROUP_ID")
  private Long groupId;

  @Column(name = "CREATE_USER_NAME")
  private String createdUserName;

  @Column(name = "APPROVE_USER_NAME")
  private String approvedUserName;

  @Column(name = "APPROVED")
  private String approved;

  @Column(name = "URL_CREATION_DATE")
  private LocalDate creationDate;

  @Column(name = "URL_EXPIRATION_DATE")
  private LocalDate expirationDate;

  public Card toCardModel() {
    return Card.builder().id(id).urlOriginal(urlOriginal).urlShort(urlShort)
        .description(description).createdUserName(createdUserName).
            approvedUserName(approvedUserName).approved(approved).groupId(groupId)
        .creationDate(creationDate).expirationDate(expirationDate).build();
  }
}
