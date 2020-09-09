package org.dfm.piggyurl.domain;

import org.dfm.piggyurl.domain.exception.PiggyurlNotFoundException;
import org.dfm.piggyurl.domain.model.Piggyurl;
import org.dfm.piggyurl.domain.model.PiggyurlInfo;
import org.dfm.piggyurl.domain.port.ObtainPiggyurl;
import org.dfm.piggyurl.domain.port.RequestPiggyurl;

import java.util.Optional;

public class PiggyurlDomain implements RequestPiggyurl {

    private ObtainPiggyurl obtainPiggyurl;

    public PiggyurlDomain() {
        this(new ObtainPiggyurl() {
        });
    }

    public PiggyurlDomain(ObtainPiggyurl obtainPiggyurl) {
        this.obtainPiggyurl = obtainPiggyurl;
    }

    @Override
    public PiggyurlInfo getPiggyurls() {
        return PiggyurlInfo.builder().piggyurls(obtainPiggyurl.getAllPiggyurls()).build();
    }

    @Override
    public Piggyurl getPiggyurlByCode(String code) {
        Optional<Piggyurl> piggyurl = obtainPiggyurl.getPiggyurlByCode(code);
        return piggyurl.orElseThrow(() -> new PiggyurlNotFoundException(code));
    }
}
