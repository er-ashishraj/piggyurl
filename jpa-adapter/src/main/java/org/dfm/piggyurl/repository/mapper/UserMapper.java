package org.dfm.piggyurl.repository.mapper;

import static java.util.Objects.nonNull;

import org.dfm.piggyurl.domain.common.UserRightLevel;
import org.dfm.piggyurl.domain.model.User;
import org.dfm.piggyurl.repository.entity.UserEntity;

public class UserMapper {

  public UserEntity mapUserToUserEntity(final User user) {
    return nonNull(user) ? UserEntity.builder().id(user.getId()).
        userName(user.getUserName()).
        password(user.getPassword()).
        firstName(user.getFirstName()).
        lastName(user.getLastName()).
        mail(user.getMail()).
        rightLevel(user.getRightLevel().name()).
        groupFtId(user.getGroupFtId()).
        groupTbId(user.getGroupTbId()).
        createdDate(user.getCreatedDate()).
        createdByUserId(user.getCreatedByUserId()).build() : null;
  }

  public User mapUserEntityToUser(final UserEntity userEntity) {
    return nonNull(userEntity) ? User.builder().id(userEntity.getId()).
        userName(userEntity.getUserName()).
        password(userEntity.getPassword()).
        firstName(userEntity.getFirstName()).
        lastName(userEntity.getLastName()).
        mail(userEntity.getMail()).
        rightLevel(UserRightLevel.valueOf(userEntity.getRightLevel())).
        groupFtId(userEntity.getGroupFtId()).
        groupTbId(userEntity.getGroupTbId()).
        createdDate(userEntity.getCreatedDate()).
        createdByUserId(userEntity.getCreatedByUserId()).build() : null;
  }

}
