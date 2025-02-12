package com.example.demo.controller;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.pojo.Doctor;
import com.example.demo.repo.DoctorRepo;
@RestController
public class DeleteDoctorController {
@Autowired
DoctorRepo doctorRepo;

@DeleteMapping("/doctors/{doctorId}")
public ResponseEntity<Void> deleteDoctor(@PathVariable int doctorId) {
    Optional<Doctor> doctors;
     
     doctors=doctorRepo.findById(doctorId);
     
    if (doctors.isPresent()) {
          doctorRepo.deleteById(doctorId);
        return ResponseEntity.noContent().build();
    } else {
        return ResponseEntity.notFound().build();
    }
}

}