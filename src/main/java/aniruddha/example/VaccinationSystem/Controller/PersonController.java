package aniruddha.example.VaccinationSystem.Controller;

import aniruddha.example.VaccinationSystem.Exception.PersonEmailDoesNotExitsException;
import aniruddha.example.VaccinationSystem.Model.Person;
import aniruddha.example.VaccinationSystem.RequestDTO.PersonDto;
import aniruddha.example.VaccinationSystem.ResponseDto.GetFemalesByDoseOne;
import aniruddha.example.VaccinationSystem.ResponseDto.PersonByAgeResponse;
import aniruddha.example.VaccinationSystem.ResponseDto.PersonResponse;
import aniruddha.example.VaccinationSystem.ResponseDto.PersonWithBothDoseResponse;
import aniruddha.example.VaccinationSystem.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    PersonService personService;

    @PostMapping("/add-person")
    public ResponseEntity addPerson(@RequestBody PersonDto person) {
        try {
            PersonResponse person1 = personService.addPerson(person);
            return new ResponseEntity<>(person1, HttpStatus.OK);
        }catch (RuntimeException runtimeException) {
            return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.ALREADY_REPORTED);
        }
    }

    @PutMapping("/update-email")
    public ResponseEntity updateEmail(@RequestParam String oldEmail, @RequestParam String newEmail) {
        try {
            PersonResponse personUpdateEmailResponse = personService.updateEmail(oldEmail, newEmail);
            return new ResponseEntity<>(personUpdateEmailResponse, HttpStatus.ACCEPTED);
        }catch (PersonEmailDoesNotExitsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // get all males of age greater than a certain age
    @GetMapping("/get-male-by")
    public ResponseEntity getAllMalesWhoAreGraterThenACertainAge(@RequestParam int age) {
        List<PersonByAgeResponse> personsList = personService.getAllMalesWhoAreGraterThenACertainAge(age);
        return new ResponseEntity<>(personsList, HttpStatus.OK);
    }

    // get all females who have taken only dose 1 not dose 2
    @GetMapping("/get-dose-one-who-have-only-dose-one")
    public ResponseEntity getAllFemalesWhoDoneWithOnlyDoseOne() {
        List<GetFemalesByDoseOne> females = personService.getAllFemalesWhoDoneWithOnlyDoseOne();
        return new ResponseEntity<>(females, HttpStatus.OK);
    }

    // get all the people who are fully vaccinated
    @GetMapping("/get-people-with-both-dose")
    public ResponseEntity getALlPeopleWhoHaveDoneWithBothDose() {
        List<PersonWithBothDoseResponse> personResponseList = personService.getALlPeopleWhoHaveDoneWithBothDose();
        return new ResponseEntity<>(personResponseList, HttpStatus.FOUND);
    }

    // get all the people who have not taken even a single dose
    @GetMapping("/not-take-single-dose")
    public ResponseEntity getAlPeopleWhoDoNotTakeSingleDose() {
        List<PersonResponse> personResponses = personService.getAlPeopleWhoDoNotTakeSingleDose();
        return new ResponseEntity(personResponses, HttpStatus.FOUND);
    }

    // get all females whose age is greater than a particular age and who have taken only dose 1
    @GetMapping("/get-female")
    public ResponseEntity getAllFemaleWhoIsGraterThenAParticularAgeAndTakeOnlyOneDose(@RequestParam int age) {
        List<GetFemalesByDoseOne> females = personService.getAllFemaleWhoIsGraterThenAParticularAgeAndTakeOnlyOneDose(age);
        return new ResponseEntity<>(females, HttpStatus.FOUND);
    }

    // get all males whose age is greater than a particular age and who have taken both
    @GetMapping("/male-taken-both-dose")
    public ResponseEntity getAllMaleWhoIsGraterThenAParticularAgeAndWhoTakenBothDose(@RequestParam int age) {
        List<PersonWithBothDoseResponse> responseList = personService.getAllMaleWhoIsGraterThenAParticularAgeAndWhoTakenBothDose(age);
        return new ResponseEntity<>(responseList, HttpStatus.FOUND);
    }
}
