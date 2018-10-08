package com.example.shivamdhammi.drag;

public class SSOInfo {

    private String SSOName,ISOnumber,Email,Address,Contact;

    SSOInfo(){

    }

    public SSOInfo(String SSOName, String ISOnumber, String email, String address, String contact) {
        this.SSOName = SSOName;
        this.ISOnumber = ISOnumber;
        Email = email;
        Address = address;
        Contact = contact;
    }

    public String getSSOName() {
        return SSOName;
    }

    public String getISOnumber() {
        return ISOnumber;
    }

    public String getEmail() {
        return Email;
    }

    public String getAddress() {
        return Address;
    }

    public String getContact() {
        return Contact;
    }
}
