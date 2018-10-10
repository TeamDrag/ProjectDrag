package com.example.shivamdhammi.drag;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class SSOInfo {

    private String UserName,SSOName,ISOnumber,Email,Address,Contact;

    public Map<String, Boolean> stars = new HashMap<>();

    public SSOInfo(){

    }

    public SSOInfo(String userName,String ssoname, String isonumber, String email, String address, String contact) {
        UserName = userName;
        SSOName = ssoname;
        ISOnumber = isonumber;
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

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setSSOName(String ssoname) {
        this.SSOName = ssoname;
    }

    public void setISOnumber(String isOnumber) {
        this.ISOnumber = isOnumber;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    
    //ye ignore kr bhai
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", UserName);
        result.put("ssoname", SSOName);
        result.put("isonumber", ISOnumber);
        result.put("contact", Contact);
        result.put("address", Address);
        result.put("email", Email);

        return result;
    }



}
