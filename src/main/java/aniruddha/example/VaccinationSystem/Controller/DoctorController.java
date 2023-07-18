package aniruddha.example.VaccinationSystem.Controller;

import aniruddha.example.VaccinationSystem.Exception.CenterDoestNotExitsException;
import aniruddha.example.VaccinationSystem.Exception.DoctorDoesNotExitsException;
import aniruddha.example.VaccinationSystem.RequestDTO.DoctorRequestDto;
import aniruddha.example.VaccinationSystem.ResponseDto.DoctorResponseDTO;
import aniruddha.example.VaccinationSystem.ResponseDto.DoctorUpdateAgeAndEmailResponse;
import aniruddha.example.VaccinationSystem.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    DoctorService doctorService;
    @PostMapping("/add-doctor")
    public ResponseEntity addDoctor(@RequestBody DoctorRequestDto doctorRequestDto) {
        try {
            DoctorResponseDTO doctorResponseDTO = doctorService.addDoctor(doctorRequestDto);
            return new ResponseEntity(doctorResponseDTO, HttpStatus.OK);
        }catch (CenterDoestNotExitsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // get the doctor with highest number of appointments
    //I have to check after complete appointment wala class
    @GetMapping("/get-highest-appointments-doctor")
    public ResponseEntity highestAppointments() {
        List<String> responseList = doctorService.highestAppointments();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    // get the list of doctors who belong to a particular center
    //I have to check after complete appointment wala class
    @GetMapping("/get-all-doctors-center")
    public ResponseEntity getAllDoctorOfACenter(String centerName) {
        try {
            List<DoctorResponseDTO> doctorResponseList = doctorService.getAllDoctorOfACenter(centerName);
            return new ResponseEntity<>(doctorResponseList, HttpStatus.OK);
        }catch (CenterDoestNotExitsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // api to update email and/or age of a doctor
    //might be new or age is blank so inthat case you need update only one thing which is given
    @PutMapping("/update-email-age")
    public ResponseEntity updateAgeAndEmail(String oldEmail, String newEmail, int age) {
        try {
            DoctorUpdateAgeAndEmailResponse updateAgeAndEmailResponse = doctorService.updateAgeAndEmail(oldEmail, newEmail, age);
            return new ResponseEntity<>(updateAgeAndEmailResponse, HttpStatus.OK);
        }catch (DoctorDoesNotExitsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
