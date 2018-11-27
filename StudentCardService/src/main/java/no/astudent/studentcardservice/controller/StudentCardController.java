package no.astudent.studentcardservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.astudent.studentcardservice.entity.StudentCard;
import no.astudent.studentcardservice.service.StudentCardService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentCardController {

    private final StudentCardService studentCardService;

    private ObjectMapper mapper;

    @Autowired
    public StudentCardController(StudentCardService studentCardService) {
        this.studentCardService = studentCardService;
        mapper = new ObjectMapper();
    }

    @RequestMapping(value = "/addCard", method = RequestMethod.POST)
    public ResponseEntity<String> addCard(HttpEntity<String> httpEntity) {
        JSONObject json;
        try {
            json = new JSONObject(httpEntity.getBody());
            StudentCard studentCard = studentCardService.getCard(json.getLong("idMember"));
            if(studentCard == null) {
                StudentCard newStudentCard = new StudentCard(json.getLong("idMember"), json.getString("imageBlob"));
                studentCardService.persistCard(newStudentCard);
            } else {
                studentCard.setImageBlob(json.getString("imageBlob"));
                studentCardService.persistCard(studentCard);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("OK");
    }

    @RequestMapping(value = "/getCard", method = RequestMethod.GET)
    public ResponseEntity getCard(@RequestParam(value = "idMember") Long idMember) {
        StudentCard studentCard = studentCardService.getCard(idMember);
        if(studentCard != null) {
            try {
                String cardObject = mapper.writeValueAsString(studentCard);
                return new ResponseEntity<>(cardObject, HttpStatus.OK);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/getCardStatus", method = RequestMethod.GET)
    public ResponseEntity setCardStatus(@RequestParam(value = "idMember") Long idMember) {
        StudentCard studentCard = studentCardService.getCard(idMember);
        return new ResponseEntity<>(studentCard.getStatus().toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/setCardStatus", method = RequestMethod.GET)
    public ResponseEntity setCardStatus(@RequestParam(value = "idMember") Long idMember,
                                        @RequestParam(value = "status") String status) {
        studentCardService.setCardStatus(idMember, status);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @Transactional(readOnly=true)
    @RequestMapping(value = "/getAllWaitingStudentCards", method = RequestMethod.GET)
    public ResponseEntity setCardStatus() {
        try {
            String list = mapper.writeValueAsString(studentCardService.getAllWaitingStudentCards());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
