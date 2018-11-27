package no.astudent.webapp;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@JsonPropertyOrder({
        "idMember",
        "name",
        "email",
        "password",
        "phone",
        "birth" ,
        "role",
        "verified",
        "authorities"
})
public class Member implements UserDetails {

    private Long idMember;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String birth;
    private String role;
    private boolean verified;
    private ArrayList<Authority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
