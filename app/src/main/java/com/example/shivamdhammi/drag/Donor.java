package com.example.shivamdhammi.drag;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Donor extends Fragment {

    RadioGroup radioGroup;
    RadioButton radioButton;

    private EditText msearchbar;
    private String radio="SSO";
    //ImageButton msearchbutton;
    private RecyclerView recyclerView;
    private ArrayList<String> ssonameList;
    private ArrayList<String> addressList;
    private ArrayList<String> profilepicList;
    private SearchAdapter searchAdapter;

    DatabaseReference myref1,myref2;

    //String st;
    private StorageReference storageReference;
    public static final String EXTRA_TEXT="com.example.vasu.projectdrag.EXTRA_TEXT";

    Button profile,event;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_donor, container, false);
        //Intent intent=getIntent();
        //String st=intent.getStringExtra(Login.EXTRA_TEXT);
       /* if(st.isEmpty())
        {   Toast.makeText(getApplicationContext(),"Sejal",Toast.LENGTH_SHORT).show();
            Intent intent1=getIntent();
            st=intent1.getStringExtra(Splash.EXTRA_TEXT);
            Toast.makeText(getApplicationContext(),st+"Sejal",Toast.LENGTH_SHORT).show();
        }*/
        //final String state=st;
        //Toast.makeText(getApplicationContext(),state,Toast.LENGTH_SHORT).show();
        //st=state;

        msearchbar=(EditText)view.findViewById(R.id.id_searchbar);
        recyclerView=(RecyclerView)view.findViewById(R.id.id_recyclerview);

        myref1=FirebaseDatabase.getInstance().getReference("SSO");
        myref2=FirebaseDatabase.getInstance().getReference("DONOR");

        storageReference= FirebaseStorage.getInstance().getReference();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        radioGroup=(RadioGroup)view.findViewById(R.id.id_radiogroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.id_radiosso:
                    {
                        radio= "SSO";
                        break;
                    }
                    case R.id.id_radiodonor:
                    {
                        radio= "DONOR";
                        break;
                    }
                }
            }
        });


        ssonameList=new ArrayList<>();
        addressList=new ArrayList<>();
        profilepicList=new ArrayList<>();



        msearchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty())
                {
                    setAdapter(s.toString());
                }
                else
                {
                    ssonameList.clear();
                    addressList.clear();
                    profilepicList.clear();
                    recyclerView.removeAllViews();
                }
            }
        });


        return view;
      /*  msearchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseUserSearch();
            }
        });*/
    }
    private void setAdapter(final String searchedstring)
    {
        if(radio.equals("SSO"))
        {
            myref1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ssonameList.clear();
                    addressList.clear();
                    profilepicList.clear();
                    recyclerView.removeAllViews();

                    int counter=0;

                    for(DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        String uid=snapshot.getKey();
                        SSOInfo ssoinfo = snapshot.child("Info").getValue(SSOInfo.class);
                        String ssoname=ssoinfo.getSSOName();
                        String email=ssoinfo.getEmail();
                        String address=ssoinfo.getAddress();
                        String profilepic=uid;

                        Log.d("vasuu",ssoname);


                        if(ssoname.toLowerCase().contains(searchedstring.toLowerCase()))
                        {
                            ssonameList.add(ssoname);
                            addressList.add(address);
                            profilepicList.add(uid);
                            counter++;
                        }
                        else if(email.toLowerCase().contains(searchedstring.toLowerCase()))
                        {
                            ssonameList.add(ssoname);
                            addressList.add(address);
                            profilepicList.add(uid);
                            counter++;
                        }

                        if(counter==20)
                            break;
                    }

                    searchAdapter=new SearchAdapter(getContext(),ssonameList,addressList,profilepicList);
                    recyclerView.setAdapter(searchAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
        {
            // Myref1 is for SSO list
            //address list mein description hai
            myref1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ssonameList.clear();
                    addressList.clear();
                    profilepicList.clear();
                    recyclerView.removeAllViews();
                    int counter=0;

                    for(DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        String uid=snapshot.getKey();
                        String ssoname=snapshot.child("Event").child("name").getValue(String.class);
                        String desc=snapshot.child("Event").child("description").getValue(String.class);
                       // String contact=snapshot.child("Event").child("contact").getValue(String.class);
                        String profilepic=uid;

                        if(ssoname.toLowerCase().contains(searchedstring.toLowerCase()))
                        {
                            ssonameList.add(ssoname);
                            addressList.add(desc);
                            profilepicList.add(uid);
                            counter++;
                        }
                        else if(desc.toLowerCase().contains(searchedstring.toLowerCase()))
                        {
                            ssonameList.add(ssoname);
                            addressList.add(desc);
                            profilepicList.add(uid);
                            counter++;
                        }

                        if(counter==10)
                            break;
                    }


                    searchAdapter=new SearchAdapter(getContext(),ssonameList,addressList,profilepicList);
                    recyclerView.setAdapter(searchAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            // address list mein donor ka description hai
            myref2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    //ssonameList.clear();
                    //addressList.clear();
                    //profilepicList.clear();
                    //recyclerView.removeAllViews();

                    int counter=0;

                    for(DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        String uid=snapshot.getKey();
                        String ssoname=snapshot.child("Event").child("name").getValue(String.class);
                        String desc=snapshot.child("Event").child("description").getValue(String.class);
                        //String contact=snapshot.child("Event").child("contact").getValue(String.class);
                        String profilepic=uid;

                        //Log.d("vasuu",ssoname);


                        if(ssoname.toLowerCase().contains(searchedstring.toLowerCase()))
                        {
                            ssonameList.add(ssoname);
                            addressList.add(desc);
                            profilepicList.add(uid);
                            counter++;
                        }
                        else if(desc.toLowerCase().contains(searchedstring.toLowerCase()))
                        {
                            ssonameList.add(ssoname);
                            addressList.add(desc);
                            profilepicList.add(uid);
                            counter++;
                        }

                        if(counter==10)
                            break;
                    }

                    searchAdapter=new SearchAdapter(getContext(),ssonameList,addressList,profilepicList);
                    recyclerView.setAdapter(searchAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




        }
        /*myref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ssonameList.clear();
                addressList.clear();
                profilepicList.clear();
                recyclerView.removeAllViews();

                int counter=0;

                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    String uid=snapshot.getKey();
                    String ssoname=snapshot.child("ssoname").getValue(String.class);
                    String email=snapshot.child("email").getValue(String.class);
                    String address=snapshot.child("address").getValue(String.class);
                    String profilepic=uid;

                    //Log.d("vasuu",ssoname);


                    if(ssoname.toLowerCase().contains(searchedstring.toLowerCase()))
                    {
                        ssonameList.add(ssoname);
                        addressList.add(address);
                        profilepicList.add(uid);
                        counter++;
                    }
                    else if(email.toLowerCase().contains(searchedstring.toLowerCase()))
                    {
                        ssonameList.add(ssoname);
                        addressList.add(address);
                        profilepicList.add(uid);
                        counter++;
                    }

                    if(counter==20)
                        break;
                }

                searchAdapter=new SearchAdapter(getApplicationContext(),ssonameList,addressList,profilepicList);
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }

    /* public void Client(View v1)
    {   Log.d("Vasu =============","Bakhchod k andar");
        radioButton=(RadioButton)v1.findViewById(radioGroup.getCheckedRadioButtonId());
    }*/

}
