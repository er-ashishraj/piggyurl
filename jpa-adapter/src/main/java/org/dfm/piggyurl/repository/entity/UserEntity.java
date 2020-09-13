package org.dfm.piggyurl.repository.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dfm.piggyurl.domain.common.UserRightLevel;
import org.dfm.piggyurl.domain.model.User;

@Table(name = "T_USER")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_T_USER")
  @Column(name = "USER_ID")
  private Long id;

  @Column(name = "USER_NAME")
  private String userName;

  @Column(name = "USER_PASSWORD")
  private String password;

  @Column(name = "USER_FIRSTNAME")
  private String firstName;

  @Column(name = "USER_LASTNAME")
  private String lastName;

  @Column(name = "USER_MAIL")
  private String mail;

  @Column(name = "USER_RIGHT_LEVEL")
  private String rightLevel;

  @Column(name = "GROUP_FT_ID")
  private Long groupFtId;

  @Column(name = "GROUP_TB_ID")
  private Long groupTbId;

  @Column(name = "USER_CREATION_DATE")
  private LocalDate createdDate;

  @Column(name = "CREATED_BY_USER_ID")
  private Long createdByUserId;

  public User toUserModel() {
    return User.builder().id(id).userName(userName).password(password).firstName(firstName).
        lastName(lastName).mail(mail).rightLevel(UserRightLevel.valueOf(rightLevel))
        .groupFtId(groupFtId).groupTbId(groupTbId).createdDate(createdDate)
        .createdByUserId(createdByUserId).build();
  }
}
