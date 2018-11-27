package no.astudent.webapp.service;

import no.astudent.webapp.Member;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import no.astudent.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    private String BASE_URI = "http://localhost:8082";

    public String startVippsPayment(Member member, String phoneNumber, String discount) {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder url = new StringBuilder();
        url.append(BASE_URI);
        url.append("/newvippspayment");
        url.append("?id=").append(member.getIdMember());
        url.append("&phonenumber=").append(phoneNumber);
        url.append("&discount=").append(discount);

        return restTemplate.getForObject(url.toString(), String.class);
    }

    public ResponseEntity<String> hasMemberPaid(Long id) {
        String HAS_MEMBER_PAID = "/hasMemberPaid";
        RestTemplate restTemplate = new RestTemplate();
        JSONObject json = new JSONObject();
        json.put("memberId", id);
        return restTemplate.postForEntity(BASE_URI + HAS_MEMBER_PAID, json.toString(), String.class);
    }

    public ResponseEntity<Boolean> vippsInitiatePaymentSuccessful(String orderId) {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder url = new StringBuilder();
        url.append(BASE_URI);
        url.append("/paymentinitiated");
        url.append("?id=").append(orderId);

        return restTemplate.exchange(url.toString(),
                HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), Boolean.class);
    }
}
