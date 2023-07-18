package aniruddha.example.VaccinationSystem.ResponseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DoctorResponseDTO {
    String name;
    String message;
    CenterResponseDto centerResponseDto;
}
