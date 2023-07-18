package aniruddha.example.VaccinationSystem.Controller;

import aniruddha.example.VaccinationSystem.Enum.DoseType;
import aniruddha.example.VaccinationSystem.Exception.PersonDoesNotExitsException;
import aniruddha.example.VaccinationSystem.ResponseDto.DoseOneResponseDTO;
import aniruddha.example.VaccinationSystem.ResponseDto.DoseTwoResponseDTO;
import aniruddha.example.VaccinationSystem.Service.DoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dose")
public class DoseController {
    @Autowired
    DoseService doseService;

    @GetMapping("/get-dose-one")
    public ResponseEntity getDose1(@RequestParam("id") int personId, @RequestParam("dosetype") DoseType doseType) {
        try {
            DoseOneResponseDTO dose = doseService.getDose1(personId, doseType);
            return new ResponseEntity(dose, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.ALREADY_REPORTED);
        }
    }
    @GetMapping("/get-dose-two")
    public ResponseEntity getDose2(@RequestParam("id")int personId) {
        try {
            DoseTwoResponseDTO doseTwoResponseTwo = doseService.getDose2(personId);
            return new ResponseEntity<>(doseTwoResponseTwo, HttpStatus.OK);
        }catch (PersonDoesNotExitsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
