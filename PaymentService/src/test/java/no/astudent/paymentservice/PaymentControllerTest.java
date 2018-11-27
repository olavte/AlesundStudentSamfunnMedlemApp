package no.astudent.paymentservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import no.astudent.paymentservice.controller.PaymentController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaymentServiceRunner.class)
public class PaymentControllerTest {

    @Autowired
    private PaymentController paymentController;

    @Test
    public void whenNewManualPayment_hasPayment_returnTrue() {
        paymentController.newManualPayment(1L);

        assertTrue(paymentController.getMemberPaymentStatus(1L));
    }

//    @Test
//    public void whenNewVippsPayment_hasPayment_returnFalse() {
//        paymentController.newVippsPayment(2L, 45279562);
//
//        assertFalse(paymentController.getMemberPaymentStatus(2L));
//    }

    @Test
    public void whenSettingNewMembershipCost_getMembershipCost_returnNewMembershipCost() {
        int oldCost = paymentController.getMembershipCost();
        paymentController.setMembershipCost(999);


        assertEquals(paymentController.getMembershipCost(), 999);
        assertNotEquals(paymentController.getMembershipCost(), oldCost);
    }

//    @Test
//    public void whenNewVippsPayment_hasInvalidPhoneNumberTooLarge_returnFalse() {
//        String paymentStatus = paymentController.newVippsPayment(871623L, 100000000);
//
//        assertNull(paymentStatus);
//    }
//
//    @Test
//    public void whenNewVippsPayment_hasInvalidPhoneNumberTooSmall_returnFalse() {
//        String paymentStatus = paymentController.newVippsPayment(7612333L, 9999999);
//
//        assertNull(paymentStatus);
//    }
//
//    @Test
//    public void whenNewVippsPayment_hasInvalidPhoneNumberNegativeDigit_returnFalse() {
//        String paymentStatus = paymentController.newVippsPayment(691337L, -45279562);
//
//        assertNull(paymentStatus);
//    }
//
//    @Test
//    public void whenNewVippsPayment_paymentVerified_hasPayment_returnTrue() {
//        paymentController.newVippsPayment(13L, 45279562);
//        paymentController.setVippsPaymentVerified(45279562);
//
//        assertTrue(paymentController.getMemberPaymentStatus(13L));
//    }
//
//    @Test
//    public void whenTwoNewVippsPayments_onePaymentVerified_hasAwaitingApproval_returnTrue() {
//        paymentController.newVippsPayment(69L, 45279562);
//        paymentController.newVippsPayment(69L, 45279562);
//        paymentController.setVippsPaymentVerified(45279562);
//
//        assertTrue(paymentController.isMemberPaymentAwaitingApproval(69L));
//    }
//
//    @Test
//    public void whenTwoNewVippsPayments_onePaymentVerified_hasPayment_returnTrue() {
//        paymentController.newVippsPayment(33L, 45279562);
//        paymentController.newVippsPayment(33L, 45279562);
//        paymentController.setVippsPaymentVerified(45279562);
//
//        assertTrue(paymentController.getMemberPaymentStatus(33L));
//    }
//
//    @Test
//    public void whenTwoNewVippsPayments_twoPaymentsVerified_hasAwaitingApproval_returnFalse() {
//        paymentController.newVippsPayment(87L, 45279562);
//        paymentController.newVippsPayment(87L, 45279562);
//        paymentController.setVippsPaymentVerified(45279562);
//        paymentController.setVippsPaymentVerified(45279562);
//
//        assertFalse(paymentController.isMemberPaymentAwaitingApproval(87L));
//    }
//
//    @Test
//    public void whenTwoNewPaymentsOneOfEach_hasAwaitingApproval_returnTrue() {
//        paymentController.newVippsPayment(75L, 45279562);
//        paymentController.newManualPayment(75L);
//        paymentController.setVippsPaymentVerified(45279562);
//
//        assertTrue(paymentController.getMemberPaymentStatus(75L));
//    }
//
//    @Test
//    public void whenTwoNewPaymentsOneOfEach_hasPayment_returnTrue() {
//        paymentController.newVippsPayment(812L, 45279562);
//        paymentController.newManualPayment(812L);
//        paymentController.setVippsPaymentVerified(45279562);
//
//        assertTrue(paymentController.getMemberPaymentStatus(812L));
//    }
}
