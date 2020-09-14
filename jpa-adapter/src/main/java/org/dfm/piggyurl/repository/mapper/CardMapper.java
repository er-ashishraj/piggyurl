package org.dfm.piggyurl.repository.mapper;

import static java.util.Objects.nonNull;

import org.dfm.piggyurl.domain.model.Card;
import org.dfm.piggyurl.repository.entity.CardEntity;

public class CardMapper {

  public CardEntity mapCardToCardEntity(final Card card) {
    return nonNull(card) ? CardEntity.builder().id(card.getId()).urlOriginal(card.getUrlOriginal())
        .urlShort(card.getUrlShort())
        .description(card.getDescription()).createdUserName(card.getCreatedUserName()).
            approvedUserName(card.getApprovedUserName()).approved(card.getApproved())
        .groupId(card.getGroupId())
        .creationDate(card.getCreationDate()).expirationDate(card.getExpirationDate()).build()
        : null;
  }

  public Card mapCardEntityToCard(final CardEntity cardentity) {
    return nonNull(cardentity) ? Card.builder().id(cardentity.getId())
        .urlOriginal(cardentity.getUrlOriginal()).urlShort(cardentity.getUrlShort())
        .description(cardentity.getDescription()).createdUserName(cardentity.getCreatedUserName()).
            approvedUserName(cardentity.getApprovedUserName()).approved(cardentity.getApproved())
        .groupId(cardentity.getGroupId())
        .creationDate(cardentity.getCreationDate()).expirationDate(cardentity.getExpirationDate())
        .build() : null;
  }

}
