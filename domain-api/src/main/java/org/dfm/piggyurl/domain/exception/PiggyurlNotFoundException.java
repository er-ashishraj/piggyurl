package org.dfm.piggyurl.domain.exception;

public class PiggyurlNotFoundException extends RuntimeException {

    public PiggyurlNotFoundException(String code) {
        super("Piggyurl with code " + code + " does not exist");
    }
}

