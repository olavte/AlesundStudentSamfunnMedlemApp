package no.astudent.webapp.security;

import no.astudent.webapp.Authority;
import no.astudent.webapp.Member;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    public static ArrayList<Member> members = new ArrayList();

    public MyUserDetailsService() {
        //in a real application, instead of using local data,
        // we will find user details by some other means e.g. from an external system
        Authority member = new Authority("MEMBER");
        Authority admin = new Authority("ADMIN");


        Member olavMember = new Member();
        olavMember.setIdMember(1L);
        olavMember.setName("Olav Telseth");
        olavMember.setEmail("olav.telseth9@hotmail.com");
        olavMember.setPassword("$2a$04$UBPIqesgYQUGI34zmI81H.pNbwhaGBa96PiC35eK4XfakJRukO1YC");
        olavMember.setPhone("48058993");
        olavMember.setAuthorities(new ArrayList<>(Arrays.asList(member)));
        olavMember.setVerified(true);
        olavMember.setRole("MEMBER");

        Member olavAdmin = new Member();
        olavAdmin.setIdMember(2L);
        olavAdmin.setName("Oluf Tellus");
        olavAdmin.setEmail("olav.telseth@hotmail.com");
        olavAdmin.setPassword("$2a$04$UBPIqesgYQUGI34zmI81H.pNbwhaGBa96PiC35eK4XfakJRukO1YC");
        olavAdmin.setPhone("48058993");
        olavAdmin.setAuthorities(new ArrayList<>(Arrays.asList(admin)));
        olavAdmin.setVerified(false);
        olavAdmin.setRole("FRIVILLIG");

        members.add(olavMember);
        members.add(olavAdmin);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member matchingMember = null;
        for(Member member : members) {
            if(member.getUsername().equals(username)) {
                matchingMember = member;
            }
        }
        if (matchingMember == null) {
            throw new UsernameNotFoundException("User not found by name: " + username);
        } else {
            return matchingMember;
        }
    }

    public void setMembers(List<Member> list) {
        members = new ArrayList<>(list);
    }
}
