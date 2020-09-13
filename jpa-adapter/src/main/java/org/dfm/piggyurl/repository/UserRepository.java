package org.dfm.piggyurl.repository;

import static java.util.Objects.nonNull;

import java.util.Optional;
import org.dfm.piggyurl.domain.common.UserGroupType;
import org.dfm.piggyurl.domain.model.Group;
import org.dfm.piggyurl.domain.model.User;
import org.dfm.piggyurl.domain.port.ObtainUser;
import org.dfm.piggyurl.repository.dao.GroupDao;
import org.dfm.piggyurl.repository.dao.UserDao;
import org.dfm.piggyurl.repository.entity.GroupEntity;
import org.dfm.piggyurl.repository.entity.UserEntity;
import org.dfm.piggyurl.repository.mapper.UserMapper;

public class UserRepository implements ObtainUser {

  private UserDao userDao;
  private GroupDao groupDao;

  public UserRepository(final UserDao userDao, final GroupDao groupDao) {
    this.userDao = userDao;
    this.groupDao = groupDao;
  }

  @Override
  public Optional<User> getUserLoginDetail(final String userName, final String password) {
    Optional<UserEntity> userEntity = userDao.findByUserNameAndPassword(userName, password);
    return userEntity.map(UserEntity::toUserModel);
  }

  @Override
  public Optional<Group> createGroup(final String groupName, final UserGroupType groupType) {
    GroupEntity groupEntity = groupDao
        .save(GroupEntity.builder().name(groupName).type(groupType.name()).build());
    return nonNull(groupEntity) ? Optional
        .of(Group.builder().id(groupEntity.getId()).name(groupEntity.getName())
            .type(UserGroupType.valueOf(groupEntity.getType())).build()) : null;
  }

  @Override
  public Optional<User> createUser(User userToBeCreated) {
    UserMapper userMapper = new UserMapper();
    UserEntity createdUserDetail = userDao.save(userMapper.mapUserToUserEntity(userToBeCreated));
    return nonNull(createdUserDetail) ? Optional
        .of(userMapper.mapUserEntityToUser(createdUserDetail)) : null;
  }

  @Override
  public Optional<User> getUserByUserName(String userName) {
    Optional<UserEntity> userEntity = userDao.findByUserName(userName);
    return userEntity.map(UserEntity::toUserModel);
  }

  @Override
  public Optional<Group> getGroupByNameAndType(String groupName,
      UserGroupType groupType) {
    Optional<GroupEntity> groupEntity = groupDao
        .findByNameAndType(groupName, groupType.getUserGroupType());
    return groupEntity.map(GroupEntity::toGroupModel);
  }
}