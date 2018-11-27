package no.astudent.paymentservice.domain.vipps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderResponse {

    String orderId;
    TransactionInfo transactionInfo;

}