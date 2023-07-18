package aniruddha.example.VaccinationSystem.Service;

import aniruddha.example.VaccinationSystem.Exception.BothDoseNotTakenException;
import aniruddha.example.VaccinationSystem.Exception.GenerateYourCertificateFirstException;
import aniruddha.example.VaccinationSystem.Exception.PersonDoesNotExitsException;
import aniruddha.example.VaccinationSystem.Model.Certificate;
import aniruddha.example.VaccinationSystem.Model.Dose;
import aniruddha.example.VaccinationSystem.Model.Person;
import aniruddha.example.VaccinationSystem.Repository.CertificateRepository;
import aniruddha.example.VaccinationSystem.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Optional;
import java.util.UUID;
@Service
public class CertificateService {
    @Autowired
    CertificateRepository certificateRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    JavaMailSender javaMailSender;
    public String generateCertificate(int personId) {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        if (optionalPerson.isEmpty())throw new PersonDoesNotExitsException("Give Correct Person Id");
        Person person = optionalPerson.get();
        if (person.getDoseList().size() < 2)throw new BothDoseNotTakenException("Complete Your Both Dose To Get the Certificate");

        Certificate certificate = new Certificate();
        certificate.setCertificateNo(String.valueOf(UUID.randomUUID()));
        certificate.setPerson(person);
        certificate.setConfirmationMessage("Your Certificate Generate SuccessFully");

        Certificate saveCertificate = certificateRepository.save(certificate);
        person.setCertificate(saveCertificate);
        Person savePerson = personRepository.save(person);
        return "Certificate Generate Successfully";
    }

    public String getCertificate(int personId)  {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        if (optionalPerson.isEmpty())throw new PersonDoesNotExitsException("Enter Correct Id");
        Person person = optionalPerson.get();
        if (person.getCertificate() == null)throw new GenerateYourCertificateFirstException("Generate Your Certificate First");
        Certificate certificate = person.getCertificate();
        Dose dose = person.getDoseList().get(0);

        String text = "COVID-19 Vaccination Certificate\n" +
                "\n" +
                "Certificate Number: " + certificate.getCertificateNo() +"\n" +
                "Individual Information:\n" +
                "Full Name: " + person.getName() + "\n" +
                "Gender: "+ person.getGender() + "\n" +
                "Contact Information: " + person.getEmailId() + "\n" +
                "[MINISTRY OF HEALTH & FAMILY WELFARE]\n" +
                "Please note that this is a general sample format and may need to be adapted based on the specific requirements and guidelines of " +
                "your country or issuing authority.";

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("indiahealthdepartment408@gmail.com");
        simpleMailMessage.setTo(person.getEmailId());
//        System.out.println(savePerson.getEmailId());
        simpleMailMessage.setSubject("CERTIFICATE");
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);
        return "Check Your Registered Mail";
    }
}
