package no.astudent.webapp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.astudent.webapp.Member;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static no.astudent.webapp.security.SecurityConstants.HEADER_STRING;
import static no.astudent.webapp.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    private AuthenticationManager authenticationManager;
    private ObjectMapper mapper;
    private JWTTokenService jwtTokenService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        mapper = new ObjectMapper();
        jwtTokenService = new JWTTokenService();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        JSONObject credentials;
        try {
            credentials = new JSONObject(req.getReader().readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getString("email"),
                        credentials.getString("password"),
                        new ArrayList<>())
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        Member member = (Member) auth.getPrincipal();

        LOGGER.info(member.toString());

        String subject = mapper.writeValueAsString(member);
        String token = jwtTokenService.buildToken(subject);
        Cookie cookie = new Cookie(HEADER_STRING, token);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        cookie.setHttpOnly(true);
        res.addCookie(cookie);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws BadCredentialsException {
        throw new BadCredentialsException("FEIL");
    }
}
