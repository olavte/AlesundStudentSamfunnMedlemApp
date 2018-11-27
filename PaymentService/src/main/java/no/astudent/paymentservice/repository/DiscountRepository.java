package no.astudent.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import no.astudent.paymentservice.domain.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

    List<Discount> findByCodeAndStartDateLessThanAndEndDateGreaterThan(String code, Date now, Date now2);

    default Discount saveDiscount(Discount discount) {
        discount.setUpdated(java.sql.Date.valueOf(LocalDateTime.now().toLocalDate()));
        return save(discount);
    }
}