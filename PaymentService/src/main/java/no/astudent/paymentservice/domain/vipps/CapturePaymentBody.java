package no.astudent.paymentservice.domain.vipps;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.astudent.paymentservice.config.PaymentConfig;
import no.astudent.paymentservice.config.VippsConfig;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CapturePaymentBody {

    MerchantInfo merchantInfo;
    Transaction transaction;

    public CapturePaymentBody(PaymentConfig paymentConfig, VippsConfig vippsConfig) {
        transaction = new Transaction(paymentConfig);
        merchantInfo = new MerchantInfo(vippsConfig);
    }
}