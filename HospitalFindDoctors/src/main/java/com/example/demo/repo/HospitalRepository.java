package com.example.demo.repo;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.example.demo.pojo.Hospital;
@Repository
public class HospitalRepository {
@Autowired
JdbcTemplate jdbctemplate;


public Hospital findHospitalById(int hospitalId) {
    
    Hospital hospital=null;
    
    String findHosById="Select * from hospital where hospital_Id=? ";
    
    try {
         hospital=jdbctemplate.queryForObject(findHosById, new RowMapper<Hospital>()
                 {
             @Override 
             public Hospital mapRow(ResultSet rs,int rowNum) throws SQLException
             {
                 Hospital hospital=new Hospital();
                 hospital.setHospitalregistrationId(rs.getInt(1));
                 hospital.setAddress(rs.getString(2));
                 hospital.setHospitalName(rs.getString(3));
                 return hospital;
             }
             },hospitalId);
            }
         catch(Exception e)
         {
            System.out.println(e.getMessage()); 
         }
         return hospital;
    }
    
}
