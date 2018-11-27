package no.astudent.memberservice.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class HashEmail {
    @Id
    @SequenceGenerator(name = "pk_hash_email", sequenceName = "hash_email_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_hash_email")
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

    public HashEmail(Long idMember) {
        this.idMember = idMember;
        this.verificationHash = java.util.UUID.randomUUID().toString();
    }
}
