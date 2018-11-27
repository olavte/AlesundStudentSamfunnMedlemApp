package no.astudent.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import no.astudent.paymentservice.domain.VippsPayment;

public interface VippsPaymentRepository extends JpaRepository<VippsPayment, Long> {

    List<VippsPayment> findByMemberAndVerifiedAndCreatedGreaterThanEqual(Long member, boolean verified, Date semesterStart);

    default VippsPayment saveVippsPayment(VippsPayment vippsPayment) {
        vippsPayment.setUpdated(java.sql.Date.valueOf(LocalDateTime.now().toLocalDate()));
        return save(vippsPayment);
    }

    VippsPayment findFirstByPhoneNumberAndVerifiedOrderByCreatedDesc(int phoneNumber, boolean verified);

    List<VippsPayment> findByMember(Long id);
}
