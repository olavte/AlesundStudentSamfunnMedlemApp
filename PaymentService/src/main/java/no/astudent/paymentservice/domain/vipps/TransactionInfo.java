package no.astudent.paymentservice.domain.vipps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionInfo {

    double amount;
    String status;
    String timeStamp;
    String transactionId;
    String transactionText;
    TransactionSummary transactionSummary;

}
