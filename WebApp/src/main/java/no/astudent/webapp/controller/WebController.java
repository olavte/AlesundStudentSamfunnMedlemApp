package no.astudent.webapp.controller;

import no.astudent.webapp.service.MemberService;
import no.astudent.webapp.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

import static no.astudent.webapp.security.SecurityConstants.HEADER_STRING;

@Controller
public class WebController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebController.class);

    private final MemberService memberService;

    private final PaymentService paymentService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebController(MemberService memberService, PaymentService paymentService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.paymentService = paymentService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "member", method = RequestMethod.GET)
    public String member() {
        return "member.html";
    }

    @RequestMapping(value = "verify", method = RequestMethod.GET)
    public String verify(@RequestParam(value = "code", defaultValue = "") String verificationCode,
                         @RequestParam(value = "email", defaultValue = "") String email) {
        HttpStatus status = memberService.verifyEmailVerificationHash(email, verificationCode).getStatusCode();
        if (status == HttpStatus.OK) {
            LOGGER.info("OK " + status);
            return "endpoint/verifySuccess.html";
        } else {
            LOGGER.info("BAD " + status);
            return "endpoint/verifyFailed.html";
        }
    }

    @RequestMapping(value = "resetPassword", method = RequestMethod.GET)
    public String setNewPassword(@RequestParam(value = "code", defaultValue = "") String verificationCode,
                                 @RequestParam(value = "email", defaultValue = "") String email,
                                 HttpServletResponse res) {
        if (memberService.verifyPasswordResetHash(email, verificationCode).getStatusCode() == HttpStatus.OK) {
            Cookie cookie = new Cookie("passwordResetAllowance", email);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60);
            cookie.setHttpOnly(true);
            res.addCookie(cookie);
            return "endpoint/setPassword.html";
        } else {
            return null;
        }
    }

    @RequestMapping(value = "paymentComplete", method = RequestMethod.GET)
    public String paymentComplete(@RequestParam(value = "idMember") Long idMember) {
        try {
            ResponseEntity<String> entity = paymentService.hasMemberPaid(idMember);
            String body = entity.getBody();
            if(body != null) {
                if(entity.getBody().equals("true")) {
                    return "endpoint/paymentComplete.html";
                }
            }
        } catch (HttpClientErrorException e) {
            return "endpoint/paymentFailed.html";
        }
        return "endpoint/paymentFailed.html";
    }

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public String admin() {
        return "admin.html";
    }
}
