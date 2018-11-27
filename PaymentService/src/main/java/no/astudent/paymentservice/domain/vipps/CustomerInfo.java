package no.astudent.paymentservice.domain.vipps;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import no.astudent.paymentservice.domain.VippsPayment;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
class CustomerInfo {
    // Mobile number
    String mobileNumber; // required

    CustomerInfo(VippsPayment vippsPayment) {
        setMobileNumber(Integer.toString(vippsPayment.getPhoneNumber()));
    }
}
