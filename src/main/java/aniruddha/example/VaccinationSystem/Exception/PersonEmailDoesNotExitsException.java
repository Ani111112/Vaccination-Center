package aniruddha.example.VaccinationSystem.Exception;

public class PersonEmailDoesNotExitsException extends RuntimeException{
    public PersonEmailDoesNotExitsException(String message) {
        super(message);
    }
}
