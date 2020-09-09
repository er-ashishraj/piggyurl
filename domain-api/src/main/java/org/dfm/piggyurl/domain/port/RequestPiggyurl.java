package org.dfm.piggyurl.domain.port;

import org.dfm.piggyurl.domain.model.Piggyurl;
import org.dfm.piggyurl.domain.model.PiggyurlInfo;

public interface RequestPiggyurl {

    PiggyurlInfo getPiggyurls();

    Piggyurl getPiggyurlByCode(String code);
}
