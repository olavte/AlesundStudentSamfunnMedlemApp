package no.astudent.paymentservice.domain.vipps;

import lombok.Data;

@Data
public class AccessToken {
    // It’s a bearer type token. When used the word ‘Bearer’ must be added before the token value
    String token_type;

    // Token expiry duration in seconds
    String expires_in;

    // Any extra expiry time. This is zero only
    String ext_expires_in;

    // Token expiry time in epoch time format
    String expires_on;

    // Token creation time in epoch time format
    String not_before;

    // A common resource object that comes by default. Not used in token validation
    String resource;

    // The access token that needs to combined with 'Bearer ' in all calls to the main API
    String access_token;

    public String getAuthorization() {
        return "Bearer " + access_token;
    }
}