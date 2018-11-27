package no.astudent.webapp.controller;

import no.astudent.webapp.security.JWTTokenService;
import no.astudent.webapp.service.MemberService;
import no.astudent.webapp.service.PaymentService;
import no.astudent.webapp.service.StudentCardService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static no.astudent.webapp.security.SecurityConstants.HEADER_STRING;

@RestController
public class RESTController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RESTController.class);

    private final MemberService memberService;

    private final PaymentService paymentService;

    private final StudentCardService studentCardService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RESTController(MemberService memberService, PaymentService paymentService,
                          PasswordEncoder passwordEncoder, StudentCardService studentCardService) {
        this.memberService = memberService;
        this.paymentService = paymentService;
        this.passwordEncoder = passwordEncoder;
        this.studentCardService = studentCardService;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity login(HttpEntity<String> httpEntity) {
        String json = httpEntity.getBody();
        if (json == null) {
            json = "";
        }
        return ResponseEntity.ok(json);
    }

    @RequestMapping(value = "perform_logout", method = RequestMethod.GET)
    public ResponseEntity logout(HttpServletRequest request,
                                 HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setValue(null);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return ResponseEntity.ok("cookiesDeleted");
    }

    @RequestMapping(value = "isEmailTaken", method = RequestMethod.POST)
    public ResponseEntity<String> isEmailTaken(HttpEntity<String> httpEntity) {
        String body = httpEntity.getBody();
        if (body != null) {
            JSONObject json = new JSONObject(body);
            return memberService.isEmailTaken(json.getString("email"));
        }
        return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "newMember", method = RequestMethod.POST)
    public ResponseEntity<String> newMember(HttpEntity<String> httpEntity) {
        String body = httpEntity.getBody();
        if (body != null) {
            JSONObject json = new JSONObject(body);
            return memberService.newMember(
                    json.getString("name"),
                    json.getString("email"),
                    passwordEncoder.encode(json.getString("password")),
                    json.getString("phone"),
                    json.getString("birth")
            );
        }
        return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/sendEmailVerification", method = RequestMethod.POST)
    public ResponseEntity sendEmailVerification(HttpEntity<String> httpEntity) {
        String body = httpEntity.getBody();
        if (body != null) {
            JSONObject json = new JSONObject(body);
            return memberService.sendEmailVerification(json.getString("email"));
        }
        return null;
    }

    @RequestMapping(value = "/sendPasswordResetEmail", method = RequestMethod.POST)
    public ResponseEntity sendPasswordResetEmail(HttpEntity<String> httpEntity) {
        String body = httpEntity.getBody();
        if (body != null) {
            JSONObject json = new JSONObject(body);
            return memberService.sendPasswordReset(json.getString("email"));
        }
        return null;
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ResponseEntity<String> resetPassword(HttpEntity<String> httpEntity, HttpServletRequest req) {
        Cookie cookie = WebUtils.getCookie(req, "passwordResetAllowance");
        String body = httpEntity.getBody();
        if (body != null) {
            JSONObject json = new JSONObject(body);
            if(cookie != null) {
                if(cookie.getValue() != null && cookie.getName().equals("passwordResetAllowance")) {
                    return memberService.resetPassword(
                            cookie.getValue(),
                            passwordEncoder.encode(json.getString("password")));
                }
            }
        }
        return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/decodeToken", method = RequestMethod.GET)
    public ResponseEntity<String> decodeToken(HttpServletRequest request) {

        Cookie cookie = WebUtils.getCookie(request, HEADER_STRING);
        if (cookie != null) {
            String jwtToken = cookie.getValue();
            JWTTokenService jwtTokenService = new JWTTokenService();
            String jwtTokenString = jwtTokenService.parseToken(jwtToken);
            LOGGER.info(jwtTokenString);
            return new ResponseEntity<>(jwtTokenString, HttpStatus.OK);
        }
        return null;
    }

    @RequestMapping(value = "/uploadStudentCard", method = RequestMethod.POST)
    public ResponseEntity<String> uploadStudentCard(HttpEntity<String> entity) {
        if (entity.getBody() != null) {
            JSONObject json = new JSONObject(entity.getBody());
            return studentCardService.uploadStudentCard(json.getLong("idMember"), json.getString("image"));
        }
        return null;
    }

    @RequestMapping(value = "/hasUploadedStudentCard", method = RequestMethod.GET)
    public ResponseEntity<String> hasUploadedStudentCard(@RequestParam(value = "idMember") Long idMember) {
        ResponseEntity<String> responseEntity;
        try {
            responseEntity = studentCardService.hasUploadedStudentCard(idMember);
        } catch (HttpClientErrorException e) {
            responseEntity = new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/getAllWaitingStudentCards", method = RequestMethod.GET)
    public ResponseEntity<String> getAllWaitingStudentCards() {
        return studentCardService.getAllWaitingStudentCards();
    }

    @RequestMapping(value = "/startVippsPayment", method = RequestMethod.POST)
    public ResponseEntity<String> startVippsPayment(HttpServletRequest request, HttpEntity<String> entity) {
        try {
            if (entity.getBody() != null) {
                JSONObject json = new JSONObject(entity.getBody());
                Cookie cookie = WebUtils.getCookie(request, HEADER_STRING);
                if (cookie != null) {
                    JWTTokenService jwtTokenService = new JWTTokenService();
                    String url = paymentService.startVippsPayment(
                            jwtTokenService.tokenToMember(cookie.getValue()),
                            json.getString("phone"),
                            json.getString("discount")
                    );
                    JSONObject responseBody = new JSONObject();
                    responseBody.put("location", url);
                    return new ResponseEntity<>(responseBody.toString(), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/hasMemberPaid", method = RequestMethod.POST)
    public ResponseEntity<String> hasMemberPaid(HttpEntity<String> entity, HttpServletRequest request) {
        if (entity.getBody() != null) {
            JSONObject json = new JSONObject(entity.getBody());
            return paymentService.hasMemberPaid(json.getLong("idMember"));
        }
        return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping("/paymentinitiated")
    public void paymentInitiatedSuccessfully(@RequestParam(value = "id") String orderId) {
        paymentService.vippsInitiatePaymentSuccessful(orderId);
    }
}
