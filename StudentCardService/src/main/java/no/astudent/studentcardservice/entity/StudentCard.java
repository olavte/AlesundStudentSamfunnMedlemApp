package no.astudent.studentcardservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"idMember"})})
public class StudentCard {

    public StudentCard(Long idMember, String imageBlob) {
        this.idMember = idMember;
        this.imageBlob = imageBlob;
        this.status = Status.PENDING;
    }

    @Id
    @SequenceGenerator(name="pk_card",sequenceName="card_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_card")
    Long idCard;

    @NotNull
    @Column(unique = true)
    Long idMember;

    @Lob
    String imageBlob;

    @Enumerated(EnumType.STRING)
    Status status;

    String verifiedBy;
}
