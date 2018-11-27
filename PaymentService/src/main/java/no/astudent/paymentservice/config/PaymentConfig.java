package no.astudent.paymentservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import lombok.Data;
import no.astudent.paymentservice.utils.DateUtils;

@ConfigurationProperties(prefix="astudent")
@Configuration
@Data
public class PaymentConfig {

    @Value("${astudent.orgNumber}")
    String orgNumber;

    @Value("${astudent.semesterStart}")
    String semesterStart;

    @Value("${astudent.membershipCost}")
    int membershipCost;

    @Value("${astudent.url}")
    String url;

    @Value("${astudent.apiRootUrl}")
    String apiRootUrl;

    @Value("${astudent.discountPassword}")
    String discountPassword;

    public Date getSemesterStart() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        return DateUtils.asDate(LocalDate.parse(semesterStart, formatter));
    }
}