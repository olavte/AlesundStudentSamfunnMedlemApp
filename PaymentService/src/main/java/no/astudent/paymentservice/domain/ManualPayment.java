package no.astudent.paymentservice.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
public class ManualPayment {
    @Id
    @SequenceGenerator(name = "pk_manual_payment", sequenceName = "manual_payment_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_manual_payment")
    Long paymentId;

    @NotNull
    Long member;

    @NotNull
    int membershipCost;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    Date created;

    @Column(nullable = false)
    @UpdateTimestamp
    Date updated;
}
