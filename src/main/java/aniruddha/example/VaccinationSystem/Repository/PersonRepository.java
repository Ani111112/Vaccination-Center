package aniruddha.example.VaccinationSystem.Repository;

import aniruddha.example.VaccinationSystem.Model.Dose;
import aniruddha.example.VaccinationSystem.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findByEmailId(String oldEmail);

    @Query(value = "select*from person where age > :age", nativeQuery = true)
    List<Person> findByAge(int age);
}
