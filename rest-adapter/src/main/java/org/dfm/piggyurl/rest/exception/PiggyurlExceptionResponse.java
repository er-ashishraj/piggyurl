package org.dfm.piggyurl.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PiggyurlExceptionResponse {

    private String message;

    private String path;
}
