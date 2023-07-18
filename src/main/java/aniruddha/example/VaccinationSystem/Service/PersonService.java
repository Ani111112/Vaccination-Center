package aniruddha.example.VaccinationSystem.Service;

import aniruddha.example.VaccinationSystem.Exception.PersonAlreadyHaveException;
import aniruddha.example.VaccinationSystem.Exception.PersonEmailDoesNotExitsException;
import aniruddha.example.VaccinationSystem.Model.Dose;
import aniruddha.example.VaccinationSystem.Model.Person;
import aniruddha.example.VaccinationSystem.Repository.PersonRepository;
import aniruddha.example.VaccinationSystem.RequestDTO.PersonDto;
import aniruddha.example.VaccinationSystem.ResponseDto.GetFemalesByDoseOne;
import aniruddha.example.VaccinationSystem.ResponseDto.PersonByAgeResponse;
import aniruddha.example.VaccinationSystem.ResponseDto.PersonResponse;
import aniruddha.example.VaccinationSystem.ResponseDto.PersonWithBothDoseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;
    public PersonResponse addPerson(PersonDto personReq) {

        //request dto -> entity
        Person person = new Person();
        person.setName(personReq.getName());
        person.setAge(personReq.getAge());
        person.setGender(personReq.getGender());
        person.setEmailId(personReq.getEmailId());
        person.setMobileNo(personReq.getMobileNo());

        if (personRepository.existsById(person.getId())) {
            throw new PersonAlreadyHaveException("Person Already Have");
        }
        Person savedPerson = personRepository.save(person); // It will give the save Person
        //entity -> response DTO
        PersonResponse personResponse = new PersonResponse();
        personResponse.setName(savedPerson.getName());
        personResponse.setMessage(savedPerson.getName() + " Awesome! You Are Added Successfully");
        return personResponse;
    }

    public PersonResponse updateEmail(String oldEmail, String newEmail) {
        Person person = personRepository.findByEmailId(oldEmail);
        if (person == null) throw new PersonEmailDoesNotExitsException("Sorry! Enter Correct Email Id");
        person.setEmailId(newEmail);
        Person savePerson = personRepository.save(person);

        //entity -> response dto
        PersonResponse personUpdateEmailResponse = new PersonResponse();
        personUpdateEmailResponse.setName(savePerson.getName());
        personUpdateEmailResponse.setMessage("Congrats! Your new EmailId is " + savePerson.getEmailId());
        return personUpdateEmailResponse;
    }

    public List<PersonByAgeResponse> getAllMalesWhoAreGraterThenACertainAge(int age) {
        List<Person> personList = personRepository.findByAge(age);
        List<PersonByAgeResponse> responseList = new ArrayList<>();
        for (Person person : personList) {
            if (person.getGender().toString().equals("MALE")) {
                PersonByAgeResponse person1 = new PersonByAgeResponse();
                person1.setName(person.getName());
                person1.setAge(person.getAge());
                responseList.add(person1);
            }
        }
        return responseList;
    }

    public List<GetFemalesByDoseOne> getAllFemalesWhoDoneWithOnlyDoseOne() {
        List<Person> personList = personRepository.findAll();
        List<GetFemalesByDoseOne> femalesByDoseOneList = new ArrayList<>();
        for (Person person : personList) {
            List<Dose> doseList = person.getDoseList();
            if (person.getGender().toString().equals("FEMALE") && doseList.size() == 1) {
                GetFemalesByDoseOne femalesByDoseOne = new GetFemalesByDoseOne();
                femalesByDoseOne.setName(person.getName());
                femalesByDoseOne.setAge(person.getAge());
                femalesByDoseOne.setDoseName(doseList.get(0).getDoseType().toString());
                femalesByDoseOneList.add(femalesByDoseOne);
            }
        }
        return femalesByDoseOneList;
    }

    public List<PersonWithBothDoseResponse> getALlPeopleWhoHaveDoneWithBothDose() {
        List<Person> personList = personRepository.findAll();
        List<PersonWithBothDoseResponse> personResponseList = new ArrayList<>();
        for (Person person : personList) {
            List<Dose> doseList = person.getDoseList();
            if (doseList.size() == 2) {
                PersonWithBothDoseResponse personResponse = new PersonWithBothDoseResponse();
                personResponse.setName(person.getName());
                personResponse.setDoseName(doseList.get(0).getDoseType().toString());
                personResponseList.add(personResponse);
            }
        }
        return personResponseList;
    }

    public List<PersonResponse> getAlPeopleWhoDoNotTakeSingleDose() {
        List<Person> personList = personRepository.findAll();
        List<PersonResponse> personResponses = new ArrayList<>();
        for (Person person : personList) {
            List<Dose> doseList = person.getDoseList();
            if (doseList.size() == 0) {
                PersonResponse personResponse = new PersonResponse();
                personResponse.setName(person.getName());
                personResponse.setMessage("Sorry! Not Take Any Dose");
                personResponses.add(personResponse);
            }
        }
        return personResponses;
    }

    public List<GetFemalesByDoseOne> getAllFemaleWhoIsGraterThenAParticularAgeAndTakeOnlyOneDose(int age) {
        List<Person> personList = personRepository.findByAge(age);
        List<GetFemalesByDoseOne> females = new ArrayList<>();
        for (Person person : personList) {
            List<Dose> doseList = person.getDoseList();
            if (person.getGender().toString().equals("FEMALE") && doseList.size() == 0) {
                GetFemalesByDoseOne getFemalesByDoseOne = new GetFemalesByDoseOne();
                getFemalesByDoseOne.setDoseName(null);
                getFemalesByDoseOne.setName(person.getName());
                getFemalesByDoseOne.setAge(person.getAge());
                females.add(getFemalesByDoseOne);
            }
        }
        return females;
    }

    public List<PersonWithBothDoseResponse> getAllMaleWhoIsGraterThenAParticularAgeAndWhoTakenBothDose(int age) {
        List<Person> personList = personRepository.findByAge(age);
        List<PersonWithBothDoseResponse> personWithBothDoseResponseList = new ArrayList<>();
        for (Person person : personList) {
            List<Dose> doseList = person.getDoseList();
            if (person.getGender().toString().equals("MALE") && doseList.size() == 2) {
                PersonWithBothDoseResponse person1 = new PersonWithBothDoseResponse();
                person1.setName(person.getName());
                person1.setDoseName(doseList.get(0).getDoseType().toString());
                personWithBothDoseResponseList.add(person1);
            }
        }
        return personWithBothDoseResponseList;
    }
}
