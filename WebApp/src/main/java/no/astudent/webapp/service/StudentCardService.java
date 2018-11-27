package no.astudent.webapp.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StudentCardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentCardService.class);

    private String BASE_URI = "http://localhost:8084";

    public ResponseEntity<String> uploadStudentCard(Long idMember, String image) {
        String UPLOAD_URI = "/addCard";
        RestTemplate restTemplate = new RestTemplate();
        JSONObject json = new JSONObject();
        json.put("idMember", idMember);
        json.put("imageBlob", image);
        return restTemplate.postForEntity(BASE_URI + UPLOAD_URI, json.toString(), String.class);
    }

    public ResponseEntity<String> getAllWaitingStudentCards() {
        String GET_ALL_URL = "/getAllWaitingStudentCards";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(BASE_URI + GET_ALL_URL, String.class);
    }

    public ResponseEntity<String> hasUploadedStudentCard(Long idMember) {
        String HAS_CARD_URL = "/getCard?idMember=" + idMember;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(BASE_URI + HAS_CARD_URL, String.class);
    }
}
