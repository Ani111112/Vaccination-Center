package aniruddha.example.VaccinationSystem.Service;

import aniruddha.example.VaccinationSystem.Exception.DoctorDoesNotExitsException;
import aniruddha.example.VaccinationSystem.Exception.PersonDoesNotExitsException;
import aniruddha.example.VaccinationSystem.Model.Appointment;
import aniruddha.example.VaccinationSystem.Model.Doctor;
import aniruddha.example.VaccinationSystem.Model.Person;
import aniruddha.example.VaccinationSystem.Model.VaccinationCenter;
import aniruddha.example.VaccinationSystem.Repository.AppointmentRepository;
import aniruddha.example.VaccinationSystem.Repository.DoctorRepository;
import aniruddha.example.VaccinationSystem.Repository.PersonRepository;
import aniruddha.example.VaccinationSystem.RequestDTO.AddAppointmentRequest;
import aniruddha.example.VaccinationSystem.ResponseDto.AddAppointmentResponse;
import aniruddha.example.VaccinationSystem.ResponseDto.CenterResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    JavaMailSender javaMailSender;
    public AddAppointmentResponse addAppointment(AddAppointmentRequest addAppointmentRequest) {
        //check person id is valid or not
        Optional<Person> optionalPerson = personRepository.findById(addAppointmentRequest.getPersonId());

        //check doctor id is valid or not
        Optional<Doctor> optionalDoctor = doctorRepository.findById(addAppointmentRequest.getDoctorId());

        //Checking the person id is correct or not
        if (optionalPerson.isEmpty())throw new PersonDoesNotExitsException("Enter Correct Person Id");
        //Checking the doctor id is correct or not
        if (optionalDoctor.isEmpty())throw new DoctorDoesNotExitsException("Enter Correct Doctor Id");

        Person person = optionalPerson.get();
        Doctor doctor = optionalDoctor.get();

        //make appointment object
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(String.valueOf(UUID.randomUUID()));
        appointment.setPerson(person);
        appointment.setDoctor(doctor);

        Appointment saveAppointment = appointmentRepository.save(appointment);

        person.getAppointmentList().add(saveAppointment);
        doctor.getAppointmentList().add(saveAppointment);

        Person savePerson = personRepository.save(person);
        Doctor saveDoctor = doctorRepository.save(doctor);


        String text = "\n" +
                "Subject: COVID-19 Vaccination Appointment Confirmation\n" +
                "\n" +
                "Dear "+  savePerson.getName() + ",\n" +
                "\n" +
                "We are pleased to inform you that your COVID-19 vaccination appointment has been scheduled successfully. " +
                "The vaccine dose details and center information are provided below:\n" +
                "\n" +
                "Appointment Details:\n" +
                "\n" +
                "Date & Time: " + saveAppointment.getAppointmentDate() +"\n" +
                "Vaccination Center Details:\n" +
                "\n" +
                "Name: " + doctor.getCenter().getCenterName() + "\n" +
                "Address: " + doctor.getCenter().getAddress() + "\n" +
                "Please remember to bring the following documents to your appointment:\n" +
                "\n" +
                "Government-issued photo identification (e.g., passport, driver's license)\n" +
                "Proof of eligibility (if applicable)\n" +
                "Any medical records or prescriptions related to your vaccination (if applicable)\n" +
                "It is important to arrive at the vaccination center a few minutes before your scheduled appointment time. This will allow sufficient time for registration and ensure a smooth vaccination process. Remember to wear a face mask and maintain social distancing guidelines while at the center.\n" +
                "\n" +
                "If, for any reason, you are unable to attend the appointment, we kindly request that you inform us as soon as possible so that we can reschedule it for another suitable date.\n" +
                "\n" +
                "We understand the significance of this vaccination campaign in combating the COVID-19 pandemic, and we greatly appreciate your participation. By getting vaccinated, you are taking an important step in safeguarding your health and the well-being of your community.\n" +
                "\n" +
                "Should you have any further questions or concerns, please do not hesitate to contact us at [Vaccination Center Phone Number] or via email at [Vaccination Center Email Address]. We are here to assist you.\n" +
                "\n" +
                "Thank you for your cooperation, and we look forward to seeing you at the vaccination center.\n" +
                "\n" +
                "Stay safe and healthy!\n";
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("indiahealthdepartment408@gmail.com");
        simpleMailMessage.setTo(savePerson.getEmailId());
        System.out.println(savePerson.getEmailId());
        simpleMailMessage.setSubject("Congrats!! Appointment Done!!");
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);

        //make entity to dto
        VaccinationCenter center = saveDoctor.getCenter();
        CenterResponseDto centerResponseDto = new CenterResponseDto();
        centerResponseDto.setCenterName(center.getCenterName());
        centerResponseDto.setCenterType(center.getCenterType());
        centerResponseDto.setAddress(center.getAddress());

        AddAppointmentResponse appointmentResponse = new AddAppointmentResponse();
        appointmentResponse.setPersonName(savePerson.getName());
        appointmentResponse.setDoctorName(saveDoctor.getName());
        appointmentResponse.setAppointmentId(saveAppointment.getAppointmentId());
        appointmentResponse.setAppointmentDate(saveAppointment.getAppointmentDate());
        appointmentResponse.setCenterResponseDto(centerResponseDto);

        return appointmentResponse;
    }


}
