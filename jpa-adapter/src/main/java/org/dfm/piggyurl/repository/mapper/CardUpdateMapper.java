package org.dfm.piggyurl.repository.mapper;

import static java.util.Objects.nonNull;

import org.dfm.piggyurl.domain.model.CardUpdate;
import org.dfm.piggyurl.repository.entity.CardUpdateEntity;

public class CardUpdateMapper {

  public CardUpdateEntity mapCardUpdateToCardUpdateEntity(final CardUpdate cardUpdate) {
    return nonNull(cardUpdate) ? CardUpdateEntity.builder().id(cardUpdate.getId())
        .cardId(cardUpdate.getCardId()).approverUserName(cardUpdate.getApproverUserName())
        .beforeUpdate(cardUpdate.getBeforeUpdate())
        .afterUpdate(cardUpdate.getAfterUpdate()).build()
        : null;
  }

  public CardUpdate mapCardUpdateEntityToCardUpdate(final CardUpdateEntity cardUpdateEntity) {
    return nonNull(cardUpdateEntity) ? CardUpdate.builder().id(cardUpdateEntity.getId())
        .cardId(cardUpdateEntity.getCardId())
        .approverUserName(cardUpdateEntity.getApproverUserName())
        .beforeUpdate(cardUpdateEntity.getBeforeUpdate())
        .afterUpdate(cardUpdateEntity.getAfterUpdate()).build()
        : null;
  }

}
