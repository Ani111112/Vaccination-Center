package aniruddha.example.VaccinationSystem.ResponseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GetFemalesByDoseOne {
    String name;
    int age;
    String doseName;
}
