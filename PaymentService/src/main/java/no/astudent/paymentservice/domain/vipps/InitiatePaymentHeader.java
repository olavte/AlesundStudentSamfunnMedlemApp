package no.astudent.paymentservice.domain.vipps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitiatePaymentHeader {

    // Authorization token, is obtained by registering merchant backend application in Merchant Developer Portal.
    String authorization;

    // Mandatory. Only alphanumeric characters allowed and max 30 characters long. This is used to make the
    // request idempotent in order to be able to re-try request without any side effects
    String xRequestId;

    // Subscription key which provides access to this API. Found in your Profile.
    String ocpApimSubscriptionKey;

}
