package com.example.shivamdhammi.drag;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SProfile extends AppCompatActivity {

    TextView Email,SSOName,ISOnumber,Address,Contact,AccountNo;
    FirebaseAuth auth;
    DatabaseReference ref;
    Button donate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprofile);

        Email =(TextView)findViewById(R.id.id_email);
        SSOName =(TextView)findViewById(R.id.id_ssoname);
        ISOnumber =(TextView)findViewById(R.id.id_isonumber);
        Address =(TextView)findViewById(R.id.id_address);
        Contact =(TextView)findViewById(R.id.id_contact);
        AccountNo =(TextView)findViewById(R.id.id_account);


        donate = (Button)findViewById(R.id.id_donate);

        auth =FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("SSO");

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplicationContext(),);
                startActivity(intent);*/
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SSOInfo ssoInfo = dataSnapshot.child(auth.getCurrentUser().getUid()).getValue(SSOInfo.class);

                SSOName.setText(ssoInfo.getSSOName());
                ISOnumber.setText(ssoInfo.getISOnumber());
                Email.setText(ssoInfo.getEmail());
                Address.setText(ssoInfo.getAddress());
                Contact.setText(ssoInfo.getContact());
                AccountNo.setText(ssoInfo.getAccountno());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"Some Error Occurred",Toast.LENGTH_LONG).show();

            }
        });


    }

}
