package aniruddha.example.VaccinationSystem.Service;

import aniruddha.example.VaccinationSystem.Enum.DoseType;
import aniruddha.example.VaccinationSystem.Exception.DoseOneAlreadyTakenException;
import aniruddha.example.VaccinationSystem.Exception.PersonDoesNotExitsException;
import aniruddha.example.VaccinationSystem.Model.Dose;
import aniruddha.example.VaccinationSystem.Model.Person;
import aniruddha.example.VaccinationSystem.Repository.PersonRepository;
import aniruddha.example.VaccinationSystem.ResponseDto.DoseOneResponseDTO;
import aniruddha.example.VaccinationSystem.ResponseDto.DoseTwoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DoseService {
    @Autowired
    PersonRepository personRepository;
    public DoseOneResponseDTO getDose1(int personId, DoseType doseType) {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        if (!optionalPerson.isPresent()) {
            throw new PersonDoesNotExitsException("Person Id Does not Exits");
        }

        Person person = optionalPerson.get();
        if (person.isDose1Taken())  {
            throw new DoseOneAlreadyTakenException("Dose One Already Taken");
        }

        Dose dose = new Dose();
        dose.setDoseId(String.valueOf(UUID.randomUUID()));
        dose.setDoseType(doseType);
        dose.setPerson(person);

        person.setDose1Taken(true);
        person.getDoseList().add(dose);
        Person person1 = personRepository.save(person);

        //entity -> response DTO
        DoseOneResponseDTO responseDTO = new DoseOneResponseDTO();
        responseDTO.setName(person1.getName());
        responseDTO.setMessage(person1.getName() + " Congratulations! Your First Dose Is Done");
        return responseDTO;
    }

    public DoseTwoResponseDTO getDose2(int personId) {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        if (optionalPerson.isEmpty()) {
            throw new PersonDoesNotExitsException("Enter Correct Details");
        }

        Person person = optionalPerson.get();
        List<Dose> doseList = person.getDoseList();
        Dose dose = doseList.get(0);
        Dose secDose = new Dose();
        secDose.setDoseId(String.valueOf(UUID.randomUUID()));
        secDose.setDoseType(dose.getDoseType());
        secDose.setPerson(person);

        person.setDose2Taken(true);
        person.getDoseList().add(secDose);
        Person person1 = personRepository.save(person);

        //entity -> DTO
        DoseTwoResponseDTO doseTwoResponseDTO = new DoseTwoResponseDTO();
        doseTwoResponseDTO.setName(person1.getName());
        doseTwoResponseDTO.setMessage(person1.getName() + " You Have Taken Your Both Dose, Please Download Your Certificate");

        return doseTwoResponseDTO;
    }
}
