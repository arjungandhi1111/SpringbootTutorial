package com.example.demo.controller;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.pojo.Doctor;
import com.example.demo.repo.DoctorRepo;
@RestController
public class FindDoctorController {
@Autowired
DoctorRepo doctorRepo;

@GetMapping("/doctors/{doctorId}")
ResponseEntity< Doctor >findDoctorById(@PathVariable int doctorId) 


{
    Optional<Doctor> optional= doctorRepo.findById(doctorId);
    if(optional.isPresent())
    {
        Doctor doctor=optional.get();
        return new ResponseEntity<Doctor>(doctor,HttpStatus.OK);
    }
    return new ResponseEntity<Doctor>(HttpStatus.NO_CONTENT);
    
    
}

}