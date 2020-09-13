package org.dfm.piggyurl.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dfm.piggyurl.domain.common.UserGroupType;
import org.dfm.piggyurl.domain.common.UserRightLevel;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    private Long id;
    private String name;
    private UserGroupType type;
}
