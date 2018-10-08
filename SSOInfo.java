package com.example.shivamdhammi.drag;

public class SSOInfo {

    private String UserName,SSOName,ISOnumber,Email,Address,Contact;

    SSOInfo(){

    }

    public SSOInfo(String UserName,String SSOName, String ISOnumber, String email, String address, String contact) {
        this.UserName = UserName;
        this.SSOName = SSOName;
        this.ISOnumber = ISOnumber;
        Email = email;
        Address = address;
        Contact = contact;
    }

    public String getUserName() {
        return UserName;
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
