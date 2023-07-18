package aniruddha.example.VaccinationSystem.Exception;

public class DoseTwoAlreadyTaken extends RuntimeException{
    public DoseTwoAlreadyTaken(String message) {
        super(message);
    }
}
