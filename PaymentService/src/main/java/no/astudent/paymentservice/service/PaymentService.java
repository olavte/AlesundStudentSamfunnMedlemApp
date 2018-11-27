package no.astudent.paymentservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import no.astudent.paymentservice.config.PaymentConfig;
import no.astudent.paymentservice.config.VippsConfig;
import no.astudent.paymentservice.domain.Discount;
import no.astudent.paymentservice.domain.ManualPayment;
import no.astudent.paymentservice.domain.VippsPayment;
import no.astudent.paymentservice.domain.vipps.CapturePaymentBody;
import no.astudent.paymentservice.domain.vipps.CapturePaymentResponse;
import no.astudent.paymentservice.domain.vipps.InitiatePaymentBody;
import no.astudent.paymentservice.domain.vipps.InitiatePaymentHeader;
import no.astudent.paymentservice.domain.vipps.InitiatePaymentResponse;
import no.astudent.paymentservice.repository.DiscountRepository;
import no.astudent.paymentservice.repository.ManualPaymentRepository;
import no.astudent.paymentservice.repository.VippsPaymentRepository;
import no.astudent.paymentservice.service.vipps.AccessTokenService;
import no.astudent.paymentservice.service.vipps.CapturePaymentService;
import no.astudent.paymentservice.service.vipps.GetOrderService;
import no.astudent.paymentservice.service.vipps.InitiatePaymentService;
import no.astudent.paymentservice.utils.DateUtils;

@Service
public class PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    private final VippsPaymentRepository vippsPaymentRepository;
    private final ManualPaymentRepository manualPaymentRepository;
    private final DiscountRepository discountRepository;

    private final PaymentConfig paymentConfig;
    private final VippsConfig vippsConfig;

    private InitiatePaymentService initiatePaymentService;
    private AccessTokenService accessTokenService;
    private GetOrderService getOrderService;
    private CapturePaymentService capturePaymentService;

    @Autowired
    public PaymentService(VippsPaymentRepository vippsPaymentRepository, ManualPaymentRepository manualPaymentRepository, PaymentConfig paymentConfig, VippsConfig vippsConfig, InitiatePaymentService initiatePaymentService, AccessTokenService accessTokenService, GetOrderService getOrderService, DiscountRepository discountRepository, CapturePaymentService capturePaymentService) {
        this.vippsPaymentRepository = vippsPaymentRepository;
        this.manualPaymentRepository = manualPaymentRepository;
        this.paymentConfig = paymentConfig;
        this.vippsConfig = vippsConfig;
        this.initiatePaymentService = initiatePaymentService;
        this.accessTokenService = accessTokenService;
        this.getOrderService = getOrderService;
        this.discountRepository = discountRepository;
        this.capturePaymentService = capturePaymentService;
    }

    public boolean isByMemberAndCreatedAfterStartOfSemester(Long member) {
        Date semesterStart = paymentConfig.getSemesterStart();

        List<ManualPayment> manualPaymentList = manualPaymentRepository.findByMemberAndCreatedGreaterThan(
                member, semesterStart);
        List<VippsPayment> vippsPaymentList = vippsPaymentRepository.findByMemberAndVerifiedAndCreatedGreaterThanEqual(
                member, true, semesterStart);

        if (manualPaymentList.size() > 0 || vippsPaymentList.size() > 0) {
            for (ManualPayment payment : manualPaymentList) {
                LOGGER.debug("Found a manual-payment({}), returning true", payment);
            }
            for (VippsPayment payment : vippsPaymentList) {
                LOGGER.debug("Found a vipps-payment({}), returning true", payment);
            }
            return true;
        }

        LOGGER.info("Payment not found, returning false!");
        return false;
    }

    public boolean isByMemberAndCreatedAfterStartOfSemesterNotVerified(Long member) {
        Date semesterStart = paymentConfig.getSemesterStart();

        List<VippsPayment> vippsPaymentList = vippsPaymentRepository.findByMemberAndVerifiedAndCreatedGreaterThanEqual(
                member, false, semesterStart);

        if (vippsPaymentList.size() > 0) {

            for (VippsPayment payment : vippsPaymentList) {
                LOGGER.debug("Found a vipps-payment({}) that awaits verification, returning true", payment);
            }
            return true;
        }

        LOGGER.info("Payment not found, returning false!");
        return false;
    }

    public boolean newManualPayment(Long member) {
        ManualPayment manualPayment = new ManualPayment();
        manualPayment.setMember(member);
        manualPaymentRepository.saveManualPayment(manualPayment);

        if (manualPayment.getPaymentId() != null) {
            LOGGER.info("Saved new ManualPayment({})", manualPayment);
            return true;
        }

        LOGGER.error("Could not save new ManualPayment({})", manualPayment);
        return false;
    }

    private VippsPayment getNewVippsPayment(Long member, int phonenumber, int membershipCost) {
        VippsPayment vippsPayment = new VippsPayment();
        vippsPayment.setMember(member);
        vippsPayment.setPhoneNumber(phonenumber);
        vippsPayment.setMembershipCost(membershipCost);
        vippsPayment.setVerified(false);

        try {
            vippsPaymentRepository.saveVippsPayment(vippsPayment);
            LOGGER.info("Saved new VippsPayment({})", vippsPayment);
            return vippsPayment;
        } catch (TransactionSystemException ex) {
            LOGGER.error("Could not save VippsPayment({}), invalid phonenumber({})");
            return null;
        }
    }

    public int getMembershipCost() {
        return paymentConfig.getMembershipCost();
    }

    public void setMembershipCost(int membershipCost) {
        paymentConfig.setMembershipCost(membershipCost);
        LOGGER.info("Setting cost of membership to {} NOK", membershipCost);
    }

    public boolean setVippsPaymentVerified(int phoneNumber) {
        try {
            VippsPayment vippsPaymentToVerify = vippsPaymentRepository.findFirstByPhoneNumberAndVerifiedOrderByCreatedDesc(phoneNumber, false);
            vippsPaymentToVerify.setVerified(true);
            vippsPaymentRepository.saveVippsPayment(vippsPaymentToVerify);
        } catch (Exception ex) {
            LOGGER.error("Could not verify VippsPayment with phone number({}), got exception({})", phoneNumber, ex);
            return false;
        }

        return true;
    }

    public void setVippsPaymentVerified(String orderId) {
        String paymentIdString = orderId.substring(9, 16);

        LOGGER.info("PaymentString: " + paymentIdString);

        Long paymentId = Long.parseLong(paymentIdString);

        LOGGER.info("PaymentID: " + paymentId);

        VippsPayment vippsPayment = vippsPaymentRepository.findOne(paymentId);
        vippsPayment.setVerified(true);
        vippsPaymentRepository.saveVippsPayment(vippsPayment);
    }

    public String vippsGetOrderStatus(String orderId) {
        long start = System.currentTimeMillis();

        // Fetch existing or new AccessToken
        if (accessTokenService.getAccessToken() == null) {
            LOGGER.error("Completed vippsGetOrderStatus prematurely in {} milliseconds, could not get AccessToken", System.currentTimeMillis() - start);
            return null;
        }

        // Get VippsPayment
        Long paymentId = Long.parseLong(orderId.substring(paymentConfig.getOrgNumber().length()).substring(0, 7));
        VippsPayment vippsPayment = vippsPaymentRepository.findOne(paymentId);
        if (vippsPayment == null || vippsPayment.getPaymentId() == null) {
            LOGGER.error("Could not find VippsPayment in local database with paymentId({}), payment has orderId({})", paymentId, orderId);
            return null;
        }

        // Make header
        InitiatePaymentHeader header = new InitiatePaymentHeader(accessTokenService.getAccessToken().getAuthorization(),
                vippsPayment.getOrderId(paymentConfig), vippsConfig.getOcpApimKeyEcommerce());

        return getOrderService.getOrderStatus(orderId, header);
    }

    public String getVippsOrderStatus(long idMember) {
        long start = System.currentTimeMillis();

        List<VippsPayment> vippsPayments = vippsPaymentRepository.findByMember(idMember);
        for(VippsPayment vippsPayment : vippsPayments) {
            String orderId = vippsPayment.getOrderId(paymentConfig);
            // Fetch existing or new AccessToken
            if (accessTokenService.getAccessToken() == null) {
                LOGGER.error(
                        "Completed vippsGetOrderStatus prematurely in {} milliseconds, could not get AccessToken", System.currentTimeMillis() - start);
                return null;
            }
            if (vippsPayment.getPaymentId() == null) {
                LOGGER.error(
                        "Could not find VippsPayment in local database with paymentId({}), payment has orderId({})",
                        vippsPayment.getPaymentId(), orderId);
                return null;
            }

            // Make header
            InitiatePaymentHeader header = new InitiatePaymentHeader(accessTokenService.getAccessToken().getAuthorization(),
                    vippsPayment.getOrderId(paymentConfig), vippsConfig.getOcpApimKeyEcommerce());

            return getOrderService.getOrderStatus(orderId, header);
        }
        return null;
    }

    private String vippsCapturePayment(String orderId) {
        long start = System.currentTimeMillis();

        // Fetch existing or new AccessToken
        if (accessTokenService.getAccessToken() == null) {
            LOGGER.error("Completed vippsCapturePayment prematurely in {} milliseconds, could not get AccessToken", System.currentTimeMillis() - start);
            return null;
        }

        // Get VippsPayment
        Long paymentId = Long.parseLong(orderId.substring(paymentConfig.getOrgNumber().length()).substring(0, 7));
        VippsPayment vippsPayment = vippsPaymentRepository.findOne(paymentId);
        if (vippsPayment == null || vippsPayment.getPaymentId() == null) {
            LOGGER.error("Could not find VippsPayment in local database with paymentId({}), payment has orderId({})", paymentId, orderId);
            return null;
        }

        // Make header
        InitiatePaymentHeader header = new InitiatePaymentHeader(accessTokenService.getAccessToken().getAuthorization(),
                vippsPayment.getOrderId(paymentConfig), vippsConfig.getOcpApimKeyEcommerce());
        CapturePaymentBody body = new CapturePaymentBody(paymentConfig, vippsConfig);

        // Get response from service
        CapturePaymentResponse response = capturePaymentService.getCapturePaymentResponse(header, body);
        LOGGER.info("Completed vippsCapturePayment successfully in {} milliseconds, status({}), capturedAmount({}), remainingAmountToCapture({})", System.currentTimeMillis() - start, response.getTransactionInfo().getStatus(), response.getTransactionSummary().getCapturedAmount(), response.getTransactionSummary().getRemainingAmountToCapture());
        return "Payment captured successfully";
    }

    public String vippsInitiatePayment(Long member, int phoneNumber, String discountCode) {
        long start = System.currentTimeMillis();

        // Fetch existing or new AccessToken
        if (accessTokenService.getAccessToken() == null) {
            LOGGER.error("Completed vippsInitiatePayment prematurely in {} milliseconds, could not get AccessToken", System.currentTimeMillis() - start);
            return null;
        }

        // Get cost of payment
        int membershipCost = paymentConfig.getMembershipCost();
        // Check if a discountCode is valid and a new cost should be set
        if (discountCode.equals("")) {
            Discount discount = getDiscount(discountCode);
            if (discount == null || discount.getAmount() < 0) {
                LOGGER.info("No discount for discountCode({}) is active, ignoring code", discountCode);
            } else {
                membershipCost = discount.getAmount();
                LOGGER.info("Discount applied for member({}) with code({}), new cost is {}", member, discountCode, membershipCost);
            }
        }

        // Persist new VippsPayment
        VippsPayment vippsPayment = getNewVippsPayment(member, phoneNumber, membershipCost);
        if (vippsPayment == null || vippsPayment.getPaymentId() == null) {
            LOGGER.error("Completed vippsInitiatePayment prematurely in {} milliseconds, unable to persist VippsPayment", System.currentTimeMillis() - start);
            return null;
        }

        // Make header and body
        InitiatePaymentHeader initiatePaymentHeader = new InitiatePaymentHeader(accessTokenService.getAccessToken().getAuthorization(),
                vippsPayment.getOrderId(paymentConfig), vippsConfig.getOcpApimKeyEcommerce());
        InitiatePaymentBody initiatePaymentBody = new InitiatePaymentBody(vippsPayment, paymentConfig, vippsConfig,
                accessTokenService.getAccessToken());

        // Make REST-call and get response
        InitiatePaymentResponse response = initiatePaymentService.getInitiatePaymentResponse(initiatePaymentHeader, initiatePaymentBody);
        if (response != null) {
            LOGGER.info("Completed vippsInitiatePayment in {} milliseconds, orderId({})", System.currentTimeMillis() - start, vippsPayment.getOrderId(paymentConfig));
            return response.getUrl();
        }

        LOGGER.error("Completed vippsInitiatePayment prematurely in {} milliseconds, response was null", System.currentTimeMillis() - start);
        return null;
    }

    private Discount getDiscount(String discountCode) {
        Date now = DateUtils.asDate(LocalDateTime.now());
        List<Discount> discountList = discountRepository.findByCodeAndStartDateLessThanAndEndDateGreaterThan(discountCode.toUpperCase(), now, now);
        Discount discount = null;
        for (Discount discountFromDatabase : discountList) {
            if (discount == null) {
                discount = discountFromDatabase;
            } else {
                if (discount.getAmount() > discountFromDatabase.getAmount()) {
                    discount = discountFromDatabase;
                }
            }
        }

        return discount;
    }

    public String vippsInitiatePaymentSuccessful(String orderId) {
        long start = System.currentTimeMillis();
        String capture = vippsCapturePayment(orderId);
        if (capture != null) {
            LOGGER.info("{} for orderId({}) in {} milliseconds", capture, orderId, System.currentTimeMillis() - start);
            return "true";
        } else {
            LOGGER.error("Attempted to CapturePayment, but it failed in {} milliseconds for orderId({})", System.currentTimeMillis() - start, orderId);
            return "false";
        }
    }

    public String newDiscount(String code, int amount, Long startDateUnixLong, Long endDateUnixLong, String discountPassword) {

        if (discountPassword == null || !discountPassword.equals(paymentConfig.getDiscountPassword())) {
            LOGGER.error("Attempt to create discount, incorrect password");
            return "Incorrect password provided!";
        }

        if (code == null || amount < 1 || startDateUnixLong == null || endDateUnixLong == null) {
            LOGGER.error("Attempt to create discount, missing required parameters");
            return "Missing required parameters!";
        }

        String codeInUpperCase = code.toUpperCase();
        Discount discount = new Discount(codeInUpperCase, amount, new Date(startDateUnixLong), new Date(endDateUnixLong));
        String startDate = DateUtils.asLocalDateTime(discount.getStartDate()).toString();
        String endDate = DateUtils.asLocalDateTime(discount.getEndDate()).toString();

        discountRepository.saveDiscount(discount);
        if (discount.getDiscountId() != null) {
            LOGGER.info("Discount with cost({}) and code({}) has been created, the code is valid from({}) - to({})",
                    amount, codeInUpperCase, startDate, endDate);
            return "Discount with cost(" + amount + ") and code(" + codeInUpperCase +
                    ") has been created, the code is valid from(" + startDate + ") - to(" + endDate + ")";
        }

        return "Could not create discount! Unknown error!";
    }

    public ResponseEntity<String> hasMemberPaid(Long memberId) {
        List<VippsPayment> vippsPayments = vippsPaymentRepository.findByMember(memberId);
        if (vippsPayments.isEmpty()) {
            return new ResponseEntity<>("false", HttpStatus.PAYMENT_REQUIRED);
        } else {
            for (VippsPayment vippsPayment : vippsPayments) {
                if (vippsPayment.isVerified()) {
                    return new ResponseEntity<>("true", HttpStatus.OK);
                } else {
                    String status = vippsGetOrderStatus(vippsPayment.getOrderId(paymentConfig));
                    if (status != null && status.equals("RESERVE")) {
                        vippsPayment.setVerified(true);
                        vippsPaymentRepository.save(vippsPayment);
                    }
                    if (vippsPayment.isVerified()) {
                        vippsInitiatePaymentSuccessful(vippsPayment.getOrderId(paymentConfig));
                        return new ResponseEntity<>("true", HttpStatus.OK);
                    }
                }
            }
            return new ResponseEntity<>("false", HttpStatus.PAYMENT_REQUIRED);
        }
    }
}
