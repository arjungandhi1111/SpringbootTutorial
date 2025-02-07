package com.example.demo.controller;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.pojo.Doctor;
import com.example.demo.repo.DoctorRepo;
@RestController
public class UpdateDoctorController {
@Autowired
DoctorRepo doctorRepo;
  
@PutMapping("/doctors/{doctorId}")
public ResponseEntity<Doctor> updateDoctor(@PathVariable int doctorId, @RequestBody Doctor updatedDoctor) {
    Optional<Doctor> existingDoctor = doctorRepo.findById(doctorId);

    if (existingDoctor.isPresent()) {
        Doctor doctor = existingDoctor.get();
        if (updatedDoctor.getDoctorName()!= null) {
            doctor.setDoctorName(updatedDoctor.getDoctorName());
        }
        if (updatedDoctor.getSpecialization()!= null) {
            doctor.setSpecialization(updatedDoctor.getSpecialization());
        }
        if (updatedDoctor.getHospitalId() != 0) {
            doctor.setHospitalId(updatedDoctor.getHospitalId());
        }
        
        doctorRepo.save(doctor);

        return ResponseEntity.ok(doctor);
    } else {
        return ResponseEntity.notFound().build();
    }
}

}