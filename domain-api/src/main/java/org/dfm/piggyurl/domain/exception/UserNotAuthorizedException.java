package org.dfm.piggyurl.domain.exception;

public class UserNotAuthorizedException extends RuntimeException {

    public UserNotAuthorizedException(final String userName) {
        super("User " + userName + " is not authorized to login");
    }
}

