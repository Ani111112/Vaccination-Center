package aniruddha.example.VaccinationSystem.Controller;

import aniruddha.example.VaccinationSystem.RequestDTO.CenterDto;
import aniruddha.example.VaccinationSystem.ResponseDto.CenterResponseDto;
import aniruddha.example.VaccinationSystem.Service.VaccinationCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/center")
public class VaccinationCenterController {
    @Autowired
    VaccinationCenterService vaccinationCenterService;

    @PostMapping("/add-center")
    public ResponseEntity addCenter(@RequestBody CenterDto centerDto) {
        CenterResponseDto centerResponseDto = vaccinationCenterService.addCenter(centerDto);
        return new ResponseEntity<>(centerResponseDto, HttpStatus.OK);
    }

    // get all the doctors at centers of a particular centerType
    @GetMapping("/get-all-doctor")
    public ResponseEntity getAllDoctorOfACenterType(@RequestParam String centerName) {
        List<String> doctorList = vaccinationCenterService.getAllDoctorOfACenterType(centerName);
        return new ResponseEntity<>(doctorList, HttpStatus.OK);
    }

    // get the center with the highest number of doctors
    @GetMapping("/with-highest-number-of-doctor")
    public ResponseEntity getCenterWithHighestNumberOfDoctors() {
        List<CenterResponseDto> centerList = vaccinationCenterService.getCenterWithHighestNumberOfDoctors();
        return new ResponseEntity<>(centerList, HttpStatus.OK);
    }

    // get the center with the highest number of doctors among a particular centerType
    @GetMapping("/highest-number-of-doctor-of-a-particular-center")
    public ResponseEntity highestNumberOfDoctorOfACenterType(@RequestParam String centerType) {
        List<CenterResponseDto> centerList = vaccinationCenterService.highestNumberOfDoctorOfACenterType(centerType);
        return new ResponseEntity<>(centerList, HttpStatus.OK);
    }
}
