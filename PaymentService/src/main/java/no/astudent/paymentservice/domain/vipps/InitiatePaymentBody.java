package no.astudent.paymentservice.domain.vipps;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.astudent.paymentservice.config.PaymentConfig;
import no.astudent.paymentservice.config.VippsConfig;
import no.astudent.paymentservice.domain.VippsPayment;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InitiatePaymentBody {

    MerchantInfo merchantInfo;
    CustomerInfo customerInfo;
    Transaction transaction;

    public InitiatePaymentBody(VippsPayment vippsPayment, PaymentConfig paymentConfig, VippsConfig vippsConfig, AccessToken vippsAccessToken) {

        customerInfo = new CustomerInfo(vippsPayment);
        transaction = new Transaction(vippsPayment, paymentConfig);
        merchantInfo = new MerchantInfo(vippsAccessToken, vippsConfig, paymentConfig, vippsPayment);

    }
}
