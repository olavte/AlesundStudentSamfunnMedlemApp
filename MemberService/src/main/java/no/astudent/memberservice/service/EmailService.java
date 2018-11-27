package no.astudent.memberservice.service;

import no.astudent.memberservice.config.MemberConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberService.class);

    private final MemberConfig memberConfig;

    @Autowired
    public EmailService(MemberConfig memberConfig) {
        this.memberConfig = memberConfig;
    }

    public boolean sendVerificationEmail(String hash, String email, String name) {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder url = new StringBuilder();
        url.append(memberConfig.getEmailServiceUrl());
        url.append("/sendVerification");
        url.append("?hashcode=").append(hash);
        url.append("&email=").append(email);
        url.append("&name=").append(name);

        return getResponseFromRestCall(restTemplate, url);
    }

    public boolean sendPasswordResetEmail(String hash, String email, String name) {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder url = new StringBuilder();
        url.append(memberConfig.getEmailServiceUrl());
        url.append("/sendPasswordReset");
        url.append("?name=").append(name);
        url.append("&hashcode=").append(hash);
        url.append("&email=").append(email);

        return getResponseFromRestCall(restTemplate, url);
    }


    private boolean getResponseFromRestCall(RestTemplate restTemplate, StringBuilder url) {
        try {
            ResponseEntity<Boolean> response = restTemplate.exchange(url.toString(),
                    HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), Boolean.class);
            return response.getBody();
        } catch (ResourceAccessException e) {
            LOGGER.info("Connection refused from Service, service is malfunctioning or down", e);
            LOGGER.info(url.toString());
            return false;
        }
    }
}
