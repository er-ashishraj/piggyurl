package org.dfm.piggyurl.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardUpdate {

  private Long id;
  private Long cardId;
  private String approverUserName;
  private String beforeUpdate;
  private String afterUpdate;
}
