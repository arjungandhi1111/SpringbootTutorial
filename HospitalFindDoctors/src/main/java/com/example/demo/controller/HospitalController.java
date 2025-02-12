package com.example.demo.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.config.AppConfig;
import com.example.demo.feign.Hospital_Doctor_Feign;
import com.example.demo.pojo.Doctor;
import com.example.demo.pojo.Hospital;
import com.example.demo.repo.HospitalRepository;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
@RestController
public class HospitalController {
@Autowired
HospitalRepository hospitalRepository;
@Autowired
Hospital_Doctor_Feign hospitalDoctorFeign;
private final RestTemplate restTemplate;

public HospitalController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
}

@GetMapping("/hospitals/{hospitalId}")
ResponseEntity<Hospital>findalldoctorsinhospital(@PathVariable int hospitalId) {
    
    List<Doctor> doctors=new ArrayList<>();
    
    Hospital hospital=    hospitalRepository.findHospitalById(hospitalId);
    //hospital.setDoctors(doctors);

if (hospital!=null) {
    
    //RestTemplate restTemplate= new RestTemplate();
	
	String url = "http://doctor-find-all-service/doctors/{hospitalId}";
    
    ResponseEntity<Doctor[]> entity=restTemplate.getForEntity(url, Doctor[].class,hospitalId);
    hospital.setDoctors(Arrays.asList(entity.getBody()));
    return new ResponseEntity<Hospital>(hospital,HttpStatus.OK);
}
        
else
{
    return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
}

}

@GetMapping("/hospitals-feign/{hospitalId}")
@CircuitBreaker(name="circuit-breaker-for-doctor",fallbackMethod="myMethod")
ResponseEntity<Hospital>findAllDoctorsInhospitalFeign(@PathVariable int hospitalId) {
    List<Doctor> doctors=new ArrayList<>();
    
    Hospital hospital=    hospitalRepository.findHospitalById(hospitalId);

    ResponseEntity<List<Doctor>> entity = hospitalDoctorFeign.findAllDoctorsByHospitalId(hospitalId);
    
    if (entity.getBody() != null) {
        hospital.setDoctors(entity.getBody());
    } else {
        hospital.setDoctors(new ArrayList<>()); 
    }

    return new ResponseEntity<Hospital>(hospital, HttpStatus.OK);

}
ResponseEntity<Hospital> myMethod( int hospitalId, Throwable e)
{
    
    Hospital hospital=new Hospital();
    hospital.setAddress("address");
    hospital.setHospitalName("name");
    hospital.setHospitalregistrationId(1);
    return new ResponseEntity<Hospital>(hospital, HttpStatus.OK);
    
}


@GetMapping("/hospitals-feign-retry/{hospitalId}")
@Retry(name="retry-for-doctor")//,fallbackMethod="myMethod")
ResponseEntity<Hospital>findAllDoctorsInhospitalFeignRetry(@PathVariable int hospitalId) {
    List<Doctor> doctors=new ArrayList<>();
    
    Hospital hospital=    hospitalRepository.findHospitalById(hospitalId);

    ResponseEntity<List<Doctor>> entity = hospitalDoctorFeign.findAllDoctorsByHospitalId(hospitalId);
    
    if (entity.getBody() != null) {
        hospital.setDoctors(entity.getBody());
    } else {
        hospital.setDoctors(new ArrayList<>()); 
    }

    return new ResponseEntity<Hospital>(hospital, HttpStatus.OK);

}

@GetMapping("/hospitals-feign-rate-limiter/{hospitalId}")
@RateLimiter(name="rate-limiter-for-doctor",fallbackMethod="myMethodRateLimiter")
ResponseEntity<Hospital>findAllDoctorsInhospitalFeignRateLimiter(@PathVariable int hospitalId) {
    List<Doctor> doctors=new ArrayList<>();
    
    Hospital hospital=    hospitalRepository.findHospitalById(hospitalId);

    ResponseEntity<List<Doctor>> entity = hospitalDoctorFeign.findAllDoctorsByHospitalId(hospitalId);
    
    if (entity.getBody() != null) {
        hospital.setDoctors(entity.getBody());
    } else {
        hospital.setDoctors(new ArrayList<>()); 
    }

    return new ResponseEntity<Hospital>(hospital, HttpStatus.OK);

}

ResponseEntity<Hospital> myMethodRateLimiter( int hospitalId, Throwable e)
{
  
    return new ResponseEntity<Hospital>( HttpStatus.TOO_MANY_REQUESTS);
    
}

@GetMapping("/hospitals-feign-bulkhead/{hospitalId}")
@Bulkhead(name="bulkhead-for-doctor",fallbackMethod="bulkHead")
ResponseEntity<Hospital>findAllDoctorsInhospitalFeignBulkhead(@PathVariable int hospitalId) {

    Hospital hospital=    hospitalRepository.findHospitalById(hospitalId);

    ResponseEntity<List<Doctor>> entity = hospitalDoctorFeign.findAllDoctorsByHospitalId(hospitalId);
    
    if (entity.getBody() != null) {
        hospital.setDoctors(entity.getBody());
    } else {
        hospital.setDoctors(new ArrayList<>()); 
    }

    return new ResponseEntity<Hospital>(hospital, HttpStatus.OK);

}

ResponseEntity<Hospital> bulkHead( int hospitalId, Throwable e)
{
    

    return new ResponseEntity<Hospital>( HttpStatus.TOO_MANY_REQUESTS);
    
}

@GetMapping("/hospitals-cb/{hospitalId}")
@CircuitBreaker(name = "doctorServiceCircuitBreaker", fallbackMethod = "fallbackForFindAllDoctorsInHospital")
ResponseEntity<Hospital>findalldoctorsinhospitalcb(@PathVariable int hospitalId) {
    
    List<Doctor> doctors=new ArrayList<>();
    
    Hospital hospital=    hospitalRepository.findHospitalById(hospitalId);
    //hospital.setDoctors(doctors);

if (hospital!=null) {
    
    //RestTemplate restTemplate= new RestTemplate();
	
	String url = "http://doctor-find-all-service/doctors/{hospitalId}";
    
    ResponseEntity<Doctor[]> entity=restTemplate.getForEntity(url, Doctor[].class,hospitalId);
    hospital.setDoctors(Arrays.asList(entity.getBody()));
    return new ResponseEntity<Hospital>(hospital,HttpStatus.OK);
}
        
else
{
    return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
}

}

public ResponseEntity<Hospital> fallbackForFindAllDoctorsInHospital(int hospitalId, Throwable throwable) {
    Hospital hospital=new Hospital();
    hospital.setAddress("addresscb");
    hospital.setHospitalName("namecb");
    hospital.setHospitalregistrationId(1);
    return new ResponseEntity<Hospital>(hospital, HttpStatus.OK);
}

}
