package org.dfm.piggyurl.domain.port;

import org.dfm.piggyurl.domain.common.UserGroupType;
import org.dfm.piggyurl.domain.model.Group;
import org.dfm.piggyurl.domain.model.User;

public interface RequestUser {

    User getUserLoginDetail(final String userName, final String password);

    Group createGroup(final String userNameOfCreator, final Group group);

    User createUser(final String userNameOfCreator, final User user);
}
