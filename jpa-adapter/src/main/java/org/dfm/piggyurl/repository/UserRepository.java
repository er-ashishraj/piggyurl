package org.dfm.piggyurl.repository;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;
import org.dfm.piggyurl.domain.common.UserGroupType;
import org.dfm.piggyurl.domain.common.UserRightLevel;
import org.dfm.piggyurl.domain.model.Group;
import org.dfm.piggyurl.domain.model.User;
import org.dfm.piggyurl.domain.port.ObtainUser;
import org.dfm.piggyurl.repository.dao.GroupDao;
import org.dfm.piggyurl.repository.dao.UserDao;
import org.dfm.piggyurl.repository.entity.GroupEntity;
import org.dfm.piggyurl.repository.entity.UserEntity;
import org.dfm.piggyurl.repository.mapper.UserMapper;
import org.springframework.util.CollectionUtils;

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
  public Optional<User> createUser(final User userToBeCreated) {
    UserMapper userMapper = new UserMapper();
    UserEntity createdUserDetail = userDao.save(userMapper.mapUserToUserEntity(userToBeCreated));
    return nonNull(createdUserDetail) ? Optional
        .of(userMapper.mapUserEntityToUser(createdUserDetail)) : null;
  }

  @Override
  public Optional<User> getUserByUserName(final String userName) {
    Optional<UserEntity> userEntity = userDao.findByUserName(userName);
    return userEntity.map(UserEntity::toUserModel);
  }

  @Override
  public Optional<User> getUserByTribeId(final Long tribeId) {
    List<UserEntity> userEntities = userDao.findAllByRightLevelAndGroupTbId(UserRightLevel.ADMIN.name(), tribeId);
    return !CollectionUtils.isEmpty(userEntities)? Optional.of(toUserModel(userEntities.get(0))): Optional.empty();
  }

  @Override
  public Optional<User> getUserByFeatureTeamId(final Long featureTeamId) {
    List<UserEntity> userEntities = userDao.findAllByRightLevelAndGroupFtId(UserRightLevel.ADMIN.name(), featureTeamId);
    return !CollectionUtils.isEmpty(userEntities)? Optional.of(toUserModel(userEntities.get(0))): Optional.empty();
  }

  @Override
  public Optional<Group> getGroupByNameAndType(final String groupName,
      UserGroupType groupType) {
    Optional<GroupEntity> groupEntity = groupDao
        .findByNameAndType(groupName, groupType.getUserGroupType());
    return groupEntity.map(GroupEntity::toGroupModel);
  }

  @Override
  public Optional<Group> getGroupByIdAndType(final Long groupId,
      UserGroupType groupType) {
    Optional<GroupEntity> groupEntity = groupDao
        .findByIdAndType(groupId, groupType.getUserGroupType());
    return groupEntity.map(GroupEntity::toGroupModel);
  }

  @Override
  public Optional<Group> getGroupById(Long groupId) {
    Optional<GroupEntity> groupEntity = groupDao
        .findById(groupId);
    return groupEntity.map(GroupEntity::toGroupModel);
  }

  public User toUserModel(final UserEntity userEntity) {
    return User.builder().id(userEntity.getId()).userName(userEntity.getUserName()).password(userEntity.getPassword()).firstName(userEntity.getFirstName()).
        lastName(userEntity.getLastName()).mail(userEntity.getMail()).rightLevel(UserRightLevel.valueOf(userEntity.getRightLevel()))
        .groupFtId(userEntity.getGroupFtId()).groupTbId(userEntity.getGroupTbId()).createdDate(userEntity.getCreatedDate())
        .createdByUserId(userEntity.getCreatedByUserId()).build();
  }
}