package org.dfm.piggyurl.repository.entity;

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
import org.dfm.piggyurl.domain.model.CardUpdate;

@Table(name = "T_CARD_UPDATE")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardUpdateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_T_CARD_UPDATE")
  @Column(name = "CARD_UPDATE_ID")
  private Long id;

  @Column(name = "CARD_ID")
  private Long cardId;

  @Column(name = "APPROVER_USER_NAME")
  private String approverUserName;

  @Column(name = "BEFORE_UPDATE")
  private String beforeUpdate;

  @Column(name = "AFTER_UPDATE")
  private String afterUpdate;

  public CardUpdate toCardUpdateModel() {
    return CardUpdate.builder().id(id).cardId(cardId).approverUserName(approverUserName)
        .beforeUpdate(beforeUpdate)
        .afterUpdate(afterUpdate).build();
  }
}
