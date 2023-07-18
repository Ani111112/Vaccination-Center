package aniruddha.example.VaccinationSystem.RequestDTO;

import aniruddha.example.VaccinationSystem.Enum.CenterType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CenterDto {
    String centerName;

    CenterType centerType;

    String address;
}
