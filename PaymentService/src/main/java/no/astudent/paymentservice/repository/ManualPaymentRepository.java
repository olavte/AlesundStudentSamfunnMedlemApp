package no.astudent.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import no.astudent.paymentservice.domain.ManualPayment;

public interface ManualPaymentRepository extends JpaRepository<ManualPayment, Long> {

    List<ManualPayment> findByMemberAndCreatedGreaterThan(Long member, Date semesterStart);

    default ManualPayment saveManualPayment(ManualPayment manualPayment) {
        manualPayment.setUpdated(java.sql.Date.valueOf(LocalDateTime.now().toLocalDate()));
        return save(manualPayment);
    }
}