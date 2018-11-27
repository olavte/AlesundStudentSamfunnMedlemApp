package no.astudent.webapp.security;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;
import no.astudent.webapp.Member;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

import static no.astudent.webapp.security.SecurityConstants.*;

@Service
@NoArgsConstructor
public class JWTTokenService {

    private ObjectMapper mapper = new ObjectMapper();

    public String parseToken(String token) {
        String fullToken = TOKEN_PREFIX + token;
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(fullToken.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
    }

    public String buildToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public Member tokenToMember(String token) {
        Member member = null;
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        try {
            String json = parseToken(token);
            member = mapper.readValue(json, Member.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return member;
    }
}
