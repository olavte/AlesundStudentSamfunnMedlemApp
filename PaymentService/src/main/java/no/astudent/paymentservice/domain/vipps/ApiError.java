package no.astudent.paymentservice.domain.vipps;

import java.util.ArrayList;

import lombok.Data;

/**
 * Class that holds values returned from the Vipps API on HttpResponse 400 and 401
 */
@Data
public class ApiError {
    String error;
    String error_description;
    ArrayList<Integer> error_code;
    String timestamp;
    String trace_id;
    String correlation_id;
}
