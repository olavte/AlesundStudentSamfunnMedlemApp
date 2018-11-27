package no.astudent.paymentservice.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.astudent.paymentservice.config.PaymentConfig;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class VippsPayment {
    @Id
    @SequenceGenerator(name = "pk_vipps_payment", sequenceName = "vipps_payment_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_vipps_payment")
    Long paymentId;

    @Setter(AccessLevel.NONE)
    @NotNull
    Integer phoneNumber;

    @NotNull
    boolean verified;

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

    public void setPhoneNumber(int phoneNumber) {
        if (phoneNumber < 99999999 && phoneNumber > 10000000) {
            this.phoneNumber = phoneNumber;
        }
    }

    // Generate an orderId which consists of orgNumber plus paymentId with 11 digits (leading zeros)
    public String getOrderId(PaymentConfig paymentConfig) {
        return paymentConfig.getOrgNumber() + String.format("%07d", getPaymentId()) + created.getTime();
    }
}
