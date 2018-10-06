package com.example.shivamdhammi.drag;

public class SSOInfo {

    private String UserName,Password,RePassword,SSOName,ISOnumber,Email,Address,Contact;

    SSOInfo(){

    }

    public SSOInfo(String userName, String password, String rePassword, String SSOName, String ISOnumber, String email, String address, String contact) {
        UserName = userName;
        Password = password;
        RePassword = rePassword;
        this.SSOName = SSOName;
        this.ISOnumber = ISOnumber;
        Email = email;
        Address = address;
        Contact = contact;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPassword() {
        return Password;
    }

    public String getRePassword() {
        return RePassword;
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
