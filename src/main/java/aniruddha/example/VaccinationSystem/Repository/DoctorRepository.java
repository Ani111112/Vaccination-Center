package aniruddha.example.VaccinationSystem.Repository;

import aniruddha.example.VaccinationSystem.Model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    Doctor findByEmailId(String oldEmail);
}
