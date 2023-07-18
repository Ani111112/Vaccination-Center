package aniruddha.example.VaccinationSystem.Exception;

public class PersonDoesNotExitsException extends RuntimeException{
    public PersonDoesNotExitsException(String message) {
        super(message);
    }
}
