package no.astudent.memberservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.astudent.memberservice.service.HashEmailService;
import no.astudent.memberservice.service.HashPasswordService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import no.astudent.memberservice.entity.Member;
import no.astudent.memberservice.service.MemberService;

@RestController
public class MemberController {

    private final MemberService memberService;
    private final HashEmailService hashEmailService;
    private final HashPasswordService hashPasswordService;
    private ObjectMapper mapper;

    @Autowired
    public MemberController(MemberService memberService,
                            HashEmailService hashEmailService,
                            HashPasswordService hashPasswordService) {
        this.memberService = memberService;
        this.hashEmailService = hashEmailService;
        this.hashPasswordService = hashPasswordService;
        mapper = new ObjectMapper();
    }

    @RequestMapping(value = "/isEmailTaken", method = RequestMethod.POST)
    public ResponseEntity<String> isEmailTaken(HttpEntity<String> httpEntity) {
        String body = httpEntity.getBody();
        if(body != null) {
            try {
                JSONObject json = new JSONObject(body);
                Member member = memberService.getMemberWithEmail(json.getString("email"));
                if(member == null) {
                    return new ResponseEntity<>("email is available", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Body is null", HttpStatus.FOUND);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Body is null", HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/newMember", method = RequestMethod.POST)
    public ResponseEntity<String> newMember(HttpEntity<String> httpEntity) {
        String body = httpEntity.getBody();
        if(body != null) {
            try {
                JSONObject json = new JSONObject(body);
                return memberService.newMember(new Member(
                        json.getString("name"),
                        json.getString("email"),
                        json.getString("password"),
                        json.getString("phone"),
                        json.getString("birth"), false));
            } catch (JSONException e) {
                e.printStackTrace();
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Body is null", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/getAllMembers", method = RequestMethod.GET)
    public ResponseEntity<String> getAllMembers() {
        try {
            String list = mapper.writeValueAsString(memberService.getAllMembers());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/sendEmailVerification", method = RequestMethod.POST)
    public ResponseEntity<String> verifyMember(HttpEntity<String> httpEntity) {
        String body = httpEntity.getBody();
        if(body != null) {
            try {
                JSONObject json = new JSONObject(body);
                return hashEmailService.sendEmailVerification(
                        memberService.getMemberWithEmail(json.getString("email")));
            } catch (JSONException e) {
                e.printStackTrace();
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Body is null", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/verifyEmailVerificationHash", method = RequestMethod.POST)
    public ResponseEntity<String> verifyEmailVerificationHash(HttpEntity<String> httpEntity) {
        String body = httpEntity.getBody();
        if(body != null) {
            try {
                JSONObject json = new JSONObject(body);
                Member member = memberService.getMemberWithEmail(json.getString("email"));
                String hash = json.getString("hash");
                ResponseEntity<String> response = hashEmailService.verifyEmailVerificationHash(member, hash);
                if(response.getStatusCode() == HttpStatus.OK) {
                    memberService.verifyMember(member);
                }
                return response;
            } catch (JSONException e) {
                e.printStackTrace();
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Body is null", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/sendPasswordReset", method = RequestMethod.POST)
    public ResponseEntity<String> sendPasswordReset(HttpEntity<String> httpEntity) {
        String body = httpEntity.getBody();
        if(body != null) {
            try {
                JSONObject json = new JSONObject(body);
                return hashPasswordService.sendPasswordReset(
                        memberService.getMemberWithEmail(json.getString("email")));
            } catch (JSONException e) {
                e.printStackTrace();
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Body is null", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/verifyPasswordResetHash", method = RequestMethod.POST)
    public ResponseEntity<String> verifyPasswordResetHash(HttpEntity<String> httpEntity) {
        String body = httpEntity.getBody();
        if(body != null) {
            try {
                JSONObject json = new JSONObject(body);
                Member member = memberService.getMemberWithEmail(json.getString("email"));
                String hash = json.getString("hash");
                ResponseEntity<String> response = hashPasswordService.verifyPasswordResetHash(member, hash);
                return response;
            } catch (JSONException e) {
                e.printStackTrace();
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Body is null", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ResponseEntity<String> resetPassword(HttpEntity<String> httpEntity) {
        String body = httpEntity.getBody();
        if(body != null) {
            try {
                JSONObject json = new JSONObject(body);
                return memberService.resetPassword(json.getString("email"), json.getString("password"));
            } catch (JSONException e) {
                e.printStackTrace();
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Body is null", HttpStatus.BAD_REQUEST);
    }
}
