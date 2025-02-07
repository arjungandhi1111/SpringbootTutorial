package com.example.demo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import com.example.demo.pojo.Doctor;
import com.example.demo.pojo.Hospital;
import com.example.demo.repo.HospitalRepository;
import jakarta.ws.rs.core.MediaType;
@SpringBootTest
@AutoConfigureMockMvc
class HospitalFindDoctorsApplicationTests {
@Autowired
private MockMvc mockMvc;

@MockBean
private HospitalRepository hospitalRepository;

@MockBean
private RestTemplate restTemplate;

@MockBean
private TestRestTemplate testRestTemplate;

@Test
public void testFindAllDoctorsInHospital() throws Exception {

    Hospital hospital = new Hospital(101, "City Hospital", "123 Street", new ArrayList<>());
    when(hospitalRepository.findHospitalById(101)).thenReturn(hospital);


    Doctor[] doctors = {
        new Doctor(1, "Dave Smith", "Cardio", 101),
        new Doctor(2, "Jenna New", "Neuro", 101)
    };
    when(testRestTemplate.getForEntity(
        eq("http://doctor-find-all-service/doctors/{hospital_id}"),
        eq(Doctor[].class),
        eq(101)
    )).thenReturn(ResponseEntity.ok(doctors));


    mockMvc.perform(get("/hospitals/101")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.hospitalregistrationId", is(101))) 
        .andExpect(jsonPath("$.hospitalName", is("City Hospital"))) 
        .andExpect(jsonPath("$.doctors[0].doctorName", is("Dave Smith"))) 
        .andExpect(jsonPath("$.doctors[1].doctorName", is("Jenna New"))); 
}


@Test
void contextLoads() {
}

}