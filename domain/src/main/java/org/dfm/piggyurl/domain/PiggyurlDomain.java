package org.dfm.piggyurl.domain;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.CARD_CANNOT_CREATE;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.CARD_CANNOT_UPDATE;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.CARD_ID_MANDATORY_FOR_UPDATE;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.CARD_NOT_FOUND;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.CREATOR_USER_NOT_ADMIN;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.CREATOR_USER_NOT_FOUND;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.GROUP_CANNOT_CREATE;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.GROUP_NOT_FOUND;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.NO_CARD_AVAILABLE_FOR_APPROVE;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.PORTS_NOT_DEFINED;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.UPDATER_USER_NOT_FOUND;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.USER_ALREADY_PRESENT;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.USER_CANNOT_CREATE;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.USER_IS_ADMIN_FOR_OTHER_GROUP;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.USER_NOT_ADMIN;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.USER_NOT_FOUND;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.dfm.piggyurl.domain.common.ShortUrlLevel;
import org.dfm.piggyurl.domain.common.UserGroupType;
import org.dfm.piggyurl.domain.common.UserRightLevel;
import org.dfm.piggyurl.domain.exception.CommonException;
import org.dfm.piggyurl.domain.exception.PiggyurlNotFoundException;
import org.dfm.piggyurl.domain.exception.UserNotAuthorizedException;
import org.dfm.piggyurl.domain.model.Card;
import org.dfm.piggyurl.domain.model.CardUpdate;
import org.dfm.piggyurl.domain.model.Group;
import org.dfm.piggyurl.domain.model.Piggyurl;
import org.dfm.piggyurl.domain.model.PiggyurlInfo;
import org.dfm.piggyurl.domain.model.User;
import org.dfm.piggyurl.domain.port.ObtainCard;
import org.dfm.piggyurl.domain.port.ObtainPiggyurl;
import org.dfm.piggyurl.domain.port.ObtainUser;
import org.dfm.piggyurl.domain.port.RequestCard;
import org.dfm.piggyurl.domain.port.RequestPiggyurl;
import org.dfm.piggyurl.domain.port.RequestUser;

public class PiggyurlDomain implements RequestPiggyurl, RequestUser, RequestCard {

  private ObtainPiggyurl obtainPiggyurl;
  private ObtainUser obtainUser;
  private ObtainCard obtainCard;

  public PiggyurlDomain() {
    this(new ObtainPiggyurl() {
    });
  }

  public PiggyurlDomain(ObtainPiggyurl obtainPiggyurl) {
    this.obtainPiggyurl = obtainPiggyurl;
  }

  public PiggyurlDomain(final ObtainUser obtainUser) {
    this.obtainUser = obtainUser;
  }

  public PiggyurlDomain(final ObtainUser obtainUser, final ObtainCard obtainCard) {
    this.obtainCard = obtainCard;
    this.obtainUser = obtainUser;
  }

  @Override
  public PiggyurlInfo getPiggyurls() {
    if (isNull(obtainPiggyurl)) {
      throw new CommonException(PORTS_NOT_DEFINED);
    }
    return PiggyurlInfo.builder().piggyurls(obtainPiggyurl.getAllPiggyurls()).build();
  }

  @Override
  public Piggyurl getPiggyurlByCode(String code) {
    if (isNull(obtainPiggyurl)) {
      throw new CommonException(PORTS_NOT_DEFINED);
    }
    Optional<Piggyurl> piggyurl = obtainPiggyurl.getPiggyurlByCode(code);
    return piggyurl.orElseThrow(() -> new PiggyurlNotFoundException(code));
  }

  @Override
  public User getUserLoginDetail(final String userName, final String password) {
    if (isNull(obtainUser)) {
      throw new CommonException(PORTS_NOT_DEFINED);
    }
    Optional<User> getUserLoginDetail = obtainUser.getUserLoginDetail(userName, password);
    return getUserLoginDetail.orElseThrow(() -> new UserNotAuthorizedException(userName));
  }

  @Override
  public Group createGroup(final String userNameOfCreator, final Group group) {
    if (isNull(obtainUser)) {
      throw new CommonException(PORTS_NOT_DEFINED);
    }
    //Get the creator user detail and he can create User only if he is an Admin
    Optional<User> creatorUserDetail = obtainUser.getUserByUserName(userNameOfCreator);
    if (creatorUserDetail.isPresent()) {
      // An Admin can only create a group
      if (UserRightLevel.ADMIN.equals(creatorUserDetail.get().getRightLevel())) {
        if (nonNull(creatorUserDetail.get().getGroupFtId()) || nonNull(
            creatorUserDetail.get().getGroupTbId())) {
          throw new CommonException(userNameOfCreator + " " + USER_IS_ADMIN_FOR_OTHER_GROUP);
        }
        Optional<Group> groupDetail = obtainUser.createGroup(group.getName(), group.getType());
        if (creatorUserDetail.isPresent()) {
          //Update User with the Group ID which is created just.
          if (UserGroupType.FEATURE_TEAM.equals(group.getType())) {
            creatorUserDetail.get().setGroupFtId(groupDetail.get().getId());
            obtainUser.createUser(creatorUserDetail.get());
          }
          if (UserGroupType.TRIBE.equals(group.getType())) {
            creatorUserDetail.get().setGroupTbId(groupDetail.get().getId());
            obtainUser.createUser(creatorUserDetail.get());
          }
        }
        return groupDetail.orElseThrow(() -> new CommonException(GROUP_CANNOT_CREATE));
      } else {
        throw new CommonException(userNameOfCreator + " " + CREATOR_USER_NOT_ADMIN);
      }
    } else {
      throw new CommonException(userNameOfCreator + " " + CREATOR_USER_NOT_FOUND);
    }
  }

  @Override
  public User createUser(String userNameOfCreator, User user) {
    if (isNull(obtainUser)) {
      throw new CommonException(PORTS_NOT_DEFINED);
    }
    //Get the creator user detail and he can create User only if he is an Admin
    Optional<User> creatorUserDetail = obtainUser.getUserByUserName(userNameOfCreator);
    if (creatorUserDetail.isPresent()) {
      if (UserRightLevel.ADMIN.equals(creatorUserDetail.get().getRightLevel())) {
        // Check if User name to be created already exist.
        Optional<User> userToBeCreatedDetail = obtainUser.getUserByUserName(user.getUserName());
        userToBeCreatedDetail.ifPresent(s -> {
          throw new CommonException(user.getUserName() + " " + USER_ALREADY_PRESENT);
        });
        if (nonNull(user.getGroupFtName())) {
          Group groupDetail = groupDetail(user.getGroupFtName(), UserGroupType.FEATURE_TEAM);
          Long featureTeamId = nonNull(groupDetail) ? groupDetail.getId() : null;
          user.setGroupFtId(featureTeamId);
        }
        if (nonNull(user.getGroupTbName())) {
          Group groupDetail = groupDetail(user.getGroupTbName(), UserGroupType.TRIBE);
          Long tribeId = nonNull(groupDetail) ? groupDetail.getId() : null;
          user.setGroupTbId(tribeId);
        }
        user.setCreatedByUserId(creatorUserDetail.get().getId());
        user.setCreatedDate(LocalDate.now());
        Optional<User> userDetail = obtainUser.createUser(user);
        return userDetail
            .orElseThrow(() -> new CommonException(user.getUserName() + " " + USER_CANNOT_CREATE));
      } else {
        throw new CommonException(userNameOfCreator + " " + CREATOR_USER_NOT_ADMIN);
      }
    } else {
      throw new CommonException(userNameOfCreator + " " + CREATOR_USER_NOT_FOUND);
    }
  }

  private Group groupDetail(final String groupName, final UserGroupType groupType) {
    if (isNull(obtainUser)) {
      throw new CommonException(PORTS_NOT_DEFINED);
    }
    if (nonNull(groupName) && nonNull(groupType)) {
      Optional<Group> groupDetail = obtainUser.getGroupByNameAndType(groupName, groupType);
      return groupDetail.orElseThrow(() -> new CommonException(groupName + " " + GROUP_NOT_FOUND));
    }
    return null;
  }

  @Override
  public Card createCard(final String userNameOfCreator, final String originalUrl,
      final ShortUrlLevel shortUrlLevel, final String description, LocalDate expirationDate,
      final int standardExpirationDurationInDays) {
    if (isNull(obtainUser) || isNull(obtainCard)) {
      throw new CommonException(PORTS_NOT_DEFINED);
    }
    //Get the creator user detail and he can create User only if he is an Admin
    Optional<User> creatorUserDetail = obtainUser.getUserByUserName(userNameOfCreator);
    if (creatorUserDetail.isPresent()) {
      if (UserRightLevel.ADMIN.equals(creatorUserDetail.get().getRightLevel())) {
        LocalDate startDate = LocalDate.now();
        if (isNull(expirationDate)) {
          expirationDate = calculateExpiryDate(startDate, standardExpirationDurationInDays);
        }
        if (ShortUrlLevel.FEATURE_TEAM.equals(shortUrlLevel) || ShortUrlLevel.TRIBE
            .equals(shortUrlLevel)) {
          expirationDate = null;
        }
        Card cartToBeCreated = Card.builder().
            urlOriginal(originalUrl).
            urlShort(generateShortUrl(creatorUserDetail.get(), shortUrlLevel)).
            description(description).
            createdUserName(creatorUserDetail.get().getUserName()).
            approvedUserName(creatorUserDetail.get().getUserName()).
            approved("Y").
            groupId(resolveGroupIdForCardCreate(creatorUserDetail.get(), shortUrlLevel)).
            creationDate(startDate).
            expirationDate(expirationDate).build();
        Optional<Card> createdCard = obtainCard.createCard(cartToBeCreated);
        return createdCard.orElseThrow(() -> new CommonException(CARD_CANNOT_CREATE));
      } else {
        throw new CommonException(userNameOfCreator + " " + CREATOR_USER_NOT_ADMIN);
      }
    } else {
      throw new CommonException(userNameOfCreator + " " + CREATOR_USER_NOT_FOUND);
    }
  }

  @Override
  public Card updateCard(final String userNameOfUpdator, final Card cardDetail) {
    if (isNull(obtainUser) || isNull(obtainCard)) {
      throw new CommonException(PORTS_NOT_DEFINED);
    }
    //Get the creator user detail and he can create User only if he is an Admin
    Optional<User> updatorUserDetail = obtainUser.getUserByUserName(userNameOfUpdator);
    if (updatorUserDetail.isPresent()) {
      if (UserRightLevel.ADMIN.equals(updatorUserDetail.get().getRightLevel())) {
        return updateCardForAdmin(userNameOfUpdator, cardDetail);
      } else {
        return updateCardForUser(userNameOfUpdator, cardDetail);
      }
    } else {
      throw new CommonException(userNameOfUpdator + " " + UPDATER_USER_NOT_FOUND);
    }
  }

  @Override
  public void deleteCard(final String userNameOfDeleter, final Long cardId) {
    if (isNull(obtainUser) || isNull(obtainCard)) {
      throw new CommonException(PORTS_NOT_DEFINED);
    }
    List<Card> cardOutputs = new ArrayList<>();
    //Get the deleter user detail and he can delete card only if he is an Admin
    Optional<User> approverUserDetail = obtainUser.getUserByUserName(userNameOfDeleter);
    if (approverUserDetail.isPresent()) {
      if (UserRightLevel.ADMIN.equals(approverUserDetail.get().getRightLevel())) {
          obtainCard.deleteCard(cardId);
      } else {
        throw new CommonException(
            userNameOfDeleter + " " + USER_NOT_ADMIN + " Hence can not delete");
      }
    } else {
      throw new CommonException(userNameOfDeleter + " " + USER_NOT_FOUND);
    }
  }

  @Override
  public List<Card> approveCard(final String approverUserName) {
    if (isNull(obtainUser) || isNull(obtainCard)) {
      throw new CommonException(PORTS_NOT_DEFINED);
    }
    List<Card> cardOutputs = new ArrayList<>();
    //Get the creator user detail and he can create User only if he is an Admin
    Optional<User> approverUserDetail = obtainUser.getUserByUserName(approverUserName);
    if (approverUserDetail.isPresent()) {
      if (UserRightLevel.ADMIN.equals(approverUserDetail.get().getRightLevel())) {
        List<CardUpdate> cardUpdateList = obtainCard.getCardUpdateByUserName(approverUserName);
        if (nonNull(cardUpdateList) && !cardUpdateList.isEmpty()) {
          for (CardUpdate cardUpdate : cardUpdateList) {
            Optional<Card> getCardDetailById = obtainCard.getCardDetailById(cardUpdate.getCardId());
            if (getCardDetailById.isPresent()) {
              getCardDetailById.get().setApproved("Y");
              getCardDetailById.get().setApprovedUserName(approverUserDetail.get().getUserName());
              Optional<Card> cardDetail = obtainCard.createCard(getCardDetailById.get());
              if (cardDetail.isPresent()) {
                cardOutputs.add(cardDetail.get());
                obtainCard.deleteCardUpdate(cardUpdate.getId());
              }
            }
          }
        } else {
          throw new CommonException(NO_CARD_AVAILABLE_FOR_APPROVE + " " + approverUserName);
        }
      } else {
        throw new CommonException(
            approverUserName + " " + USER_NOT_ADMIN + " Hence can not approve");
      }
    } else {
      throw new CommonException(approverUserName + " " + USER_NOT_FOUND);
    }
    return cardOutputs;
  }

  @Override
  public List<CardUpdate> getCardUpdatesForApproval(final String userName) {
    if (isNull(obtainUser) || isNull(obtainCard)) {
      throw new CommonException(PORTS_NOT_DEFINED);
    }
    Optional<User> approverUserDetail = obtainUser.getUserByUserName(userName);
    if (approverUserDetail.isPresent()) {
      return obtainCard.getCardUpdateByUserName(userName);
    } else {
      throw new CommonException(userName + " " + USER_NOT_FOUND);
    }
  }

  @Override
  public List<Card> getApprovedCardsForGroup(final String groupName,
      final UserGroupType groupType) {
    if (isNull(obtainUser) || isNull(obtainCard)) {
      new UserNotAuthorizedException(PORTS_NOT_DEFINED);
    }
    Optional<Group> groupDetail = obtainUser.getGroupByNameAndType(groupName, groupType);
    if (groupDetail.isPresent()) {
      return obtainCard.getApprovedCardsByGroupId(groupDetail.get().getId());
    } else {
      throw new CommonException(groupName + " " + GROUP_NOT_FOUND);
    }
  }

  private Card updateCardForAdmin(final String userNameOfUpdator, final Card cardDetail) {
    if (isNull(cardDetail.getId())) {
      throw new CommonException(CARD_ID_MANDATORY_FOR_UPDATE);
    }
    cardDetail.setApproved("Y");
    cardDetail.setApprovedUserName(userNameOfUpdator);
    Optional<Card> updatedCard = obtainCard.createCard(cardDetail);
    return updatedCard.orElseThrow(() -> new CommonException(CARD_CANNOT_UPDATE));
  }

  private Card updateCardForUser(final String userNameOfUpdator, final Card cardDetail) {
    if (isNull(cardDetail.getId())) {
      throw new CommonException(CARD_ID_MANDATORY_FOR_UPDATE);
    }
    //Get existing Card Detail before update
    Optional<Card> existingCardDetail = obtainCard.getCardDetailById(cardDetail.getId());
    if (existingCardDetail.isPresent()) {
      cardDetail.setApproved("N");
      cardDetail.setApprovedUserName(null);
      Optional<Card> updatedCard = obtainCard.createCard(cardDetail);
      // Now store the changes in new table for Admin to validate.
      Optional<Group> groupDetail = obtainUser.getGroupById(cardDetail.getGroupId());
      String cardUpdateUserName = userNameOfUpdator;
      if (groupDetail.isPresent()) {
        //Get the Admin of that Group
        Optional<User> adminUserForGroup = Optional.empty();
        if (UserGroupType.TRIBE.equals(groupDetail.get().getType())) {
          adminUserForGroup = obtainUser.getUserByTribeId(groupDetail.get().getId());
        } else {
          adminUserForGroup = obtainUser.getUserByFeatureTeamId(groupDetail.get().getId());
        }
        if (adminUserForGroup.isPresent()) {
          cardUpdateUserName = adminUserForGroup.get().getUserName();
        }
      }
      // Insert into Card Update Table for Admin approval
      obtainCard.createCardUpdate(CardUpdate.builder().cardId(updatedCard.get().getId())
          .approverUserName(cardUpdateUserName)
          .beforeUpdate(existingCardDetail.toString()).afterUpdate(updatedCard.toString()).build());
      return updatedCard.orElseThrow(() -> new CommonException(CARD_CANNOT_UPDATE));
    } else {
      throw new CommonException(CARD_NOT_FOUND);
    }
  }

  private String generateShortUrl(final User creatorUserDetail, final ShortUrlLevel shortUrlLevel) {
    String randomString = UUID.randomUUID().toString();
    String defaultUrl = "http://PiggyUrl/tiny/" + randomString;
    switch (shortUrlLevel) {
      case TRIBE:
        Optional<Group> tribeDetail = obtainUser
            .getGroupByIdAndType(creatorUserDetail.getGroupTbId(), UserGroupType.TRIBE);
        return tribeDetail.isPresent() ? "http://PiggyUrl/" + tribeDetail.get().getName() + "/"
            + randomString : defaultUrl;
      case FEATURE_TEAM:
        Optional<Group> ftDetail = obtainUser
            .getGroupByIdAndType(creatorUserDetail.getGroupFtId(), UserGroupType.FEATURE_TEAM);
        return ftDetail.isPresent() ? "http://PiggyUrl/" + ftDetail.get().getName() + "/"
            + randomString : defaultUrl;
      case USER:
        return "http://PiggyUrl/" + creatorUserDetail.getUserName() + "/" + randomString;
      case NONE:
        return defaultUrl;
    }
    return defaultUrl;
  }

  private LocalDate calculateExpiryDate(final LocalDate startDate, final int expiryDuration) {
    return startDate.plusDays(expiryDuration);
  }

  private Long resolveGroupIdForCardCreate(final User creatorUserDetail,
      final ShortUrlLevel shortUrlLevel) {
    Long groupId = null;
    switch (shortUrlLevel) {
      case TRIBE:
        Optional<Group> tribeDetail = obtainUser
            .getGroupByIdAndType(creatorUserDetail.getGroupTbId(), UserGroupType.TRIBE);
        return tribeDetail.isPresent() ? tribeDetail.get().getId() : groupId;
      case FEATURE_TEAM:
        Optional<Group> ftDetail = obtainUser
            .getGroupByIdAndType(creatorUserDetail.getGroupFtId(), UserGroupType.FEATURE_TEAM);
        return ftDetail.isPresent() ? ftDetail.get().getId() : groupId;
    }
    return groupId;
  }
}
