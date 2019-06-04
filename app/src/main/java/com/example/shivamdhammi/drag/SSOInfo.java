package com.example.shivamdhammi.drag;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class SSOInfo {

    private String SSOName,ISOnumber,Email,Address,Contact,AccountNo;

    public SSOInfo(){
    }

    public SSOInfo(String ssoname, String isonumber, String email, String address, String contact,String accountno) {
        SSOName = ssoname;
        ISOnumber = isonumber;
        Email = email;
        Address = address;
        Contact = contact;
        AccountNo = accountno;
    }

    public String getAccountno() {
        return AccountNo;
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

    public void setAccountno(String Accountno) {
        AccountNo = Accountno;
    }


    //ye ignore kr bhai
 /*   @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ssoname", SSOName);
        result.put("isonumber", ISOnumber);
        result.put("contact", Contact);
        result.put("address", Address);
        result.put("email", Email);

        return result;
    }*/



}
