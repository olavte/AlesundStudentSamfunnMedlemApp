package no.astudent.memberservice.service;

import no.astudent.memberservice.entity.HashEmail;
import no.astudent.memberservice.entity.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import no.astudent.memberservice.repository.HashEmailRepository;

@Service
public class HashEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HashEmailService.class);

    private final HashEmailRepository hashEmailRepository;
    private final EmailService emailService;
    private final HashService hashService;

    private final long EXPIRATION_TIME = 7200000;


    @Autowired
    public HashEmailService(HashEmailRepository hashEmailRepository,
                            EmailService emailService,
                            HashService hashService) {
        this.hashEmailRepository = hashEmailRepository;
        this.emailService = emailService;
        this.hashService = hashService;
    }

    public ResponseEntity<String> sendEmailVerification(Member member) {
        HashEmail hashEmail = hashEmailRepository.findByIdMember(member.getIdMember());
        String response;
        if(hashEmail != null) {
            hashEmail.setVerificationHash(java.util.UUID.randomUUID().toString());
            response = "Updated the hash and sent new verification email";
        } else {
            hashEmail = new HashEmail(member.getIdMember());
            response = "Created a new hash and sent verification email";
        }
        if(emailService.sendVerificationEmail(hashEmail.getVerificationHash(), member.getEmail(), member.getName())) {
            hashEmailRepository.saveHash(hashEmail);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("email-service returned false", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> verifyEmailVerificationHash(Member member, String hash) {
        HashEmail hashEmail = hashEmailRepository.findByIdMember(member.getIdMember());
        if(hashEmail != null) {
            return hashService.validateHash(hashEmail.getVerificationHash(), hash, hashEmail.getUpdated(), EXPIRATION_TIME);
        } else {
            return new ResponseEntity<>("Member has no verifications", HttpStatus.BAD_REQUEST);
        }
    }
}
