package no.astudent.studentcardservice.repository;

import no.astudent.studentcardservice.entity.StudentCard;
import no.astudent.studentcardservice.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentCardRepository extends JpaRepository<StudentCard, Long> {

    StudentCard findByIdMember(Long idMember);

    List<StudentCard> findByStatus(Status status);
}
