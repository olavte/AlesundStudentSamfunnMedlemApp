package no.astudent.memberservice;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import no.astudent.memberservice.entity.Member;
import no.astudent.memberservice.repository.MemberRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MemberServiceRunner.class)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private List<Member> memberList;

//    @Before
//    public void setUp() {
//        memberList = Arrays.asList(
//            new Member("Oliver", "tellnesoliver@gmail.com", "pw1asd", "130390", false),
//            new Member("Olav", "olav.telseth94@hotmail.com", "pw2asd", "160894", false),
//            new Member("Frank Even", "frevalde@gmail.com", "pw3asd", "241292", false));
//
//        memberRepository.save(memberList);
//    }
//
//    @Test
//    public void whenDeleteMember_thenFindAll_returnsOneLessMember() {
//        Member memberToDelete = memberRepository.findByEmail("frevalde@gmail.com");
//        memberRepository.delete(memberToDelete);
//        List<Member> members = memberRepository.findAll();
//
//        assertNotEquals(memberList.size(), members.size());
//        assertEquals(memberList.size() - 1, members.size());
//    }
//
//    @Test
//    public void whenFindMemberByEmailAndPasswordHash_validMember_thenReturnMemberWithEqualFields() {
//        int randomNumber = (int) (Math.random() * (memberList.size() - 1));
//        Member memberFromList = memberList.get(randomNumber);
//
//        Member sameMemberFromDatabase = memberRepository.findByEmailAndPasswordHash(
//            memberFromList.getEmail(), memberFromList.getPasswordHash());
//
//        assertEquals(memberFromList.getName(), sameMemberFromDatabase.getName());
//        assertEquals(memberFromList.getEmail(), sameMemberFromDatabase.getEmail());
//        assertEquals(memberFromList.getBirth(), sameMemberFromDatabase.getBirth());
//        assertEquals(memberFromList.getPasswordHash(), sameMemberFromDatabase.getPasswordHash());
//    }
//
//    @Test
//    public void whenFindMemberByEmailAndPasswordHash_thenReturnMember() {
//        Member member = memberRepository.findByEmailAndPasswordHash("tellnesoliver@gmail.com", "pw1asd");
//
//        assertNotNull(member);
//        assertEquals("Oliver", member.getName());
//    }
//
//    @Test
//    public void whenFindMemberByEmail_thenReturnMember() {
//        Member member = memberRepository.findByEmail("olav.telseth94@hotmail.com");
//
//        assertNotNull(member);
//        assertEquals("Olav", member.getName());
//    }
//
//    @Test
//    public void whenFindAllMembers_thenReturnAllMembers() {
//        List<Member> members = memberRepository.findAll();
//
//        assertEquals(memberList.size(), members.size());
//    }
//
//    @Test
//    public void whenSaveMember_validMember_thenReturnPersistedMember() {
//        Member newMember = new Member("Haakon", "test@example.com", "pw", "010190", false);
//        newMember = memberRepository.save(newMember);
//
//        Member persistedNewMember = memberRepository.findOne(newMember.getIdMember());
//
//        assertEquals(newMember, persistedNewMember);
//    }
//
//    @Test
//    public void whenSaveMember_thenFindAll_returnsOneMoreMember() {
//        Member newMember = new Member("Haakon Fivelstad", "leder@astudent.no", "åss", "170598", false);
//        memberRepository.save(newMember);
//        List<Member> members = memberRepository.findAll();
//
//        assertNotEquals(memberList.size(), members.size());
//        assertEquals(memberList.size() + 1, members.size());
//    }
//
//    @After
//    public void tearDown() {
//        memberRepository.deleteAll();
//    }
}