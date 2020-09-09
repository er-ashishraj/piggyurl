package org.dfm.piggyurl.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Piggyurl {

    private Long id;
    private String code;
    private String description;
}
