package com.example.vasu.projectdrag;

public class DonorInfo {
    private String UserName,Name,Email,Address,Contact;


    DonorInfo()
    {

    }

    public DonorInfo(String UserName,String Name,String Email,String Address,String Contact)
    {
        this.UserName=UserName;
        this.Name=Name;
        this.Email=Email;
        this.Address=Address;
        this.Contact=Contact;
    }

    public String getUserName() {
        return UserName;
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
}
