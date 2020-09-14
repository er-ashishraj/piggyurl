package org.dfm.piggyurl.domain.port;

import java.time.LocalDate;
import java.util.List;
import org.dfm.piggyurl.domain.common.ShortUrlLevel;
import org.dfm.piggyurl.domain.common.UserGroupType;
import org.dfm.piggyurl.domain.model.Card;
import org.dfm.piggyurl.domain.model.CardUpdate;

public interface RequestCard {

  Card createCard(final String userNameOfCreator, final String originalUrl,
      final ShortUrlLevel shortUrlLevel, final String description, final LocalDate expirationDate,
      final int standardExpirationDurationInDays);

  Card updateCard(final String userNameOfUpdator, final Card cardDetail);

  List<Card> approveCard(final String approverUserName);

  List<CardUpdate> getCardUpdatesForApproval(final String userName);

  List<Card> getApprovedCardsForGroup(final String groupName, final UserGroupType groupType);
}
