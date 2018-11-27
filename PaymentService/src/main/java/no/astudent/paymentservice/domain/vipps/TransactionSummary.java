package no.astudent.paymentservice.domain.vipps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSummary {
    String capturedAmount;
    String refundedAmount;
    String remainingAmountToCapture;
    String remainingAmountToRefund;
}
