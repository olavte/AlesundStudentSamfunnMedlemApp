package no.astudent.memberservice.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Member {

    public Member(String name, String email, String password, String phone, String birth, boolean verified) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birth = birth;
        this.phone = phone;
        this.verified = verified;
    }

    @Id
    @SequenceGenerator(name = "pk_member", sequenceName = "member_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_member")
    Long idMember;

    @NotNull
    @NotEmpty
    String name;

    String password;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    String email;

    @NotNull
    @NotEmpty
    String phone;

    @NotNull
    @NotEmpty
    String birth;

    @NotNull
    boolean verified;

    @NotNull
    @Enumerated(EnumType.STRING)
    Role role;

    @NotNull
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "idAuthority")
    private List<Authority> authorities;
}
