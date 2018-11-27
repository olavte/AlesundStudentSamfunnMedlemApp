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
public class Discount {

    @Id
    @SequenceGenerator(name = "pk_discount", sequenceName = "discount_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_discount")
    Long discountId;

    @NotNull
    String code;

    @NotNull
    int amount;

    @NotNull
    Date startDate;

    @NotNull
    Date endDate;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    Date created;

    @Column(nullable = false)
    @UpdateTimestamp
    Date updated;

    public Discount(@NotNull String code, int amount, @NotNull Date startDate, @NotNull Date endDate) {
        this.code = code.toUpperCase();
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
