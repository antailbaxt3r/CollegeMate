package com.antailbaxt3r.collegemate.ModalClasses;

public class ProfileModal {
    public String name;
    public String email;
    public String phone;
    public String year_of_study;
    public String enrollment_id;

    public ProfileModal(String name, String email, String phone, String year_of_study, String enrollment_id) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.year_of_study = year_of_study;
        this.enrollment_id = enrollment_id;
    }

    public ProfileModal(){

    }
}
