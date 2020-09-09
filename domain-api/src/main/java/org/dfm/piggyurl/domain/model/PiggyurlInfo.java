package org.dfm.piggyurl.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PiggyurlInfo {

    private List<Piggyurl> piggyurls;
}
