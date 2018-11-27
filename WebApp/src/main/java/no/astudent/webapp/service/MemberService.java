package no.astudent.webapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.astudent.webapp.Member;
import no.astudent.webapp.security.MyUserDetailsService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class MemberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberService.class);

    private String BASE_URI = "http://localhost:8081";

    private final MyUserDetailsService myUserDetailsService;

    @Autowired
    public MemberService(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void getMembersOnServerStartup() {
        getAllMembers();
    }

    public ResponseEntity<String> isEmailTaken(String email) {
        String IS_EMAIL_TAKEN_URI = "/isEmailTaken";
        RestTemplate restTemplate = new RestTemplate();
        JSONObject json = new JSONObject();
        json.put("email", email);
        return restTemplate.postForEntity(BASE_URI + IS_EMAIL_TAKEN_URI, json.toString(), String.class);
    }

    public ResponseEntity<String> newMember(String name, String email, String password, String phoneNumber, String birth) {
        ResponseEntity<String> response;
        try{
            String NEW_MEMBER_URI = "/newMember";
            RestTemplate restTemplate = new RestTemplate();
            JSONObject json = new JSONObject();
            json.put("name", name);
            json.put("email", email);
            json.put("password", password);
            json.put("phone", phoneNumber);
            json.put("birth", birth);
            response = restTemplate.postForEntity(BASE_URI + NEW_MEMBER_URI, json.toString(), String.class);
            getAllMembers();
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    public ResponseEntity<String> getAllMembers() {
        ResponseEntity<String> response;
        try{
            String NEW_MEMBER_URI = "/getAllMembers";
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.getForEntity(BASE_URI + NEW_MEMBER_URI, String.class);
            if(response.getBody() != null) {
                ObjectMapper mapper = new ObjectMapper();
                List<Member> myObjects = mapper.readValue(response.getBody(), new TypeReference<List<Member>>(){});
                myUserDetailsService.setMembers(myObjects);
            }
        } catch (HttpClientErrorException e) {
            LOGGER.warn(e.getMessage());
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException | ResourceAccessException e) {
            LOGGER.warn(e.getMessage());
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    public ResponseEntity<String> sendEmailVerification(String email) {
        String SEND_EMAIL_VERIFICATION_URI = "/sendEmailVerification";
        RestTemplate restTemplate = new RestTemplate();
        JSONObject json = new JSONObject();
        json.put("email", email);
        return restTemplate.postForEntity(BASE_URI + SEND_EMAIL_VERIFICATION_URI, json.toString(), String.class);
    }

    public ResponseEntity<String> verifyEmailVerificationHash(String email, String hash) {
        ResponseEntity<String> response;
        try{
            String VERIFY_EMAIL_HASH = "/verifyEmailVerificationHash";
            RestTemplate restTemplate = new RestTemplate();
            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("hash", hash);
            response = restTemplate.postForEntity(BASE_URI + VERIFY_EMAIL_HASH, json.toString(), String.class);
            getAllMembers();
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    public ResponseEntity<String> sendPasswordReset(String email) {
        String SEND_PASSWORD_RESET_URI = "/sendPasswordReset";
        RestTemplate restTemplate = new RestTemplate();
        JSONObject json = new JSONObject();
        json.put("email", email);
        return restTemplate.postForEntity(BASE_URI + SEND_PASSWORD_RESET_URI, json.toString(), String.class);
    }

    public ResponseEntity<String> verifyPasswordResetHash(String email, String hash) {
        ResponseEntity<String> response;
        try{
            String VERIFY_PASSWORD_HASH = "/verifyPasswordResetHash";
            RestTemplate restTemplate = new RestTemplate();
            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("hash", hash);
            response = restTemplate.postForEntity(BASE_URI + VERIFY_PASSWORD_HASH, json.toString(), String.class);
            getAllMembers();
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    public ResponseEntity<String> resetPassword(String email, String password) {
        ResponseEntity<String> response;
        try{
            String SET_PASSWORD = "/resetPassword";
            RestTemplate restTemplate = new RestTemplate();
            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("password", password);
            response = restTemplate.postForEntity(BASE_URI + SET_PASSWORD, json.toString(), String.class);
            getAllMembers();
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }
}
