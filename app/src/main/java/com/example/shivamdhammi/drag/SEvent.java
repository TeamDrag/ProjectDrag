package com.example.shivamdhammi.drag;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SEvent extends Fragment {

    EditText name,description;
    Button submit;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("SSO");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_sevent, container, false);


        submit = (Button)view.findViewById(R.id.id_ssubmit);
        name = (EditText) view.findViewById(R.id.id_seventname);
        description = (EditText)view.findViewById(R.id.id_seventdescription);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent();
            }
        });

        return view;
    }

    void addEvent(){

        String Name = name.getText().toString().trim();
        String Desc = description.getText().toString().trim();

        if(Name.isEmpty()){
            name.setError("Enter the Event Name");
            name.requestFocus();
            return;
        }
        else if(Desc.isEmpty()){
            description.setError("Enter the Description");
            description.requestFocus();
            return;
        }
        else{

            EventInfo info = new EventInfo(Name,Desc);
            myRef.child(mAuth.getCurrentUser().getUid()).child("Event").setValue(info);
            Toast.makeText(getContext(),"Event Registered Sucessfully",Toast.LENGTH_LONG).show();

        }




    }



}
