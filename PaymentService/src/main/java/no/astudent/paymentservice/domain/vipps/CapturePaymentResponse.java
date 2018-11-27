package no.astudent.paymentservice.domain.vipps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapturePaymentResponse {

    String orderId;
    TransactionInfo transactionInfo;
    TransactionSummary transactionSummary;

}
