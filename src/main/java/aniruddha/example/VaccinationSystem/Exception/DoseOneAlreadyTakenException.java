package aniruddha.example.VaccinationSystem.Exception;

public class DoseOneAlreadyTakenException extends RuntimeException{
    public DoseOneAlreadyTakenException(String message) {
        super(message);
    }
}
