package aniruddha.example.VaccinationSystem.Controller;

import aniruddha.example.VaccinationSystem.Exception.BothDoseNotTakenException;
import aniruddha.example.VaccinationSystem.Exception.GenerateYourCertificateFirstException;
import aniruddha.example.VaccinationSystem.Exception.PersonDoesNotExitsException;
import aniruddha.example.VaccinationSystem.Model.Person;
import aniruddha.example.VaccinationSystem.Service.AppointmentService;
import aniruddha.example.VaccinationSystem.Service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/certificate")
public class CertificateController {
    @Autowired
    CertificateService certificateService;
    @PostMapping("/generate-certificate")
    public ResponseEntity generateCertificate(@RequestParam int personId) {
        try {
            String message = certificateService.generateCertificate(personId);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }catch (PersonDoesNotExitsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (BothDoseNotTakenException bothDoseNotTakenException) {
            return new ResponseEntity<>(bothDoseNotTakenException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/send-certificate")
    public ResponseEntity getCertificate(@RequestParam int personId) {
        try {
            String message = certificateService.getCertificate(personId);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }catch (PersonDoesNotExitsException exitsException) {
            return new ResponseEntity<>(exitsException.getMessage(), HttpStatus.NOT_FOUND);
        }catch (GenerateYourCertificateFirstException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
