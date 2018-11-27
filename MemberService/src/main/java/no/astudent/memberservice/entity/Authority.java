package no.astudent.memberservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
public class Authority {

    @Id
    @JsonIgnore
    @SequenceGenerator(name = "pk_authority", sequenceName = "authority_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_authority")
    private long idAuthority;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthorityType authority;
}
