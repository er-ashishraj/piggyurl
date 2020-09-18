package org.dfm.piggyurl.domain.port;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.dfm.piggyurl.domain.model.Card;
import org.dfm.piggyurl.domain.model.CardUpdate;

public interface ObtainCard {

  default Optional<Card> createCard(final Card card) {
    return Optional.empty();
  }

  default Optional<Card> getCardDetailById(final Long cardId) {
    return Optional.empty();
  }

  default Optional<CardUpdate> createCardUpdate(final CardUpdate cardUpdate) {
    return Optional.empty();
  }

  default void deleteCardUpdate(final Long cardUpdateId) {
  }

  default void deleteCard(final Long cardId) {
  }

  default List<CardUpdate> getCardUpdateByUserName(final String approverUserName) {
    return Collections.emptyList();
  }

  default List<Card> getApprovedCardsByGroupId(final Long groupId) {
    return Collections.emptyList();
  }
}
