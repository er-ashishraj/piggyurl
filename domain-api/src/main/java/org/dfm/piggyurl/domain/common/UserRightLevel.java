package org.dfm.piggyurl.domain.common;

public enum UserRightLevel {
    ADMIN("ADMIN"), USER("USER");
    private String userRightLevel;

    UserRightLevel(final String rightLevel) {
        this.userRightLevel = userRightLevel;
    }

    public String getUserRightLevel() {
        return this.userRightLevel;
    }
}
