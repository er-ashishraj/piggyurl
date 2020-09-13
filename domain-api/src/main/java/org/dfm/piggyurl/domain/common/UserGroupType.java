package org.dfm.piggyurl.domain.common;

public enum UserGroupType {
    FEATURE_TEAM("FEATURE_TEAM"), TRIBE("TRIBE");
    private String userGroupType;

    UserGroupType(final String userGroupType) {
        this.userGroupType = userGroupType;
    }

    public String getUserGroupType() {
        return this.userGroupType;
    }
}
