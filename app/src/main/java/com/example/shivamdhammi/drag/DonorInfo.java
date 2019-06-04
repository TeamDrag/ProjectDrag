package com.example.shivamdhammi.drag;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class DonorInfo {
    private String Name,Email,Address,Contact;
    public Map<String, Boolean> stars = new HashMap<>();


    DonorInfo()
    {

    }

    public DonorInfo(String Name,String Email,String Address,String Contact)
    {
        this.Name=Name;
        this.Email=Email;
        this.Address=Address;
        this.Contact=Contact;
    }

    public String getName() {
        return Name;
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

    public void setName(String name) {
        Name = name;
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

    //@Exclude
    /*public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", Name);
        result.put("contact", Contact);
        result.put("address", Address);
        result.put("email", Email);

        return result;
    }*/
}
