package no.astudent.memberservice;

import no.astudent.memberservice.entity.Member;
import no.astudent.memberservice.repository.AuthorityRepository;
import no.astudent.memberservice.repository.MemberRepository;
import no.astudent.memberservice.service.HashEmailService;
import no.astudent.memberservice.service.MemberService;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Ignore
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class MemberServiceTests {

    @TestConfiguration
    static class MemberServiceTestsContextConfiguration {
        @Bean
        public MemberService memberService(MemberRepository memberRepository, AuthorityRepository authorityRepository) {
            return new MemberService(memberRepository, authorityRepository);
        }
    }

    @Autowired
    private MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private HashEmailService hashEmailService;

    private List<Member> memberList = new ArrayList<>();

//    @Before
//    public void setUp() {
//        memberList = Arrays.asList(
//                new Member("Oliver", "tellnesoliver@gmail.com", "pw1asd", "130390", false),
//                new Member("Olav", "olav.telseth94@hotmail.com", "pw2asd", "160894", false),
//                new Member("Frank Even", "frevalde@gmail.com", "pw3asd", "241292", false));
//
//        for (Member member : memberList) {
//            Mockito.when(memberRepository.findOne(member.getIdMember()))
//                    .thenReturn(member);
//        }
//    }
//
//    @Test
//    public void whenOneVerifiedMemberOutOfTwo_thenReturnOne() {
//        // Create new members and add them to the "memberRepository"
//        Member verifiedMember = new Member(4L, "Aron", "aronmar@live.no", "pw4asd", "140497", true);
//        Member notVerifiedMember = new Member(5L, "Kjetil", "kjetilhammerseth@gmail.com", "pw5asd", "181094", false);
//        memberRepository.save(verifiedMember);
//        memberRepository.save(notVerifiedMember);
//
//        List<Member> listOfVerifiedMembers = memberService.getVerifiedMembers();
//        assertEquals(1L, listOfVerifiedMembers.size());
//    }
//
//    @Test
//    public void whenAddMemberAndSendVerification_validMember_thenReturnTrue() {
//        Member member = new Member("Haakon", "test@example.com", "pw", "010190", false);
//        Member newMember = new Member(4L, "Haakon", "test@example.com", "pw", "010190", false);
//
//        // if the member is saved, it gets a id assigned
//        Mockito.when(memberRepository.save(member)).thenReturn(
//                new Member(4L, "Haakon", "test@example.com", "pw", "010190", false)
//        );
//        Mockito.when(hashService.sendVerification(newMember)).thenReturn(true);
//
//
//        boolean created = hashService.addMemberAndSendVerification(member);
//        //TODO FIX BROKEN TEST
//        assertFalse(created);
//    }
//
//    @Test
//    public void whenAddMemberAndSendVerification_emptyMember_thenThrowExceptionAndReturnFalse() {
//        Member member = new Member();
//
//        // if the member has null-values, throw exception
//        Mockito.when(memberRepository.save(member))
//                .thenThrow(new DataIntegrityViolationException(""));
//
//        boolean created = hashService.addMemberAndSendVerification(member);
//
//        assertFalse(created);
//    }
//
//    @Test
//    public void whenAddMemberAndSendVerification_nullMember_thenThrowExceptionAndReturnFalse() {
//        Member nullMember = null;
//
//        // if the member has null-values, throw exception
//        Mockito.when(memberRepository.save(nullMember))
//                .thenThrow(new DataIntegrityViolationException(""));
//
//        boolean created = hashService.addMemberAndSendVerification(nullMember);
//
//        assertFalse(created);
//    }
}