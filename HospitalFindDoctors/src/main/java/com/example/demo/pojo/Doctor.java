package com.example.demo.pojo;
public class Doctor {
private int doctorId;
private String doctorName;
private String spec;
@Override
public String toString() {
    return "Doctor [doctorId=" + doctorId + ", doctorName=" + doctorName + ", spec=" + spec + "]";
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
public String getSpec() {
    return spec;
}
public void setSpec(String spec) {
    this.spec = spec;
}
public Doctor(int i, String string, String string2, int j) {
    super();
    // TODO Auto-generated constructor stub
}
public Doctor(int doctorId, String doctorName, String spec) {
    super();
    this.doctorId = doctorId;
    this.doctorName = doctorName;
    this.spec = spec;
}

}