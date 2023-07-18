package aniruddha.example.VaccinationSystem.ResponseDto;

import aniruddha.example.VaccinationSystem.Enum.CenterType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CenterResponseDto {
    String centerName;

    CenterType centerType;

    String address;
}
