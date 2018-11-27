package no.astudent.memberservice.service;

import no.astudent.memberservice.entity.HashPassword;
import no.astudent.memberservice.entity.Member;
import no.astudent.memberservice.repository.HashPasswordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HashPasswordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HashEmailService.class);

    private final HashPasswordRepository hashPasswordRepository;
    private final EmailService emailService;
    private final HashService hashService;

    private final long EXPIRATION_TIME = 7200000;

    @Autowired
    public HashPasswordService(HashPasswordRepository hashPasswordRepository,
                               EmailService emailService,
                               HashService hashService) {
        this.hashPasswordRepository = hashPasswordRepository;
        this.emailService = emailService;
        this.hashService = hashService;
    }

    public ResponseEntity<String> sendPasswordReset(Member member) {
        HashPassword hashPassword = hashPasswordRepository.findByIdMember(member.getIdMember());
        String response;
        if (hashPassword != null) {
            hashPassword.setVerificationHash(java.util.UUID.randomUUID().toString());
            response = "Updated the hash and sent new verification email";
        } else {
            hashPassword = new HashPassword(member.getIdMember());
            response = "Created a new hash and sent verification email";
        }
        if(emailService.sendPasswordResetEmail(hashPassword.getVerificationHash(), member.getEmail(), member.getName())) {
            hashPasswordRepository.saveHash(hashPassword);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("email-service returned false", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> verifyPasswordResetHash(Member member, String hash) {
        HashPassword hashPassword = hashPasswordRepository.findByIdMember(member.getIdMember());
        if (hashPassword != null) {
            return hashService.validateHash(hashPassword.getVerificationHash(), hash, hashPassword.getUpdated(), EXPIRATION_TIME);
        } else {
            return new ResponseEntity<>("Member has no verifications", HttpStatus.BAD_REQUEST);
        }
    }
}
