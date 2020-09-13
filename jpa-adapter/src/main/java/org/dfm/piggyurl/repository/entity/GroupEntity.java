package org.dfm.piggyurl.repository.entity;

import java.time.LocalDate;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dfm.piggyurl.domain.model.Group;
import org.dfm.piggyurl.domain.common.UserGroupType;

@Table(name = "T_GROUP")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_T_GROUP")
    @Column(name = "GROUP_ID")
    private Long id;

    @Column(name = "GROUP_NAME")
    private String name;

    @Column(name = "GROUP_TYPE")
    private String type;

    public Group toGroupModel() {
        return Group.builder().id(id).name(name).type(UserGroupType.valueOf(type)).build();
    }
}
