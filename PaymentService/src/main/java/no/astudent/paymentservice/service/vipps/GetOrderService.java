package no.astudent.paymentservice.service.vipps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import no.astudent.paymentservice.config.VippsConfig;
import no.astudent.paymentservice.domain.vipps.GetOrderResponse;
import no.astudent.paymentservice.domain.vipps.InitiatePaymentHeader;

@Service
public class GetOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetOrderService.class);

    private final VippsConfig vippsConfig;

    @Autowired
    public GetOrderService(VippsConfig vippsConfig){
        this.vippsConfig = vippsConfig;
    }

    public String getOrderStatus(String orderId, InitiatePaymentHeader header){
        RestTemplate restTemplate = new RestTemplate();
        String url = vippsConfig.getServerUrl() + vippsConfig.getGetOrderUrlPathBeginning() + orderId + vippsConfig.getGetOrderUrlPathEnd();
        HttpHeaders httpHeaders = getHttpRequestHeader(header);
        ResponseEntity<GetOrderResponse> response = null;

        try {
            response = restTemplate.exchange(url,
                HttpMethod.GET, new HttpEntity<>(httpHeaders), GetOrderResponse.class);
            GetOrderResponse getOrderResponse = response.getBody();
            if(getOrderResponse != null && getOrderResponse.getTransactionInfo() != null && getOrderResponse.getTransactionInfo().getStatus() != null) {
                return getOrderResponse.getTransactionInfo().getStatus();
            }
            LOGGER.error("GetOrderResponse was null or had null values");
            return null;
        } catch (ResourceAccessException ex) {
            LOGGER.error("Failed to fetch GetOrderResponse from Vipps-API, unhandled ResourceAccessException({})", ex.getLocalizedMessage());
            return null;
        } catch (HttpClientErrorException ex) {
            LOGGER.info("Failed to fetch GetOrderResponse from Vipps-API, reason: ", ex.getMessage());
            ex.printStackTrace();
            if(response != null) {
                response.getStatusCode();
            }
            return null;
        }
    }

    private HttpHeaders getHttpRequestHeader(InitiatePaymentHeader header) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", header.getAuthorization());
        httpHeaders.set("X-Request-Id", header.getXRequestId());
        httpHeaders.set("X-TimeStamp", "");
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Ocp-Apim-Subscription-Key", header.getOcpApimSubscriptionKey());
        return httpHeaders;
    }
}
