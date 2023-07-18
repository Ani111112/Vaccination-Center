package aniruddha.example.VaccinationSystem.Model;

import aniruddha.example.VaccinationSystem.Enum.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;

    @Column(unique = true)
    String emailId;

    @Enumerated(EnumType.STRING)
    Gender gender;

    int age;

    int mobileNo;

    boolean dose1Taken;
    boolean dose2Taken;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    List<Dose> doseList = new ArrayList<>();

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    Certificate certificate;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    List<Appointment> appointmentList = new ArrayList<>();
}
