package aniruddha.example.VaccinationSystem.RequestDTO;

import aniruddha.example.VaccinationSystem.Enum.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PersonDto {
    String name;

    String emailId;

    Gender gender;

    int age;

    int mobileNo;
}
