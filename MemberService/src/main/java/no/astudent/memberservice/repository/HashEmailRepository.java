package no.astudent.memberservice.repository;

import no.astudent.memberservice.entity.HashEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

import no.astudent.memberservice.entity.Member;

public interface HashEmailRepository extends JpaRepository<HashEmail, Long> {

    HashEmail findByIdMember(Long idMember);

    default HashEmail saveHash(HashEmail hashEmail) {
        hashEmail.setUpdated(java.sql.Date.valueOf(LocalDateTime.now().toLocalDate()));
        return save(hashEmail);
    }

}
