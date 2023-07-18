package aniruddha.example.VaccinationSystem.Exception;

public class DoctorDoesNotExitsException extends RuntimeException{
    public DoctorDoesNotExitsException(String message) {
        super(message);
    }
}
