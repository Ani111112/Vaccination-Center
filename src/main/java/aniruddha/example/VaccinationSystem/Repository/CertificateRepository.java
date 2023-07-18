package aniruddha.example.VaccinationSystem.Repository;

import aniruddha.example.VaccinationSystem.Model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Integer> {
}
