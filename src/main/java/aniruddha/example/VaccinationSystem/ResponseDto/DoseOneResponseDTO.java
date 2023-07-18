package aniruddha.example.VaccinationSystem.ResponseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DoseOneResponseDTO {
    String name;
    String message;

}
