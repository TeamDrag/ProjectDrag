package com.example.shivamdhammi.drag;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DEvent extends Fragment {
    EditText Eventname,Eventdescription;
    Button Submit;
    FirebaseAuth mauth;
    DatabaseReference myref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_devent, container, false);
        Eventname=(EditText)view.findViewById(R.id.id_eventname);
        Eventdescription=(EditText)view.findViewById(R.id.id_eventdescription);
        Submit=(Button)view.findViewById(R.id.id_submit);
        mauth=FirebaseAuth.getInstance();
        myref=FirebaseDatabase.getInstance().getReference("DONOR");

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Function which registres the event in Firebase Database
                addevent();
            }
        });
        return view;
    }

    private void addevent() {
        String eventname=Eventname.getText().toString();
        String eventdescription=Eventdescription.getText().toString();
        if(TextUtils.isEmpty(eventname))
        {
            Eventname.setError("Please Enter the Event Name");
            Eventname.requestFocus();
            return;
        }
        else
        {
            if(TextUtils.isEmpty(eventdescription))
            {
                Eventdescription.setError("Please describe the Event");
                Eventdescription.requestFocus();
                return;
            }
            else
            {
                EventInfo eventInfo=new EventInfo(eventname,eventdescription);
                myref.child(mauth.getCurrentUser().getUid()).child("Event").setValue(eventInfo);
                Toast.makeText(getContext(),"Event Registered Sucessfully",  Toast.LENGTH_LONG).show();
                //Calls the Donor Profile
                //startActivity(new Intent(getApplicationContext(),Donor.class));
            }
        }
    }
}
