package no.astudent.memberservice.service;

import no.astudent.memberservice.entity.Authority;
import no.astudent.memberservice.entity.AuthorityType;
import no.astudent.memberservice.entity.Role;
import no.astudent.memberservice.repository.AuthorityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import no.astudent.memberservice.entity.Member;
import no.astudent.memberservice.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberService.class);

    private final MemberRepository memberRepository;
    private final AuthorityRepository authorityRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, AuthorityRepository authorityRepository) {
        this.memberRepository = memberRepository;
        this.authorityRepository = authorityRepository;
    }

    public ResponseEntity<String> newMember(Member member) {
        Member foundMember = memberRepository.findByEmail(member.getEmail());
        if(foundMember == null) {
            Authority authority = new Authority();
            AuthorityType authorityType = AuthorityType.MEMBER;
            authority.setAuthority(authorityType);
            List<Authority> authorities = new ArrayList<>();
            authorities.add(authority);
            member.setAuthorities(authorities);
            member.setRole(Role.MEMBER);
            memberRepository.save(member);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Member already exists", HttpStatus.BAD_REQUEST);
        }
    }

    public Member getMemberWithId(Long idMember) {
        return memberRepository.findOne(idMember);
    }

    public Member getMemberWithEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public void verifyMember(Member member) {
        member.setVerified(true);
        memberRepository.save(member);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public ResponseEntity<String> resetPassword(String email, String password) {
        Member member = getMemberWithEmail(email);
        member.setPassword(password);
        memberRepository.save(member);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
