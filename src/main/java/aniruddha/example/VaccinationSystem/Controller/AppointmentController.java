package aniruddha.example.VaccinationSystem.Controller;

import aniruddha.example.VaccinationSystem.RequestDTO.AddAppointmentRequest;
import aniruddha.example.VaccinationSystem.ResponseDto.AddAppointmentResponse;
import aniruddha.example.VaccinationSystem.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;
    @PostMapping("/book-appointment")
    public ResponseEntity addAppointment(@RequestBody AddAppointmentRequest addAppointmentRequest) {
        AddAppointmentResponse appointmentResponse =  appointmentService.addAppointment(addAppointmentRequest);
        return new ResponseEntity<>(appointmentResponse, HttpStatus.CREATED);
    }
}
