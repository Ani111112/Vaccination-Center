package aniruddha.example.VaccinationSystem.Repository;

import aniruddha.example.VaccinationSystem.Model.VaccinationCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccinationCenterRepository extends JpaRepository<VaccinationCenter, Integer> {
    VaccinationCenter findByCenterName(String centerName);
}
