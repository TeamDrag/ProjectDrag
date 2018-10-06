package com.example.shivamdhammi.drag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class SSOReg extends AppCompatActivity {

    private EditText UserName,Password,RePassword,SSOName,ISOnumber,Email,Address,Contact;
    private Button Register;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssoreg);

        UserName = (EditText)findViewById(R.id.id_username);
        Password = (EditText)findViewById(R.id.id_password);
        RePassword = (EditText)findViewById(R.id.id_repassword);
        SSOName = (EditText)findViewById(R.id.id_ssoname);
        ISOnumber = (EditText)findViewById(R.id.id_isonumber);
        Email = (EditText)findViewById(R.id.id_email);
        Address = (EditText)findViewById(R.id.id_address);
        Contact = (EditText)findViewById(R.id.id_contact);
        Register = (Button)findViewById(R.id.id_register);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("SSO");

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });


    }

    private void addUser(){

        String username = UserName.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String repassword = RePassword.getText().toString().trim();
        String ssoname = SSOName.getText().toString().trim();
        String isonumber = ISOnumber.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String address = Address.getText().toString().trim();
        String contact = Contact.getText().toString().trim();

        if(!TextUtils.isEmpty(username)){

            SSOInfo info = new SSOInfo(username,password,repassword,ssoname,isonumber,email,address,contact);

            myRef.child(username).setValue(info);

            Toast.makeText(getApplicationContext(),"Registered Successfully..",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getApplicationContext(),Login.class);
            startActivity(intent);

        }
        else{
            Toast.makeText(getApplicationContext(),"Please Enter The UserName..",Toast.LENGTH_LONG).show();
        }


    }


}
