package no.astudent.studentcardservice.service;

import no.astudent.studentcardservice.entity.StudentCard;
import no.astudent.studentcardservice.entity.Status;
import no.astudent.studentcardservice.repository.StudentCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentCardService {
    private final StudentCardRepository studentCardRepository;

    @Autowired
    public StudentCardService(StudentCardRepository studentCardRepository) {
        this.studentCardRepository = studentCardRepository;
    }

    public void persistCard(StudentCard studentCard) {
        studentCardRepository.saveAndFlush(studentCard);
    }

    @Transactional
    public StudentCard getCard(Long idMember) {
        return studentCardRepository.findByIdMember(idMember);
    }

    public void setCardStatus(Long idMember, String status) {
        StudentCard studentCard = studentCardRepository.findByIdMember(idMember);
        studentCard.setStatus(Status.valueOf(status));
        studentCardRepository.save(studentCard);
    }

    public List<StudentCard> getAllWaitingStudentCards() {
        return studentCardRepository.findByStatus(Status.PENDING);
    }
}
