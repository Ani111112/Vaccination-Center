package aniruddha.example.VaccinationSystem.RequestDTO;

import aniruddha.example.VaccinationSystem.Enum.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DoctorRequestDto {
    Integer centerId;
    String name;
    String emailId;
    int age;
    Gender gender;
}
