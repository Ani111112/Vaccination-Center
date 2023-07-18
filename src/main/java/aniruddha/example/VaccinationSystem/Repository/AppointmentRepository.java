package aniruddha.example.VaccinationSystem.Repository;

import aniruddha.example.VaccinationSystem.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
}
