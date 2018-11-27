package no.astudent.paymentservice.service.vipps;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Date;

import no.astudent.paymentservice.config.VippsConfig;
import no.astudent.paymentservice.domain.vipps.AccessToken;
import no.astudent.paymentservice.domain.vipps.ApiError;
import no.astudent.paymentservice.utils.DateUtils;

@Service
public class AccessTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenService.class);

    private final VippsConfig vippsConfig;

    private AccessToken vippsAccessToken;

    @Autowired
    public AccessTokenService(VippsConfig vippsConfig) {
        this.vippsConfig = vippsConfig;
    }

    public AccessToken getAccessToken() {
        // Get AccessToken that needs to be used for further interactions, renew if expired
        if (vippsAccessToken == null || isAccessTokenExpired()) {
            vippsAccessToken = newAccessToken();
            if (vippsAccessToken == null || isAccessTokenExpired()) {
                LOGGER.error("Could not renew or fetch Vipps-AccessToken.");
                return null;
            }
        }

        return vippsAccessToken;
    }

    private boolean isAccessTokenExpired() {
        return LocalDateTime.now()
            .isBefore(DateUtils.asLocalDateTime(
                new Date(Long.parseLong(vippsAccessToken.getExpires_on())))
                .minusHours(3L));
    }

    private AccessToken newAccessToken() {
        ResponseEntity<String> response = getResponseEntityFromApiCall();

        if (response == null) {
            LOGGER.error("Could not retrieve response from Vipps API, response was null");
            return null;
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            return mapAccessToken(response.getBody());
        } else if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
            LOGGER.error("Vipps API returned Bad Request, HttpStatus({}), ApiError({})",
                response.getStatusCode(), mapApiError(response.getBody()));
            return null;

        } else if (response.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
            LOGGER.error("Vipps API returned an Internal Server Error, HttpStatus({}), ApiError({})",
                response.getStatusCode(), mapApiError(response.getBody()));
            return null;
        } else {
            LOGGER.error("Unknown status-code from API-call to get AccessToken, HttpStatus({}), ApiError({}), reseponse-body({})",
                response.getStatusCode(), mapApiError(response.getBody()), response.getBody());
            return null;
        }
    }

    private AccessToken mapAccessToken(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, AccessToken.class);
        } catch (Exception ex) {
            LOGGER.error("Exception when trying deserialize json-string to AccessToken, got exception({})", ex);
            return null;
        }
    }

    private ApiError mapApiError(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, ApiError.class);
        } catch (Exception ex) {
            LOGGER.error("Exception when trying deserialize json-string to ApiError, got exception({})", ex);
            return null;
        }
    }

    private ResponseEntity<String> getResponseEntityFromApiCall() {
        RestTemplate restTemplate = new RestTemplate();
        String url = getHttpRequestUrl(vippsConfig);
        HttpHeaders httpHeaders = getHttpRequestHeader(vippsConfig);

        try {
            return restTemplate.exchange(url,
                HttpMethod.POST, new HttpEntity<>(httpHeaders), String.class);
        } catch (ResourceAccessException ex) {
            LOGGER.info("Failed to fetch AccessToken from Vipps-API, unhandled ResourceAccessException({})", ex.getLocalizedMessage());
            return null;
        } catch (HttpClientErrorException ex) {
            LOGGER.info("Failed to fetch AccessToken from Vipps-API, reason: ", ex.getLocalizedMessage());
            return null;
        }
    }

    // TODO: Could be moved into VippsConfig-class?
    private String getHttpRequestUrl(VippsConfig vippsConfig) {
        return vippsConfig.getServerUrl() + vippsConfig.getAccessTokenUrlPath();
    }

    // TODO: Could be moved into VippsConfig-class?
    private HttpHeaders getHttpRequestHeader(VippsConfig vippsConfig) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("client_id", vippsConfig.getClientId());
        httpHeaders.set("client_secret", vippsConfig.getClientSecret());
        httpHeaders.set("Ocp-Apim-Subscription-Key", vippsConfig.getOcpApimKeyAccessToken());
        return httpHeaders;
    }
}
