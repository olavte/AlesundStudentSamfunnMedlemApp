package no.astudent.memberservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HashService {
    public ResponseEntity<String> validateHash(String sentHash, String receivedHash, Date sentDate, long expiration) {
        long currentDate = new Date().getTime();
        if ((currentDate - sentDate.getTime()) < expiration) {
            if (receivedHash.equals(sentHash)) {
                return new ResponseEntity<>("Hash is correct", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Hash is not correct", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Hash has expired", HttpStatus.BAD_REQUEST);
        }
    }
}
