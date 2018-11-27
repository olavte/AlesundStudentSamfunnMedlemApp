package no.astudent.memberservice.repository;

import no.astudent.memberservice.entity.Authority;
import no.astudent.memberservice.entity.HashEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
