package no.astudent.paymentservice.domain.vipps;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InitiatePaymentResponse {

    String orderId;
    String url;

}
