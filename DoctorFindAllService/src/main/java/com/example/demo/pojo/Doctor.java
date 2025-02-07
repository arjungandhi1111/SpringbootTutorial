package com.example.demo.pojo;
import jakarta.persistence.*;
@Entity
@Table(name = "doctor")
public class Doctor {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY) 
@Column(name = "doctor_id") 
private int doctorId;

@Column(name = "doctor_name", nullable = false, length = 100) 
private String doctorName;

@Column(name = "specialization", nullable = false, length = 50) 
private String specialization;

@Column(name = "hospital_id", nullable = false) 
private int hospitalId;


public Doctor() {}


public Doctor(int doctorId, String doctorName, String specialization, int hospitalId) {
    this.doctorId = doctorId;
    this.doctorName = doctorName;
    this.specialization = specialization;
    this.hospitalId = hospitalId;
}


public int getDoctorId() {
    return doctorId;
}

public void setDoctorId(int doctorId) {
    this.doctorId = doctorId;
}

public String getDoctorName() {
    return doctorName;
}

public void setDoctorName(String doctorName) {
    this.doctorName = doctorName;
}

public String getSpecialization() {
    return specialization;
}

public void setSpecialization(String specialization) {
    this.specialization = specialization;
}

public int getHospitalId() {
    return hospitalId;
}

public void setHospitalId(int hospitalId) {
    this.hospitalId = hospitalId;
}

@Override
public String toString() {
    return "Doctor [doctorId=" + doctorId + ", doctorName=" + doctorName + 
           ", specialization=" + specialization + ", hospitalId=" + hospitalId + "]";
}

}