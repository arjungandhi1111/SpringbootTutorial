package com.example.demo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.example.demo.pojo.Doctor;
import com.example.demo.repo.DoctorRepo;
import jakarta.ws.rs.core.MediaType;
@SpringBootTest
@AutoConfigureMockMvc
class DoctorFindAllApplicationTests {
@Autowired
private MockMvc mockMvc;

@MockBean
private DoctorRepo doctorRepo;

@Test
void contextLoads() {
}

@Test
public void testFindAllDoctorsByHosId() throws Exception{
    Doctor doctor1=new Doctor(1,"Dave Smith","Cardio",101);
    Doctor doctor2=new Doctor(2,"Jenna New","Neuro",101);
    
    List<Doctor> doctors=Arrays.asList(doctor1,doctor2);
    
    when(doctorRepo.findByHospitalId(101)).thenReturn(doctors);
    
    mockMvc.perform(get("/doctors/101")
    .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$[0].doctorName",is("Dave Smith")))
    .andExpect(jsonPath("$[1].doctorName",is("Jenna New")));
    
}

}