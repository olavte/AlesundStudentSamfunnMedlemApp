package no.astudent.paymentservice.domain.vipps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import no.astudent.paymentservice.config.PaymentConfig;
import no.astudent.paymentservice.config.VippsConfig;
import no.astudent.paymentservice.domain.VippsPayment;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MerchantInfo {

    MerchantInfo(AccessToken accessToken, VippsConfig vippsConfig, PaymentConfig paymentConfig, VippsPayment vippsPayment) {
        this.setAuthToken(accessToken.getAuthorization());
        this.setCallbackPrefix(paymentConfig.getUrl() + "/paymentinitiated?id=" + vippsPayment.getOrderId(paymentConfig));
        this.setMerchantSerialNumber(vippsConfig.getMerchantSerialNumber());
        this.setFallBack(paymentConfig.getUrl() + "/paymentComplete?idMember=" + vippsPayment.getMember()); // user go here when payment complete
        this.setPaymentType("eComm Regular Payment");
        // this.setConsentRemovalPrefix(paymentConfig.getUrl() + "/removeconsent?id=" + vippsPayment.getOrderId(paymentConfig));
    }

    MerchantInfo(VippsConfig vippsConfig) {
        this.setMerchantSerialNumber(vippsConfig.getMerchantSerialNumber());
    }

    // Merchant should share this token if merchant has authentication mechanism in place which could be used
    // for making callbacks secure
    String authToken; // REQUIRED

    // This is to receive the callback after the payment request. Domain name and context path should be provided by
    // merchant as the value for this parameter. The rest of the URL will be appended by Vipps according to Vipps guidelines.
    String callbackPrefix; // REQUIRED

    // In case of expess checkout payments, this callback will be used for informing merchant about consent removal
    // from Vipps user. This means that particular user do not want merchant to store/use his personal information anymore
    String consentRemovalPrefix; // OPTIONAL

    // Vipps will use the fall back URL to redirect Merchant Page once Payment is completed in Vipps System
    String fallBack; // REQUIRED

    // This parameter indicates whether payment request is triggered from Mobile App or Web browser.
    // Based on this value, response will be redirect url for Vipps landing page or deeplink Url to connect vipps App
    boolean isApp; // OPTIONAL, default: false

    @JsonProperty(value = "isApp")
    public boolean isApp() {
        return isApp;
    }

    // Identifies a merchant sales channel i.e. website, mobile app etc
    String merchantSerialNumber; // required

    // This will parameter will identify difference between ecomm payment and ecomm express payment.
    String paymentType; //OPTIONAL, alternatives: "eComm Express Payment" / "eComm Regular Payment"

    // In case of express checkout payment, merchant should pass this prefix to let Vipps fetch
    // shipping cost and method related details
    String shippingDetailsPrefix; //OPTIONAL
}
