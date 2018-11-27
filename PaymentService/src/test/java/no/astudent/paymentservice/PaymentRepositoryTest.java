package no.astudent.paymentservice;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import no.astudent.paymentservice.config.PaymentConfig;
import no.astudent.paymentservice.domain.ManualPayment;
import no.astudent.paymentservice.repository.ManualPaymentRepository;
import no.astudent.paymentservice.repository.VippsPaymentRepository;
import no.astudent.paymentservice.utils.DateUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaymentServiceRunner.class)
public class PaymentRepositoryTest {

    @Autowired
    private VippsPaymentRepository vippsPaymentRepository;

    @Autowired
    private ManualPaymentRepository manualPaymentRepository;

    private PaymentConfig paymentConfig = new PaymentConfig();

    @Test
    public void whenOneManualPaymentForMember_afterSemesterStart_onePaymentFound() {
        ManualPayment manualPayment = new ManualPayment();
        manualPayment.setMember(1L);
        manualPayment.setMembershipCost(paymentConfig.getMembershipCost());
        manualPaymentRepository.saveManualPayment(manualPayment);

        ManualPayment manualPayment2 = new ManualPayment();
        manualPayment2.setMember(2L);
        manualPayment2.setMembershipCost(paymentConfig.getMembershipCost());
        manualPaymentRepository.saveManualPayment(manualPayment2);

        Date semesterStart = DateUtils.asDate(LocalDateTime.now().minusDays(1L));
        List<ManualPayment> manualPaymentsFromDatabase = manualPaymentRepository.findByMemberAndCreatedGreaterThan(1L, semesterStart);

        assertNotEquals(0, manualPaymentsFromDatabase.size());
        assertEquals(1, manualPaymentsFromDatabase.size());
        assertNotEquals(2, manualPaymentsFromDatabase.size());
    }

    @Test
    public void whenOneManualPaymentsForMember_beforeSemesterStart_noPaymentFound() {
        ManualPayment manualPayment = new ManualPayment();
        manualPayment.setMember(1L);
        manualPayment.setMembershipCost(paymentConfig.getMembershipCost());
        manualPaymentRepository.saveManualPayment(manualPayment);

        ManualPayment manualPayment2 = new ManualPayment();
        manualPayment2.setMember(2L);
        manualPayment2.setMembershipCost(paymentConfig.getMembershipCost());
        manualPaymentRepository.saveManualPayment(manualPayment2);

        Date semesterStart = DateUtils.asDate(LocalDateTime.now().plusDays(1L));
        List<ManualPayment> manualPaymentsFromDatabase = manualPaymentRepository.findByMemberAndCreatedGreaterThan(1L, semesterStart);

        assertEquals(0, manualPaymentsFromDatabase.size());
        assertNotEquals(1, manualPaymentsFromDatabase.size());
        assertNotEquals(2, manualPaymentsFromDatabase.size());
    }

    @Test
    public void whenFourManualPaymentsForMember_allAfterSemesterStart_fourPaymentsFound() {
        ManualPayment manualPayment = new ManualPayment();
        manualPayment.setMember(1L);
        manualPayment.setMembershipCost(paymentConfig.getMembershipCost());
        manualPaymentRepository.saveManualPayment(manualPayment);

        ManualPayment manualPayment2 = new ManualPayment();
        manualPayment2.setMember(1L);
        manualPayment2.setMembershipCost(paymentConfig.getMembershipCost());
        manualPaymentRepository.saveManualPayment(manualPayment2);

        ManualPayment manualPayment3 = new ManualPayment();
        manualPayment3.setMember(1L);
        manualPayment3.setMembershipCost(paymentConfig.getMembershipCost());
        manualPaymentRepository.saveManualPayment(manualPayment3);

        ManualPayment manualPayment4 = new ManualPayment();
        manualPayment4.setMember(1L);
        manualPayment4.setMembershipCost(paymentConfig.getMembershipCost());
        manualPaymentRepository.saveManualPayment(manualPayment4);

        ManualPayment manualPayment5 = new ManualPayment();
        manualPayment5.setMember(2L);
        manualPayment5.setMembershipCost(paymentConfig.getMembershipCost());
        manualPaymentRepository.saveManualPayment(manualPayment5);

        Date semesterStart = DateUtils.asDate(LocalDateTime.now().minusDays(1L));
        List<ManualPayment> manualPaymentsFromDatabase = manualPaymentRepository.findByMemberAndCreatedGreaterThan(1L, semesterStart);

        assertNotEquals(0, manualPaymentsFromDatabase.size());
        assertNotEquals(1, manualPaymentsFromDatabase.size());
        assertNotEquals(2, manualPaymentsFromDatabase.size());
        assertNotEquals(3, manualPaymentsFromDatabase.size());
        assertEquals(4, manualPaymentsFromDatabase.size());
        assertNotEquals(5, manualPaymentsFromDatabase.size());
        assertEquals(5, manualPaymentRepository.findAll().size());
    }

    @Test
    public void whenThreeNewManualPayments_forOtherMembers_afterSemesterStart_paymentNotFound() {
        ManualPayment manualPayment = new ManualPayment();
        manualPayment.setMember(3L);
        manualPayment.setMembershipCost(paymentConfig.getMembershipCost());
        manualPaymentRepository.saveManualPayment(manualPayment);

        ManualPayment manualPayment2 = new ManualPayment();
        manualPayment2.setMember(6L);
        manualPayment2.setMembershipCost(paymentConfig.getMembershipCost());

        ManualPayment manualPayment3 = new ManualPayment();
        manualPayment3.setMember(9L);
        manualPayment3.setMembershipCost(paymentConfig.getMembershipCost());
        manualPaymentRepository.saveManualPayment(manualPayment3);

        Date semesterStart = DateUtils.asDate(LocalDateTime.now().minusDays(1L));
        List<ManualPayment> manualPaymentsFromDatabase = manualPaymentRepository.findByMemberAndCreatedGreaterThan(2L, semesterStart);

        assertEquals(0, manualPaymentsFromDatabase.size());
        assertNotEquals(1, manualPaymentsFromDatabase.size());
        assertNotEquals(2, manualPaymentsFromDatabase.size());
        assertNotEquals(3, manualPaymentsFromDatabase.size());
    }

    @Test
    public void whenNewManualPayments_forOtherMember_afterSemesterStart_paymentNotFound() {
        ManualPayment manualPayment = new ManualPayment();
        manualPayment.setMember(77L);
        manualPayment.setMembershipCost(paymentConfig.getMembershipCost());
        manualPaymentRepository.saveManualPayment(manualPayment);

        Date semesterStart = DateUtils.asDate(LocalDateTime.now().minusDays(1L));
        List<ManualPayment> manualPaymentsFromDatabase = manualPaymentRepository.findByMemberAndCreatedGreaterThan(3L, semesterStart);

        assertEquals(0, manualPaymentsFromDatabase.size());
        assertNotEquals(1, manualPaymentsFromDatabase.size());
        assertNotEquals(2, manualPaymentsFromDatabase.size());
    }

    @After
    public void tearDown() {
        manualPaymentRepository.deleteAll();
        vippsPaymentRepository.deleteAll();
    }

}
