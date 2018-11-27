package no.astudent.paymentservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@ConfigurationProperties(prefix = "vipps")
@Configuration
@Data
public class VippsConfig {
    @Value("${vipps.clientId}")
    String clientId;

    @Value("${vipps.clientSecret}")
    String clientSecret;

    @Value("${vipps.ocpApimKeyAccessToken}")
    String ocpApimKeyAccessToken;

    @Value("${vipps.ocpApimKeyAccessTokenSecondary}")
    String ocpApimKeyAccessTokenSecondary;

    @Value("${vipps.ocpApimKeyEcommerce}")
    String ocpApimKeyEcommerce;

    @Value("${vipps.ocpApimKeyEcommerceSecondary}")
    String ocpApimKeyEcommerceSecondary;

    @Value("${vipps.serverUrl}")
    String serverUrl;

    @Value("${vipps.accessTokenUrlPath}")
    String accessTokenUrlPath;

    @Value("${vipps.merchantSerialNumber}")
    String merchantSerialNumber;

    @Value("${vipps.initiatePaymentUrlPath}")
    String initiatePaymentUrlPath;

    @Value("${vipps.getOrderUrlPathBeginning}")
    String getOrderUrlPathBeginning;

    @Value("${vipps.getOrderUrlPathEnd}")
    String getOrderUrlPathEnd;

    @Value("${vipps.capturePaymentUrlPathBeginning}")
    String capturePaymentUrlPathBeginning;

    @Value("${vipps.capturePaymentUrlPathEnd}")
    String capturePaymentUrlPathEnd;

    public String getInitiatePaymentUrl() {
        return serverUrl + initiatePaymentUrlPath;
    }

    public String getAccessTokenUrl() {
        return serverUrl + accessTokenUrlPath;
    }
}
