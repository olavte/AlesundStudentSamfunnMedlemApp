package no.astudent.memberservice.repository;

import no.astudent.memberservice.entity.HashPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface HashPasswordRepository extends JpaRepository<HashPassword, Long> {

    HashPassword findByIdMember(Long idMember);

    default HashPassword saveHash(HashPassword hashPassword) {
        hashPassword.setUpdated(java.sql.Date.valueOf(LocalDateTime.now().toLocalDate()));
        return save(hashPassword);
    }

}
