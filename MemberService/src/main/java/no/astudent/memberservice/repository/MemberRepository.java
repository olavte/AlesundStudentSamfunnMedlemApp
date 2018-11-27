package no.astudent.memberservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import no.astudent.memberservice.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);
}
