package org.dfm.piggyurl.repository;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.dfm.piggyurl.domain.model.Card;
import org.dfm.piggyurl.domain.model.CardUpdate;
import org.dfm.piggyurl.domain.port.ObtainCard;
import org.dfm.piggyurl.repository.dao.CardDao;
import org.dfm.piggyurl.repository.dao.CardUpdateDao;
import org.dfm.piggyurl.repository.dao.GroupDao;
import org.dfm.piggyurl.repository.dao.UserDao;
import org.dfm.piggyurl.repository.entity.CardEntity;
import org.dfm.piggyurl.repository.entity.CardUpdateEntity;
import org.dfm.piggyurl.repository.mapper.CardMapper;
import org.dfm.piggyurl.repository.mapper.CardUpdateMapper;
import org.springframework.util.CollectionUtils;

public class CardRepository implements ObtainCard {

  private UserDao userDao;
  private GroupDao groupDao;
  private CardDao cardDao;
  private CardUpdateDao cardUpdateDao;

  public CardRepository(final UserDao userDao, final GroupDao groupDao, final CardDao cardDao,
      final CardUpdateDao cardUpdateDao) {
    this.userDao = userDao;
    this.groupDao = groupDao;
    this.cardDao = cardDao;
    this.cardUpdateDao = cardUpdateDao;
  }

  @Override
  public Optional<Card> createCard(final Card card) {
    CardMapper cardMapper = new CardMapper();
    CardEntity createdCardDetail = cardDao.save(cardMapper.mapCardToCardEntity(card));
    return nonNull(createdCardDetail) ? Optional
        .of(cardMapper.mapCardEntityToCard(createdCardDetail)) : null;
  }

  @Override
  public Optional<Card> getCardDetailById(Long cardId) {
    Optional<CardEntity> createdCardDetail = cardDao.findById(cardId);
    return createdCardDetail.map(CardEntity::toCardModel);
  }

  @Override
  public Optional<CardUpdate> createCardUpdate(CardUpdate cardUpdate) {
    CardUpdateMapper cardUpdateMapper = new CardUpdateMapper();
    CardUpdateEntity createdCardUpdateDetail = cardUpdateDao
        .save(cardUpdateMapper.mapCardUpdateToCardUpdateEntity(cardUpdate));
    return nonNull(createdCardUpdateDetail) ? Optional
        .of(cardUpdateMapper.mapCardUpdateEntityToCardUpdate(createdCardUpdateDetail)) : null;
  }

  @Override
  public void deleteCardUpdate(final Long cardUpdateId) {
    cardUpdateDao.deleteById(cardUpdateId);
  }

  @Override
  public List<CardUpdate> getCardUpdateByUserName(final String userName) {
    List<CardUpdate> cardUpdateList = new ArrayList<>();
    CardUpdateMapper cardUpdateMapper = new CardUpdateMapper();
    List<CardUpdateEntity> createdCardUpdateDetails = cardUpdateDao
        .findAllByApproverUserName(userName);
    if (!CollectionUtils.isEmpty(createdCardUpdateDetails)) {
      for (CardUpdateEntity cardUpdateEntity : createdCardUpdateDetails) {
        cardUpdateList.add(cardUpdateMapper.mapCardUpdateEntityToCardUpdate(cardUpdateEntity));
      }
    }
    return cardUpdateList;
  }

  @Override
  public List<Card> getApprovedCardsByGroupId(final Long groupId) {
    List<Card> cardList = new ArrayList<>();
    CardMapper cardMapper = new CardMapper();
    List<CardEntity> approvedCardDetails = cardDao
        .findAllByGroupIdAndApproved(groupId, "Y");
    if (!CollectionUtils.isEmpty(approvedCardDetails)) {
      for (CardEntity cardEntity : approvedCardDetails) {
        cardList.add(cardMapper.mapCardEntityToCard(cardEntity));
      }
    }
    return cardList;
  }
}