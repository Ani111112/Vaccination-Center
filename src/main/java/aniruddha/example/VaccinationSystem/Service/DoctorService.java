package aniruddha.example.VaccinationSystem.Service;

import aniruddha.example.VaccinationSystem.Exception.CenterDoestNotExitsException;
import aniruddha.example.VaccinationSystem.Exception.DoctorDoesNotExitsException;
import aniruddha.example.VaccinationSystem.Model.Doctor;
import aniruddha.example.VaccinationSystem.Model.VaccinationCenter;
import aniruddha.example.VaccinationSystem.Repository.DoctorRepository;
import aniruddha.example.VaccinationSystem.Repository.VaccinationCenterRepository;
import aniruddha.example.VaccinationSystem.RequestDTO.DoctorRequestDto;
import aniruddha.example.VaccinationSystem.ResponseDto.CenterResponseDto;
import aniruddha.example.VaccinationSystem.ResponseDto.DoctorResponseDTO;
import aniruddha.example.VaccinationSystem.ResponseDto.DoctorUpdateAgeAndEmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    VaccinationCenterRepository vaccinationCenterRepository;
    public DoctorResponseDTO addDoctor(DoctorRequestDto doctorRequestDto) {
        //first check that this center id is valid or not
        Optional<VaccinationCenter> centerOptional = vaccinationCenterRepository.findById(doctorRequestDto.getCenterId());

        if (centerOptional.isEmpty()) {
            throw new CenterDoestNotExitsException("Sorry! Put Correct Center Id");
        }

        VaccinationCenter center = centerOptional.get();

        //make Entity from request dto
        Doctor doctor = new Doctor();
        doctor.setName(doctorRequestDto.getName());
        doctor.setGender(doctorRequestDto.getGender());
        doctor.setEmailId(doctorRequestDto.getEmailId());
        doctor.setCenter(center);
        doctor.setAge(doctorRequestDto.getAge());

        center.getVaccinationCenters().add(doctor);

        VaccinationCenter savedCenter = vaccinationCenterRepository.save(center); //save both center and doctor

        //make Entity to response Dto
        CenterResponseDto centerResponseDto = new CenterResponseDto();
        centerResponseDto.setCenterName(savedCenter.getCenterName());
        centerResponseDto.setCenterType(savedCenter.getCenterType());
        centerResponseDto.setAddress(savedCenter.getAddress());

        DoctorResponseDTO doctorResponseDTO = new DoctorResponseDTO();
        doctorResponseDTO.setName(doctor.getName());
        doctorResponseDTO.setMessage("Congrats! You Have Been Assign To a Center");
        doctorResponseDTO.setCenterResponseDto(centerResponseDto);
        return doctorResponseDTO;
    }

    public List<String> highestAppointments() {
        List<Doctor> doctorList = doctorRepository.findAll();

        int maxAppointment = 0;
        for (Doctor doctor : doctorList) {
            maxAppointment = Math.max(maxAppointment, doctor.getAppointmentList().size());
        }

        List<String> doctorResponses = new ArrayList<>();
        for (Doctor doctor : doctorList) {
            if (doctor.getAppointmentList().size() == maxAppointment) {
                doctorResponses.add(doctor.getName());
            }
        }
        Collections.sort(doctorResponses);
        return doctorResponses;
    }

    public List<DoctorResponseDTO> getAllDoctorOfACenter(String centerName) {
        VaccinationCenter vaccinationCenter = vaccinationCenterRepository.findByCenterName(centerName);
        if (vaccinationCenter == null) throw new CenterDoestNotExitsException("Sorry! Enter Correct Center Name");
        List<Doctor> doctorList = vaccinationCenter.getVaccinationCenters();
        List<DoctorResponseDTO> doctorResponse = new ArrayList<>();
        for (Doctor doctor : doctorList) {
            CenterResponseDto centerResponseDto = new CenterResponseDto();
            centerResponseDto.setCenterName(vaccinationCenter.getCenterName());
            centerResponseDto.setCenterType(vaccinationCenter.getCenterType());
            centerResponseDto.setAddress(vaccinationCenter.getAddress());

            DoctorResponseDTO doctorResponseDTO = new DoctorResponseDTO();
            doctorResponseDTO.setName(doctor.getName());
            doctorResponseDTO.setCenterResponseDto(centerResponseDto);
            doctorResponseDTO.setMessage("Thank You! Stay Healthy");
            doctorResponse.add(doctorResponseDTO);
        }
        return doctorResponse;
    }

    public DoctorUpdateAgeAndEmailResponse updateAgeAndEmail(String oldEmail, String newEmail, int age) {
        Doctor doctor = doctorRepository.findByEmailId(oldEmail);
        if (doctor == null)throw new DoctorDoesNotExitsException("Doctor Does Not Exits Please Check Your Given Mail Id");
        VaccinationCenter center = doctor.getCenter();
        List<Doctor> doctorList = center.getVaccinationCenters();
        for(int i = 0; i < doctorList.size(); i++) {
            if (doctorList.get(i).getEmailId().equals(oldEmail)) {
                if (newEmail.length() > 0 && age > 0) {
                   Doctor doctor1 = doctorList.get(i);
                   doctor1.setEmailId(newEmail);
                   doctor1.setAge(age);
                   doctorList.set(i, doctor1);
                   doctor.setEmailId(newEmail);
                   doctor.setAge(age);
                   break;
                }else if (newEmail.length() == 0 && age > 0) {
                    Doctor doctor1 = doctorList.get(i);
                    doctor1.setAge(age);
                    doctorList.set(i, doctor1);
                    doctor.setEmailId(newEmail);
                    break;
                }else if (newEmail.length() > 0 && age == 0) {
                    Doctor doctor1 = doctorList.get(i);
                    doctor1.setEmailId(newEmail);
                    doctorList.set(i, doctor1);
                    doctor.setAge(age);
                    break;
                }
            }
        }
        center.setVaccinationCenters(doctorList);
        VaccinationCenter savedCenter = vaccinationCenterRepository.save(center);
        Doctor savedDoctor = doctorRepository.save(doctor);
        DoctorUpdateAgeAndEmailResponse doctorUpdateAgeAndEmailResponse = new DoctorUpdateAgeAndEmailResponse();
        doctorUpdateAgeAndEmailResponse.setName(savedDoctor.getName());
        doctorUpdateAgeAndEmailResponse.setAge(savedDoctor.getAge());
        doctorUpdateAgeAndEmailResponse.setEmail(savedDoctor.getEmailId());
        doctorUpdateAgeAndEmailResponse.setEmail("Your Email and Age Updated Successfully");
        return doctorUpdateAgeAndEmailResponse;
    }
}
