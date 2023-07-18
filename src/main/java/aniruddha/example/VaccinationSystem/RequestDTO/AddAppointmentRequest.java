package aniruddha.example.VaccinationSystem.RequestDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AddAppointmentRequest {
    int personId;
    int doctorId;
}
