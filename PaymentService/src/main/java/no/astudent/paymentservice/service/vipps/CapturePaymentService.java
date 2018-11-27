package no.astudent.paymentservice.service.vipps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import no.astudent.paymentservice.config.VippsConfig;
import no.astudent.paymentservice.domain.vipps.CapturePaymentBody;
import no.astudent.paymentservice.domain.vipps.CapturePaymentResponse;
import no.astudent.paymentservice.domain.vipps.InitiatePaymentHeader;

@Service
public class CapturePaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CapturePaymentService.class);

    private final VippsConfig vippsConfig;

    @Autowired
    public CapturePaymentService(VippsConfig vippsConfig) {
        this.vippsConfig = vippsConfig;
    }

    public CapturePaymentResponse getCapturePaymentResponse(InitiatePaymentHeader header, CapturePaymentBody body) {
        try {
            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String jsonBody = objectWriter.writeValueAsString(body);
            HttpPost httpPostRequest = getHttpPostRequest(header, new StringEntity(jsonBody));
            HttpClient httpclient = HttpClients.createDefault();
            HttpResponse response = httpclient.execute(httpPostRequest);
            return mapCapturePaymentResponse(EntityUtils.toString(response.getEntity()));
        } catch (IOException ex) {
            LOGGER.error("Got IOException when calling getCapturePaymentResponse: ({})", ex.getLocalizedMessage());
            return null;
        }
    }

    private CapturePaymentResponse mapCapturePaymentResponse(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, CapturePaymentResponse.class);
        } catch (Exception ex) {
            LOGGER.error("Exception when trying deserialize json-string to InitiatePaymentResponse, got exception({})", ex);
            return null;
        }
    }

    private HttpPost getHttpPostRequest(InitiatePaymentHeader header, StringEntity body) {

        URIBuilder builder;
        URI uri;

        try {
            builder = new URIBuilder(
                vippsConfig.getServerUrl() +
                    vippsConfig.getCapturePaymentUrlPathBeginning() +
                    header.getXRequestId() +
                    vippsConfig.getCapturePaymentUrlPathEnd()
            );
            uri = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
        HttpPost httpPostRequest = new HttpPost(uri);
        httpPostRequest.setHeader("Authorization", header.getAuthorization());
        httpPostRequest.setHeader("X-Request-Id", header.getXRequestId());
        httpPostRequest.setHeader("X-TimeStamp", "");
        httpPostRequest.setHeader("Content-Type", "application/json");
        httpPostRequest.setHeader("Ocp-Apim-Subscription-Key", header.getOcpApimSubscriptionKey());
        httpPostRequest.setEntity(body);
        return httpPostRequest;
    }
}
