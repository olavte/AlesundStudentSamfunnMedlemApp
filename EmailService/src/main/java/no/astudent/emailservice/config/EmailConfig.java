package no.astudent.emailservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@ConfigurationProperties(prefix = "astudent")
@Configuration
@Data
public class EmailConfig {

    @Value("${astudent.url}")
    private String url;

    @Value("${astudent.apiRootUrl}")
    private String apiRootUrl;
}