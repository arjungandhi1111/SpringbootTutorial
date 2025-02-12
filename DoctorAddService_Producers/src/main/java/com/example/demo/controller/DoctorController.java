package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dao.DoctorDAO;
import com.example.demo.exception.DoctorAlreadyExistsException;
import com.example.demo.pojo.Doctor;
@RestController
public class DoctorController {
@Autowired
DoctorDAO doctorDAO;

@Autowired
StreamBridge bridge;

@PostMapping(path="/doctors",consumes= { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE })
public  ResponseEntity<Doctor> createNewDoctor(@RequestBody Doctor doctor) throws DoctorAlreadyExistsException
{
    int added=0;
    
    added=doctorDAO.addDoctor(doctor);
    
    if(added==1
            ) {
        
        boolean isPub=bridge.send("doctorSupplier-out-0", MessageBuilder.withPayload(doctor).build());
        return new ResponseEntity<Doctor>(doctor, HttpStatusCode.valueOf(201));
        
    }
    
    return new ResponseEntity<Doctor>(HttpStatusCode.valueOf(204));
            
    /*
     * if(added!=0) { return doctor; } else { return null; }
     */
}

}