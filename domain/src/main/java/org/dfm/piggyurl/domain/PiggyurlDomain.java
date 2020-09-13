package org.dfm.piggyurl.domain;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.CREATOR_USER_NOT_ADMIN;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.CREATOR_USER_NOT_FOUND;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.GROUP_CANNOT_CREATE;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.GROUP_NOT_FOUND;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.PORTS_NOT_DEFINED;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.USER_ALREADY_PRESENT;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.USER_CANNOT_CREATE;
import static org.dfm.piggyurl.domain.common.ExceptionConstants.USER_IS_ADMIN_FOR_OTHER_GROUP;

import java.time.LocalDate;
import java.util.Optional;
import org.dfm.piggyurl.domain.common.UserGroupType;
import org.dfm.piggyurl.domain.common.UserRightLevel;
import org.dfm.piggyurl.domain.exception.CommonException;
import org.dfm.piggyurl.domain.exception.PiggyurlNotFoundException;
import org.dfm.piggyurl.domain.exception.UserNotAuthorizedException;
import org.dfm.piggyurl.domain.model.Group;
import org.dfm.piggyurl.domain.model.Piggyurl;
import org.dfm.piggyurl.domain.model.PiggyurlInfo;
import org.dfm.piggyurl.domain.model.User;
import org.dfm.piggyurl.domain.port.ObtainPiggyurl;
import org.dfm.piggyurl.domain.port.ObtainUser;
import org.dfm.piggyurl.domain.port.RequestPiggyurl;
import org.dfm.piggyurl.domain.port.RequestUser;

public class PiggyurlDomain implements RequestPiggyurl, RequestUser {

  private ObtainPiggyurl obtainPiggyurl;
  private ObtainUser obtainUser;

  public PiggyurlDomain() {
    this(new ObtainPiggyurl() {
    });
  }

  public PiggyurlDomain(ObtainPiggyurl obtainPiggyurl) {
    this.obtainPiggyurl = obtainPiggyurl;
  }

  public PiggyurlDomain(ObtainUser obtainUser) {
    this.obtainUser = obtainUser;
  }

  @Override
  public PiggyurlInfo getPiggyurls() {
    if (isNull(obtainPiggyurl)) {
      new UserNotAuthorizedException(PORTS_NOT_DEFINED);
    }
    return PiggyurlInfo.builder().piggyurls(obtainPiggyurl.getAllPiggyurls()).build();
  }

  @Override
  public Piggyurl getPiggyurlByCode(String code) {
    if (isNull(obtainPiggyurl)) {
      new UserNotAuthorizedException(PORTS_NOT_DEFINED);
    }
    Optional<Piggyurl> piggyurl = obtainPiggyurl.getPiggyurlByCode(code);
    return piggyurl.orElseThrow(() -> new PiggyurlNotFoundException(code));
  }

  @Override
  public User getUserLoginDetail(final String userName, final String password) {
    if (isNull(obtainUser)) {
      new UserNotAuthorizedException(PORTS_NOT_DEFINED);
    }
    Optional<User> getUserLoginDetail = obtainUser.getUserLoginDetail(userName, password);
    return getUserLoginDetail.orElseThrow(() -> new UserNotAuthorizedException(userName));
  }

  @Override
  public Group createGroup(final String userNameOfCreator, final Group group) {
    if (isNull(obtainUser)) {
      new UserNotAuthorizedException(PORTS_NOT_DEFINED);
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
      new UserNotAuthorizedException(PORTS_NOT_DEFINED);
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
      new UserNotAuthorizedException(PORTS_NOT_DEFINED);
    }
    if (nonNull(groupName) && nonNull(groupType)) {
      Optional<Group> groupDetail = obtainUser.getGroupByNameAndType(groupName, groupType);
      return groupDetail.orElseThrow(() -> new CommonException(groupName + " " + GROUP_NOT_FOUND));
    }
    return null;
  }
}
