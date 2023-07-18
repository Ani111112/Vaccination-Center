package aniruddha.example.VaccinationSystem.Service;

import aniruddha.example.VaccinationSystem.Model.Doctor;
import aniruddha.example.VaccinationSystem.Model.VaccinationCenter;
import aniruddha.example.VaccinationSystem.Repository.VaccinationCenterRepository;
import aniruddha.example.VaccinationSystem.RequestDTO.CenterDto;
import aniruddha.example.VaccinationSystem.ResponseDto.CenterResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

@Service
public class VaccinationCenterService {
    @Autowired
    VaccinationCenterRepository vaccinationCenterRepository;
    public CenterResponseDto addCenter(CenterDto centerDto) {
        //Request Dto -> Entity
        VaccinationCenter vaccinationCenter = new VaccinationCenter();
        vaccinationCenter.setCenterName(centerDto.getCenterName());
        vaccinationCenter.setCenterType(centerDto.getCenterType());
        vaccinationCenter.setAddress(centerDto.getAddress());

        //save entity to the database
        VaccinationCenter savedCenter = vaccinationCenterRepository.save(vaccinationCenter);
        System.out.println(savedCenter.getAddress() + " " + savedCenter.getCenterName());

        //Entity -> Response Dto
        CenterResponseDto centerResponseDto = new CenterResponseDto();
        centerResponseDto.setCenterName(savedCenter.getCenterName());
        centerResponseDto.setCenterType(savedCenter.getCenterType());
        centerResponseDto.setAddress(savedCenter.getAddress());
        return centerResponseDto;
    }

    public List<String> getAllDoctorOfACenterType(String centerType) {
        List<VaccinationCenter> vaccinationCenterList = vaccinationCenterRepository.findAll();
        List<String> doctorList = new ArrayList<>();
        for (VaccinationCenter center : vaccinationCenterList) {
            if (center.getCenterType().toString().equals(centerType)) {
                List<Doctor> doctors = center.getVaccinationCenters();
                for (Doctor doctor : doctors) {
                    doctorList.add(doctor.getName());
                }
            }
        }
        return doctorList;
    }

    public List<CenterResponseDto> getCenterWithHighestNumberOfDoctors() {
        List<VaccinationCenter> centerList = vaccinationCenterRepository.findAll();

        int max = 0;
        for (VaccinationCenter center : centerList) {
            max = Math.max(max, center.getVaccinationCenters().size());
        }

        List<CenterResponseDto> centerResponseList = new ArrayList<>();
        for (VaccinationCenter center : centerList) {
            if (center.getVaccinationCenters().size() == max) {
                CenterResponseDto centerResponseDto = new CenterResponseDto();
                centerResponseDto.setCenterName(center.getCenterName());
                centerResponseDto.setCenterType(center.getCenterType());
                centerResponseDto.setAddress(center.getAddress());
                centerResponseList.add(centerResponseDto);
            }
        }
        return centerResponseList;
    }

    public List<CenterResponseDto> highestNumberOfDoctorOfACenterType(String centerType) {
        List<VaccinationCenter> vaccinationCenterList = vaccinationCenterRepository.findAll();

        int max = 0;
        for (VaccinationCenter center : vaccinationCenterList) {
            if (center.getCenterType().toString().equals(centerType))
            max = Math.max(max, center.getVaccinationCenters().size());
        }

        List<CenterResponseDto> centerList = new ArrayList<>();
        for (VaccinationCenter center : vaccinationCenterList) {
            if (center.getCenterType().toString().equals(centerType) && center.getVaccinationCenters().size() == max) {
                CenterResponseDto centerResponseDto = new CenterResponseDto();
                centerResponseDto.setCenterName(center.getCenterName());
                centerResponseDto.setCenterType(center.getCenterType());
                centerResponseDto.setAddress(center.getAddress());
                centerList.add(centerResponseDto);
            }
        }

        return centerList;
    }
}
