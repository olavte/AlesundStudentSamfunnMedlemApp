package no.astudent.webapp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import no.astudent.webapp.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static no.astudent.webapp.security.SecurityConstants.HEADER_STRING;
import static no.astudent.webapp.security.SecurityConstants.SECRET;
import static no.astudent.webapp.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    private ObjectMapper mapper;
    private JWTTokenService jwtTokenService;

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
        mapper = new ObjectMapper();
        jwtTokenService = new JWTTokenService();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        Cookie cookie = WebUtils.getCookie(req, HEADER_STRING);
        if(cookie != null && cookie.getName().equals(HEADER_STRING) && cookie.getValue() != null) {
            UsernamePasswordAuthenticationToken authentication
                    = getAuthentication(cookie.getValue());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (token != null) {
            Member member = jwtTokenService.tokenToMember(token);
            if (member != null) {
                return new UsernamePasswordAuthenticationToken(
                        member.getUsername(),
                        null,
                        member.getAuthorities());
            }
            return null;
        }
        return null;
    }
}
