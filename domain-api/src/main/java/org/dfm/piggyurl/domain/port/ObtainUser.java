package org.dfm.piggyurl.domain.port;

import org.dfm.piggyurl.domain.common.UserGroupType;
import org.dfm.piggyurl.domain.model.Group;
import org.dfm.piggyurl.domain.model.User;

import java.util.Optional;

public interface ObtainUser {

    default Optional<User> getUserLoginDetail(final String userName, final String password) {
        return Optional.empty();
    }

    default Optional<Group> createGroup(final String groupName, final UserGroupType groupType) {
        return Optional.empty();
    }

    default Optional<Group> getGroupByNameAndType(final String groupName, final UserGroupType groupType) {
        return Optional.empty();
    }

    default Optional<Group> getGroupByIdAndType(final Long groupId, final UserGroupType groupType) {
        return Optional.empty();
    }

    default Optional<Group> getGroupById(final Long groupId) {
        return Optional.empty();
    }

    default Optional<User> createUser(final User userToBeCreated) {
        return Optional.empty();
    }

    default Optional<User> getUserByUserName(final String userName) {
        return Optional.empty();
    }
    default Optional<User> getUserByTribeId(final Long tribeId) {
        return Optional.empty();
    }
    default Optional<User> getUserByFeatureTeamId(final Long tribeId) {
        return Optional.empty();
    }
}
