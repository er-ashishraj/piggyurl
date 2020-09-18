package org.dfm.piggyurl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.dfm.piggyurl.domain.PiggyurlDomain;
import org.dfm.piggyurl.domain.common.ShortUrlLevel;
import org.dfm.piggyurl.domain.common.UserGroupType;
import org.dfm.piggyurl.domain.common.UserRightLevel;
import org.dfm.piggyurl.domain.exception.CommonException;
import org.dfm.piggyurl.domain.model.Card;
import org.dfm.piggyurl.domain.model.CardUpdate;
import org.dfm.piggyurl.domain.model.Group;
import org.dfm.piggyurl.domain.model.User;
import org.dfm.piggyurl.domain.port.ObtainCard;
import org.dfm.piggyurl.domain.port.ObtainUser;
import org.dfm.piggyurl.domain.port.RequestCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CardAcceptanceTest {

  private static final String ORIGINAL_URL = "http://someapp:5601/app/kibana#/dashboard/target_new_prod_dashboard?_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:now-7d,mode:quick,to:now))&_a=(filters:!(),options:(darkTheme:!f),panels:!((col:1,id:ogn-data-received-vs-ogn-data-saved,panelIndex:1,row:1,size_x:5,size_y:4,type:visualization),(col:1,id:missing-ogn-information,panelIndex:2,row:5,size_x:5,size_y:5,type:visualization),(col:6,id:distribution-of-new-production-types,panelIndex:3,row:1,size_x:4,size_y:8,type:visualization)),query:(query_string:(analyze_wildcard:!t,query:'*')),title:target_new_prod_dashboard,uiState:())";

  @Test
  @DisplayName("user should to create card for none successfully from hard coded")
  public void userShouldAbleToCreateCardForNoneSuccessfullyFromHardCoded(
      @Mock ObtainUser obtainUser, @Mock ObtainCard obtainCard) {
  /*
      RequestCard    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainCard     - right side port
   */
    lenient().when(obtainUser.getUserByUserName("ashish.raj"))
        .thenReturn(mockUser(UserRightLevel.ADMIN));
    lenient().when(obtainCard.createCard(any()))
        .thenReturn(mockCardForCreate(ShortUrlLevel.NONE));
    // When
    RequestCard requestUser = new PiggyurlDomain(obtainUser, obtainCard);
    Card cardInfo = requestUser
        .createCard("ashish.raj", ORIGINAL_URL, ShortUrlLevel.NONE, "Description Test",
            LocalDate.now().plusDays(30), 30);
    assertThat(cardInfo).isNotNull();
    assertThat(cardInfo).extracting("createdUserName", "approved")
        .contains("ashish.raj", "Y");
  }

  @Test
  @DisplayName("user should to get exception for create card when ports not available")
  public void userShouldGetExceptionForCreateCardWhenPortsNotAvailable(
      @Mock ObtainUser obtainUser, @Mock ObtainCard obtainCard) {
  /*
      RequestCard    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainCard     - right side port
   */

    // When
    RequestCard requestUser = new PiggyurlDomain();
    Assertions.assertThrows(CommonException.class, () -> requestUser
        .createCard("ashish.raj", ORIGINAL_URL, ShortUrlLevel.NONE, "Description Test",
            LocalDate.now().plusDays(30), 30));
  }

  @Test
  @DisplayName("user should to get exception for create card when wrong user passed")
  public void userShouldGetExceptionForCreateCardWhenWrongUserPassed(
      @Mock ObtainUser obtainUser, @Mock ObtainCard obtainCard) {
  /*
      RequestCard    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainCard     - right side port
   */
    lenient().when(obtainUser.getUserByUserName("ashish.raj"))
        .thenReturn(Optional.empty());
    // When
    RequestCard requestUser = new PiggyurlDomain(obtainUser, obtainCard);
    Assertions.assertThrows(CommonException.class, () -> requestUser
        .createCard("ashish.raj", ORIGINAL_URL, ShortUrlLevel.NONE, "Description Test",
            LocalDate.now().plusDays(30), 30));
  }

  @Test
  @DisplayName("user should to get exception for create card when user is not admin")
  public void userShouldGetExceptionForCreateCardWhenUserIsNotAdmin(
      @Mock ObtainUser obtainUser, @Mock ObtainCard obtainCard) {
  /*
      RequestCard    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainCard     - right side port
   */
    lenient().when(obtainUser.getUserByUserName("ashish.raj"))
        .thenReturn(mockUser(UserRightLevel.USER));
    // When
    RequestCard requestUser = new PiggyurlDomain(obtainUser, obtainCard);
    Assertions.assertThrows(CommonException.class, () -> requestUser
        .createCard("ashish.raj", ORIGINAL_URL, ShortUrlLevel.NONE, "Description Test",
            LocalDate.now().plusDays(30), 30));
  }


  @Test
  @DisplayName("user should able to create card for user successfully from hard coded")
  public void userShouldAbleToCreateCardForUserSuccessfullyFromHardCoded(
      @Mock ObtainUser obtainUser, @Mock ObtainCard obtainCard) {
  /*
      RequestCard    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainCard     - right side port
   */
    lenient().when(obtainUser.getUserByUserName("ashish.raj"))
        .thenReturn(mockUser(UserRightLevel.ADMIN));
    lenient().when(obtainCard.createCard(any()))
        .thenReturn(mockCardForCreate(ShortUrlLevel.USER));
    // When
    RequestCard requestUser = new PiggyurlDomain(obtainUser, obtainCard);
    Card cardInfo = requestUser
        .createCard("ashish.raj", ORIGINAL_URL, ShortUrlLevel.USER, "Description Test",
            LocalDate.now().plusDays(30), 30);
    assertThat(cardInfo).isNotNull();
    assertThat(cardInfo).extracting("createdUserName", "approved")
        .contains("ashish.raj", "Y");
  }

  @Test
  @DisplayName("user should able to create card for tribe successfully from hard coded")
  public void userShouldAbleToCreateCardForTribeSuccessfullyFromHardCoded(
      @Mock ObtainUser obtainUser, @Mock ObtainCard obtainCard) {
  /*
      RequestCard    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainCard     - right side port
   */
    lenient().when(obtainUser.getUserByUserName("ashish.raj"))
        .thenReturn(mockUser(UserRightLevel.ADMIN));
    lenient().when(obtainCard.createCard(any()))
        .thenReturn(mockCardForCreate(ShortUrlLevel.TRIBE));
    // When
    RequestCard requestUser = new PiggyurlDomain(obtainUser, obtainCard);
    Card cardInfo = requestUser
        .createCard("ashish.raj", ORIGINAL_URL, ShortUrlLevel.TRIBE, "Description Test",
            LocalDate.now().plusDays(30), 30);
    assertThat(cardInfo).isNotNull();
    assertThat(cardInfo).extracting("createdUserName", "approved")
        .contains("ashish.raj", "Y");
  }

  @Test
  @DisplayName("user should able to create card for feature team successfully from hard coded")
  public void userShouldAbleToCreateCardForFeatureTeamSuccessfullyFromHardCoded(
      @Mock ObtainUser obtainUser, @Mock ObtainCard obtainCard) {
  /*
      RequestCard    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainCard     - right side port
   */
    lenient().when(obtainUser.getUserByUserName("ashish.raj"))
        .thenReturn(mockUser(UserRightLevel.ADMIN));
    lenient().when(obtainCard.createCard(any()))
        .thenReturn(mockCardForCreate(ShortUrlLevel.FEATURE_TEAM));
    // When
    RequestCard requestUser = new PiggyurlDomain(obtainUser, obtainCard);
    Card cardInfo = requestUser
        .createCard("ashish.raj", ORIGINAL_URL, ShortUrlLevel.FEATURE_TEAM, "Description Test",
            LocalDate.now().plusDays(30), 30);
    assertThat(cardInfo).isNotNull();
    assertThat(cardInfo).extracting("createdUserName", "approved")
        .contains("ashish.raj", "Y");
  }

  @Test
  @DisplayName("user should able to update card for admin successfully from hard coded")
  public void userShouldAbleToUpdateCardForAdminSuccessfullyFromHardCoded(
      @Mock ObtainUser obtainUser, @Mock ObtainCard obtainCard) {
  /*
      RequestCard    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainCard     - right side port
   */
    lenient().when(obtainUser.getUserByUserName("ashish.raj"))
        .thenReturn(mockUser(UserRightLevel.ADMIN));
    lenient().when(obtainCard.createCard(any()))
        .thenReturn(mockCardForCreate(ShortUrlLevel.FEATURE_TEAM));
    // When
    RequestCard requestUser = new PiggyurlDomain(obtainUser, obtainCard);
    Card cardInfo = requestUser
        .updateCard("ashish.raj", mockCardForCreate(ShortUrlLevel.USER).get());
    assertThat(cardInfo).isNotNull();
    assertThat(cardInfo).extracting("createdUserName", "approved")
        .contains("ashish.raj", "Y");
  }

  @Test
  @DisplayName("user should to get exception for update card when wrong user passed")
  public void userShouldGetExceptionForUpdateCardWhenWrongUserPassed(
      @Mock ObtainUser obtainUser, @Mock ObtainCard obtainCard) {
  /*
      RequestCard    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainCard     - right side port
   */
    lenient().when(obtainUser.getUserByUserName("ashish.raj"))
        .thenReturn(Optional.empty());
    // When
    RequestCard requestUser = new PiggyurlDomain(obtainUser, obtainCard);
    Assertions.assertThrows(CommonException.class, () -> requestUser
        .updateCard("ashish.raj", mockCardForCreate(ShortUrlLevel.USER).get()));
  }

  @Test
  @DisplayName("user should to get exception for update card when user is not admin")
  public void userShouldGetExceptionForUpdateCardWhenUserIsNotAdmin(
      @Mock ObtainUser obtainUser, @Mock ObtainCard obtainCard) {
  /*
      RequestCard    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainCard     - right side port
   */
    lenient().when(obtainUser.getUserByUserName("ashish.raj"))
        .thenReturn(mockUser(UserRightLevel.USER));
    // When
    RequestCard requestUser = new PiggyurlDomain(obtainUser, obtainCard);
    Assertions.assertThrows(CommonException.class, () -> requestUser
        .updateCard("ashish.raj", mockCardForCreate(ShortUrlLevel.USER).get()));
  }

  @Test
  @DisplayName("user should to get exception for update card when ports not available")
  public void userShouldGetExceptionForUpdateCardWhenPortsNotAvailable(
      @Mock ObtainUser obtainUser, @Mock ObtainCard obtainCard) {
  /*
      RequestCard    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainCard     - right side port
   */

    // When
    RequestCard requestUser = new PiggyurlDomain();
    Assertions.assertThrows(CommonException.class, () -> requestUser
        .updateCard("ashish.raj", mockCardForCreate(ShortUrlLevel.USER).get()));
  }

  @Test
  @DisplayName("user should able to delete card successfully from hard coded")
  public void userShouldAbleToDeleteCardSuccessfullyFromHardCoded(
      @Mock ObtainUser obtainUser, @Mock ObtainCard obtainCard) {
  /*
      RequestCard    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainCard     - right side port
   */
    lenient().when(obtainUser.getUserByUserName("ashish.raj"))
        .thenReturn(mockUser(UserRightLevel.ADMIN));
    // When
    RequestCard requestUser = new PiggyurlDomain(obtainUser, obtainCard);
    requestUser
        .deleteCard("ashish.raj", 1l);
    verify(obtainCard).deleteCard(1l);
  }

  @Test
  @DisplayName("user should to get exception for delete card when wrong user passed")
  public void userShouldGetExceptionForDeleteCardWhenWrongUserPassed(
      @Mock ObtainUser obtainUser, @Mock ObtainCard obtainCard) {
  /*
      RequestCard    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainCard     - right side port
   */
    lenient().when(obtainUser.getUserByUserName("ashish.raj"))
        .thenReturn(Optional.empty());
    // When
    RequestCard requestUser = new PiggyurlDomain(obtainUser, obtainCard);
    Assertions.assertThrows(CommonException.class, () -> requestUser
        .deleteCard("ashish.raj", 1l));
  }

  @Test
  @DisplayName("user should to get exception for delete card when user is not admin")
  public void userShouldGetExceptionForDeleteCardWhenUserIsNotAdmin(
      @Mock ObtainUser obtainUser, @Mock ObtainCard obtainCard) {
  /*
      RequestCard    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainCard     - right side port
   */
    lenient().when(obtainUser.getUserByUserName("ashish.raj"))
        .thenReturn(mockUser(UserRightLevel.USER));
    // When
    RequestCard requestUser = new PiggyurlDomain(obtainUser, obtainCard);
    Assertions.assertThrows(CommonException.class, () -> requestUser
        .deleteCard("ashish.raj", 1l));
  }

  @Test
  @DisplayName("user should to get exception for delete card when ports not available")
  public void userShouldGetExceptionForDeleteCardWhenPortsNotAvailable(
      @Mock ObtainUser obtainUser, @Mock ObtainCard obtainCard) {
  /*
      RequestCard    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainCard     - right side port
   */

    // When
    RequestCard requestUser = new PiggyurlDomain();
    Assertions.assertThrows(CommonException.class, () -> requestUser
        .deleteCard("ashish.raj", 1l));
  }

  @Test
  @DisplayName("user should able to delete card successfully from hard coded")
  public void userShouldApproveCardSuccessfullyFromHardCoded(
      @Mock ObtainUser obtainUser, @Mock ObtainCard obtainCard) {
  /*
      RequestCard    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainCard     - right side port
   */
    lenient().when(obtainUser.getUserByUserName("ashish.raj"))
        .thenReturn(mockUser(UserRightLevel.ADMIN));
    lenient().when(obtainCard.getCardUpdateByUserName("ashish.raj"))
        .thenReturn(mockCardUpdateForApprove());
    lenient().when(obtainCard.getCardDetailById(1l))
        .thenReturn(mockCardForCreate(ShortUrlLevel.TRIBE));
    lenient().when(obtainCard.createCard(mockCardForCreate(ShortUrlLevel.TRIBE).get()))
        .thenReturn(mockCardForCreate(ShortUrlLevel.TRIBE));
    // When
    RequestCard requestCard = new PiggyurlDomain(obtainUser, obtainCard);
    List<Card> cards = requestCard
        .approveCard("ashish.raj");
    verify(obtainCard).getCardUpdateByUserName("ashish.raj");
    assertThat(cards).isNotNull();
    assertThat(cards).extracting("createdUserName", "approved")
        .contains(tuple("ashish.raj", "Y"));
  }

  @Test
  @DisplayName("user should to get exception for approve card when wrong user passed")
  public void userShouldGetExceptionForApproveCardWhenWrongUserPassed(
      @Mock ObtainUser obtainUser, @Mock ObtainCard obtainCard) {
  /*
      RequestCard    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainCard     - right side port
   */
    lenient().when(obtainUser.getUserByUserName("ashish.raj"))
        .thenReturn(Optional.empty());
    // When
    RequestCard requestUser = new PiggyurlDomain(obtainUser, obtainCard);
    Assertions.assertThrows(CommonException.class, () -> requestUser
        .approveCard("ashish.raj"));
  }

  @Test
  @DisplayName("user should to get exception for approve card when user is not admin")
  public void userShouldGetExceptionForApproveCardWhenUserIsNotAdmin(
      @Mock ObtainUser obtainUser, @Mock ObtainCard obtainCard) {
  /*
      RequestCard    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainCard     - right side port
   */
    lenient().when(obtainUser.getUserByUserName("ashish.raj"))
        .thenReturn(mockUser(UserRightLevel.USER));
    // When
    RequestCard requestUser = new PiggyurlDomain(obtainUser, obtainCard);
    Assertions.assertThrows(CommonException.class, () -> requestUser
        .approveCard("ashish.raj"));
  }

  @Test
  @DisplayName("user should to get exception for approve card when ports not available")
  public void userShouldGetExceptionForApproveCardWhenPortsNotAvailable(
      @Mock ObtainUser obtainUser, @Mock ObtainCard obtainCard) {
  /*
      RequestCard    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainCard     - right side port
   */

    // When
    RequestCard requestUser = new PiggyurlDomain();
    Assertions.assertThrows(CommonException.class, () -> requestUser
        .approveCard("ashish.raj"));
  }

  private Optional<User> mockUser(final UserRightLevel rightLevel) {
    return Optional
        .of(User.builder().id(1l).userName("ashish.raj").password("ashish@123").firstName("Ashish")
            .lastName("Raj").mail("er.ashishraj2010@gmail.com").rightLevel(rightLevel).groupFtId(1l)
            .groupTbId(2l).createdDate(
                LocalDate.now()).createdByUserId(1l).build());
  }

  private List<CardUpdate> mockCardUpdateForApprove() {
    return Arrays.asList(CardUpdate.builder().id(1l).cardId(1l).afterUpdate("AfterUpdate")
        .beforeUpdate("BeforeUpdate").build());
  }

  private Optional<User> mockUserForCreateUser(final UserRightLevel rightLevel) {
    return Optional
        .of(User.builder().id(1l).userName("ashish.raj1").password("ashish@123").firstName("Ashish")
            .lastName("Raj").mail("er.ashishraj2010@gmail.com").rightLevel(rightLevel).groupFtId(1l)
            .groupTbId(2l).createdDate(
                LocalDate.now()).createdByUserId(1l).build());
  }

  private Optional<User> mockUserForGroup(final UserRightLevel rightLevel) {
    return Optional
        .of(User.builder().id(1l).userName("ashish.raj").password("ashish@123").firstName("Ashish")
            .lastName("Raj").mail("er.ashishraj2010@gmail.com").rightLevel(rightLevel).createdDate(
                LocalDate.now()).createdByUserId(1l).build());
  }

  private Optional<Group> mockGroup(final String groupName, final UserGroupType groupType) {
    return Optional.of(Group.builder().id(1l).name(groupName).type(groupType).build());
  }

  private Optional<Card> mockCardForCreate(final ShortUrlLevel shortUrlLevel) {
    return Optional.of(Card.builder().id(1l).urlOriginal(ORIGINAL_URL)
        .urlShort(resolveShortenUrl(shortUrlLevel))
        .description("Description").createdUserName("ashish.raj").
            approvedUserName("ashish.raj").approved("Y").groupId(1l)
        .creationDate(LocalDate.now()).expirationDate(LocalDate.now().plusDays(30)).build());
  }

  private String resolveShortenUrl(final ShortUrlLevel shortUrlLevel) {
    String randomString = "tinyUrl123";
    String defaultUrl = "http://PiggyUrl/tiny/" + randomString;
    switch (shortUrlLevel) {
      case TRIBE:
        return "http://PiggyUrl/" + "Tribe1" + "/"
            + randomString;
      case FEATURE_TEAM:
        return "http://PiggyUrl/" + "FeatureTeam1" + "/"
            + randomString;
      case USER:
        return "http://PiggyUrl/" + "ashish.raj" + "/" + randomString;
      case NONE:
        return defaultUrl;
    }
    return defaultUrl;
  }
}
