package aniruddha.example.VaccinationSystem.Exception;

public class PersonAlreadyHaveException extends RuntimeException{
    public PersonAlreadyHaveException(String message) {
        super(message);
    }
}
