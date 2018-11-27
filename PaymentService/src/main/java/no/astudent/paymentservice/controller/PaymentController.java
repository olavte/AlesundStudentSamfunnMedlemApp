package no.astudent.paymentservice.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import no.astudent.paymentservice.service.PaymentService;

@RestController
public class PaymentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping("/haspayment")
    public boolean getMemberPaymentStatus(@RequestParam(value = "id") Long member) {
        return paymentService.isByMemberAndCreatedAfterStartOfSemester(member);
    }

    @RequestMapping("/hasawaitingpayment")
    public boolean isMemberPaymentAwaitingApproval(@RequestParam(value = "id") Long member) {
        return paymentService.isByMemberAndCreatedAfterStartOfSemesterNotVerified(member);
    }

    @RequestMapping("/newdiscount")
    public String newDiscount(@RequestParam(value = "code") String code,
                               @RequestParam(value = "amount") int amount,
                               @RequestParam(value = "start") Long startDateUnixLong,
                               @RequestParam(value = "end") Long endDateUnixLong,
                               @RequestParam(value = "password") String discountPassword) {
        return paymentService.newDiscount(code, amount, startDateUnixLong, endDateUnixLong, discountPassword);
    }

    @RequestMapping("/newmanualpayment")
    public boolean newManualPayment(@RequestParam(value = "id") Long member) {
        return paymentService.newManualPayment(member);
    }

    @RequestMapping("/paymentinitiated")
    public void paymentInitiatedSuccessfully(@RequestParam(value = "id") String orderId) {
        LOGGER.info("Prefix received, id: " + orderId);
        paymentService.vippsInitiatePaymentSuccessful(orderId);
        paymentService.setVippsPaymentVerified(orderId);
    }

    @RequestMapping("/newvippspayment")
    public String newVippsPayment(@RequestParam(value = "id") Long member, @RequestParam(value = "phonenumber") int phoneNumber, @RequestParam(value = "discount") String discountCode) {
        return paymentService.vippsInitiatePayment(member, phoneNumber, discountCode);
    }

    @RequestMapping("/getmembershipcost")
    public int getMembershipCost() {
        return paymentService.getMembershipCost();
    }

    @RequestMapping("/setmembershipcost")
    public void setMembershipCost(int membershipCost) {
        paymentService.setMembershipCost(membershipCost);
    }

    @RequestMapping("/verifyvippspayment")
    public void setVippsPaymentVerified(@RequestParam(value = "id") int phoneNumber) {
        paymentService.setVippsPaymentVerified(phoneNumber);
    }

    @RequestMapping(value = "/hasMemberPaid", method = RequestMethod.POST)
    public ResponseEntity<String> uploadStudentCard(HttpEntity<String> entity) {
        if(entity.getBody() != null) {
            try {
                JSONObject json = new JSONObject(entity.getBody());
                return paymentService.hasMemberPaid(json.getLong("memberId"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>("false", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping("/testinitiatepayment")
    public String testInitiatePayment() {
        return paymentService.vippsInitiatePayment(1L, 48058993, "fAddEr");
    }

    @RequestMapping("/testorderstatus")
    public void testInitiatePayment(@RequestParam(value = "id") String orderId) {
        paymentService.vippsInitiatePaymentSuccessful(orderId);
    }

    @RequestMapping(value="/getVippsOrderStatus", method = RequestMethod.GET)
    public ResponseEntity<String> getVippsOrderStatus(@RequestParam(value = "id") String idMember) {
        return new ResponseEntity<>(paymentService.vippsGetOrderStatus(idMember), HttpStatus.OK);
    }

    @RequestMapping(value="/capturePayment", method = RequestMethod.GET)
    public ResponseEntity<String> capturePayment(@RequestParam(value = "id") String idMember) {
        return new ResponseEntity<String>(paymentService.vippsInitiatePaymentSuccessful(idMember), HttpStatus.OK);
    }
}
