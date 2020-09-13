package org.dfm.piggyurl;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;
import org.dfm.piggyurl.domain.PiggyurlDomain;
import org.dfm.piggyurl.domain.common.UserGroupType;
import org.dfm.piggyurl.domain.common.UserRightLevel;
import org.dfm.piggyurl.domain.model.Group;
import org.dfm.piggyurl.domain.model.User;
import org.dfm.piggyurl.domain.port.ObtainUser;
import org.dfm.piggyurl.domain.port.RequestUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserAcceptanceTest {

  @Test
  @DisplayName("user should login successfully from hard coded")
  public void userShouldLoginSuccessfullyFromHardCoded(@Mock ObtainUser obtainUser) {
  /*
      RequestUser    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainUser     - right side port
   */
    Mockito.lenient().when(obtainUser.getUserLoginDetail("ashish.raj", "ashish@123"))
        .thenReturn(mockUser(UserRightLevel.ADMIN));
    // When
    RequestUser requestUser = new PiggyurlDomain(obtainUser);
    User userInfo = requestUser.getUserLoginDetail("ashish.raj", "ashish@123");
    assertThat(userInfo).isNotNull();
    assertThat(userInfo).extracting("userName", "password")
        .contains("ashish.raj", "ashish@123");
  }

  @Test
  @DisplayName("user should create feature team group successfully from hard coded")
  public void userShouldCreateFeatureTeamGroupSuccessfullyFromHardCoded(
      @Mock ObtainUser obtainUser) {
  /*
      RequestUser    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainUser     - right side port
   */
    Mockito.lenient().when(obtainUser.createGroup("FeatureTeam1", UserGroupType.FEATURE_TEAM))
        .thenReturn(mockGroup("FeatureTeam1", UserGroupType.FEATURE_TEAM));
    Mockito.lenient().when(obtainUser.getUserByUserName("ashish.raj"))
        .thenReturn(mockUserForGroup(UserRightLevel.ADMIN));
    // When
    RequestUser requestUser = new PiggyurlDomain(obtainUser);
    Group groupInfo = requestUser
        .createGroup("ashish.raj", mockGroup("FeatureTeam1", UserGroupType.FEATURE_TEAM).get());
    assertThat(groupInfo).isNotNull();
    assertThat(groupInfo).extracting("name", "type")
        .contains("FeatureTeam1", UserGroupType.FEATURE_TEAM);
  }

  @Test
  @DisplayName("user should create tribe group successfully from hard coded")
  public void userShouldCreateTribeGroupSuccessfullyFromHardCoded(@Mock ObtainUser obtainUser) {
  /*
      RequestUser    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainUser     - right side port
   */
    Mockito.lenient().when(obtainUser.createGroup("Tribe1", UserGroupType.TRIBE))
        .thenReturn(mockGroup("Tribe1", UserGroupType.TRIBE));
    Mockito.lenient().when(obtainUser.getUserByUserName("ashish.raj"))
        .thenReturn(mockUserForGroup(UserRightLevel.ADMIN));
    // When
    RequestUser requestUser = new PiggyurlDomain(obtainUser);
    Group groupInfo = requestUser
        .createGroup("ashish.raj", mockGroup("Tribe1", UserGroupType.TRIBE).get());
    assertThat(groupInfo).isNotNull();
    assertThat(groupInfo).extracting("name", "type")
        .contains("Tribe1", UserGroupType.TRIBE);
  }

  @Test
  @DisplayName("user should create user successfully from hard coded")
  public void userShouldCreateUserSuccessfullyFromHardCoded(@Mock ObtainUser obtainUser) {
  /*
      RequestUser    - left side port
      PiggyurlDomain - hexagon (domain)
      ObtainUser     - right side port
   */
    Mockito.lenient().when(obtainUser.createUser(mockUserForCreateUser(UserRightLevel.ADMIN).get()))
        .thenReturn(mockUser(UserRightLevel.ADMIN));
    Mockito.lenient().when(obtainUser.getUserByUserName("ashish.raj"))
        .thenReturn(mockUser(UserRightLevel.ADMIN));
    // When
    RequestUser requestUser = new PiggyurlDomain(obtainUser);
    User userInfo = requestUser
        .createUser("ashish.raj", mockUserForCreateUser(UserRightLevel.ADMIN).get());
    assertThat(userInfo).isNotNull();
    assertThat(userInfo).extracting("userName", "password")
        .contains("ashish.raj", "ashish@123");
  }

  private Optional<User> mockUser(final UserRightLevel rightLevel) {
    return Optional
        .of(User.builder().id(1l).userName("ashish.raj").password("ashish@123").firstName("Ashish")
            .lastName("Raj").mail("er.ashishraj2010@gmail.com").rightLevel(rightLevel).groupFtId(1l)
            .groupTbId(2l).createdDate(
                LocalDate.now()).createdByUserId(1l).build());
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
}
