package aniruddha.example.VaccinationSystem.ResponseDto;

import aniruddha.example.VaccinationSystem.Model.Doctor;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AddAppointmentResponse {
    String personName;
    String doctorName;
    String appointmentId;
    Date appointmentDate;
    CenterResponseDto centerResponseDto;
}
