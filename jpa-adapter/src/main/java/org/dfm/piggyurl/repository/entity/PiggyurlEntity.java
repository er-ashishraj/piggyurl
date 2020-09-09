package org.dfm.piggyurl.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dfm.piggyurl.domain.model.Piggyurl;

import javax.persistence.*;

@Table(name = "T_PIGGYURL")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PiggyurlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_T_PIGGYURL")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "DESCRIPTION")
    private String description;

    public Piggyurl toModel() {
        return Piggyurl.builder().id(id).code(code).description(description).build();
    }
}
