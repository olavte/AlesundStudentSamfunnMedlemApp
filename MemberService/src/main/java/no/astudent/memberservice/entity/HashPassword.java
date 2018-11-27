package no.astudent.memberservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class HashPassword {
    @Id
    @SequenceGenerator(name = "pk_hash_password", sequenceName = "hash_password_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_hash_password")
    Long idHash;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    Date created;

    @Column(nullable = false)
    @UpdateTimestamp
    Date updated;

    @NotNull
    @NotEmpty
    String verificationHash;

    @NotNull
    boolean expired = false;

    @NotNull
    private Long idMember;

    public HashPassword(Long idMember) {
        this.idMember = idMember;
        this.verificationHash = java.util.UUID.randomUUID().toString();
    }
}
