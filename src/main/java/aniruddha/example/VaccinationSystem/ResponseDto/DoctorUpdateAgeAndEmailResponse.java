package aniruddha.example.VaccinationSystem.ResponseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DoctorUpdateAgeAndEmailResponse {
    String name;
    String email;
    int age;
    String message;
}
