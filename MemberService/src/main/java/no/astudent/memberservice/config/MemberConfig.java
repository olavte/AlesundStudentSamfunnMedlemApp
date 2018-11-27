package no.astudent.memberservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@ConfigurationProperties(prefix = "astudent")
@Configuration()
@Data
public class MemberConfig {

    @Value("${astudent.apiRootUrl}")
    private String apiRootUrl;

    @Value("${astudent.emailServicePort}")
    private String emailServicePort;

    public String getEmailServiceUrl() {
        return apiRootUrl + emailServicePort;
    }
}