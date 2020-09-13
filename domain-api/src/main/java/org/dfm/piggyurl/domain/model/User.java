package org.dfm.piggyurl.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dfm.piggyurl.domain.common.UserRightLevel;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String mail;
    private UserRightLevel rightLevel;
    private String groupFtName;
    private String groupTbName;
    private Long groupFtId;
    private Long groupTbId;
    private LocalDate createdDate;
    private Long createdByUserId;
}
