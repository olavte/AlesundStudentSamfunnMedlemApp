package no.astudent.paymentservice.domain.vipps;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

import lombok.Data;
import no.astudent.paymentservice.config.PaymentConfig;
import no.astudent.paymentservice.domain.VippsPayment;
import no.astudent.paymentservice.utils.DateUtils;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
class Transaction {

    // Amount in øre. 32 Bit Integer (2147483647)
    String amount; // required

    // Id which uniquely identifies a payment. Maximum length is 30 alphanumeric characters.
    String orderId; // required

    // Identifies if the payment references to some past orders registered with Vipps. If defined, transactions for
    // this payment will show up as child transactions of the specified order.
    String refOrderId; // optional

    // Timestamp in ISO-8601 representing when the order has been made by merchant
    String timeStamp; // optional

    // Transaction text that can be displayed to end user
    String transactionText; // required

    Transaction(VippsPayment vippsPayment, PaymentConfig paymentConfig) {
        setAmount(Integer.toString(vippsPayment.getMembershipCost() * 100));
        setOrderId(vippsPayment.getOrderId(paymentConfig));
        LocalDateTime semesterStart = DateUtils.asLocalDateTime(paymentConfig.getSemesterStart());
        int startYear = semesterStart.getYear();
        int endYear = startYear + 1;
        setTransactionText("Medlemskap " + startYear + "-" + endYear);
    }

    Transaction(PaymentConfig paymentConfig) {
        setAmount("0");
        setTransactionText("Medlemskap " + getYearString(paymentConfig));
    }

    private String getYearString(PaymentConfig paymentConfig) {
        LocalDateTime semesterStart = DateUtils.asLocalDateTime(paymentConfig.getSemesterStart());
        return Integer.toString(semesterStart.getYear()) + "-" + Integer.toString(semesterStart.getYear() + 1);
    }
}
