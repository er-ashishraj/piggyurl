package org.dfm.piggyurl.rest;

import org.dfm.piggyurl.domain.model.Piggyurl;
import org.dfm.piggyurl.domain.model.PiggyurlInfo;
import org.dfm.piggyurl.domain.port.RequestPiggyurl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/piggyurls")
public class PiggyurlResource {

    @Autowired
    @Qualifier("requestPiggyurl")
    private RequestPiggyurl requestPiggyurl;

    @GetMapping
    public ResponseEntity<PiggyurlInfo> getPiggyurls() {
        return ResponseEntity.ok(requestPiggyurl.getPiggyurls());
    }

    @GetMapping("/{code}")
    public ResponseEntity<Piggyurl> getPiggyurlByCode(@PathVariable String code) {
        return ResponseEntity.ok(requestPiggyurl.getPiggyurlByCode(code));
    }
}
