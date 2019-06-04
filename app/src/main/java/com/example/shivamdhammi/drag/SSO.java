package com.example.shivamdhammi.drag;

import android.content.Intent;
import android.graphics.Bitmap;
import android.nfc.Tag;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.LogTime;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class SSO extends Fragment {

    private EditText searchbar;
    private RecyclerView recyclerView;
    private String radio="SSO";
    RadioGroup radioGroup;
    RadioButton radioButton;
    private Button profile, event;

    private ArrayList<String> ssoNameList;
    private ArrayList<String> addressList;
    private ArrayList<String> profilepicList;

    private SearchAdapter searchAdapter;

    private DatabaseReference mref1,mref2;
    private StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_sso, container, false);

        searchbar = view.findViewById(R.id.id_searchbar);
        recyclerView = view.findViewById(R.id.id_recyclerView);

        mref1 = FirebaseDatabase.getInstance().getReference("SSO");
        mref2 = FirebaseDatabase.getInstance().getReference("DONOR");
        storageReference = FirebaseStorage.getInstance().getReference();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        radioGroup = (RadioGroup) view.findViewById(R.id.id_radiogroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.id_radiosso: {
                        radio = "SSO";
                        break;
                    }
                    case R.id.id_radiodonor: {
                        radio = "DONOR";
                        break;
                    }
                }
            }
        });

        ssoNameList = new ArrayList<>();
        addressList = new ArrayList<>();
        profilepicList = new ArrayList<>();


        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty()) {

                    setAdapter(s.toString());

                } else {
                    ssoNameList.clear();
                    addressList.clear();
                    profilepicList.clear();
                    recyclerView.removeAllViews();
                }

            }
        });

        return view;

    }

    private void setAdapter(final String searchstring) {


        if (radio.equals("SSO")) {
            mref1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ssoNameList.clear();
                    addressList.clear();
                    profilepicList.clear();
                    recyclerView.removeAllViews();

                    int counter = 0;

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                        String uid = snapshot.getKey();
                    /*String ssoname = snapshot.child("ssoname").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String address = snapshot.child("address").getValue(String.class);*/
                        SSOInfo ssoInfo = snapshot.child("Info").getValue(SSOInfo.class);
                        String profilepic = uid;
                        String email = ssoInfo.getEmail();
                        String address = ssoInfo.getAddress();
                        String ssoname = ssoInfo.getSSOName();

                        //               Log.d("SHIVAM_dHAMMI",)

                        if (ssoname.toLowerCase().contains(searchstring.toLowerCase())) {
                            ssoNameList.add(ssoname);
                            addressList.add(address);
                            profilepicList.add(uid);
                            counter++;

                        } else if (email.toLowerCase().contains(searchstring.toLowerCase())) {
                            ssoNameList.add(ssoname);
                            addressList.add(address);
                            profilepicList.add(uid);
                            counter++;
                        }

                        if (counter == 20)
                            break;

                    }

                    Log.d("shivam@@@@@@@", "yaha toh aa gye");
                    searchAdapter = new SearchAdapter(getContext(), ssoNameList, addressList, profilepicList);
                    Log.d("Mayank", "bahar aa gye");
                    recyclerView.setAdapter(searchAdapter);

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    else {
            // Myref1 is for SSO list
            //address list mein description hai
            mref1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ssoNameList.clear();
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

                        if(ssoname.toLowerCase().contains(searchstring.toLowerCase()))
                        {
                            ssoNameList.add(ssoname);
                            addressList.add(desc);
                            profilepicList.add(uid);
                            counter++;
                        }
                        else if(desc.toLowerCase().contains(searchstring.toLowerCase()))
                        {
                            ssoNameList.add(ssoname);
                            addressList.add(desc);
                            profilepicList.add(uid);
                            counter++;
                        }

                        if(counter==10)
                            break;
                    }


                    searchAdapter=new SearchAdapter(getContext(),ssoNameList,addressList,profilepicList);
                    recyclerView.setAdapter(searchAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            // address list mein donor ka description hai
            mref2.addListenerForSingleValueEvent(new ValueEventListener() {
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


                        if(ssoname.toLowerCase().contains(searchstring.toLowerCase()))
                        {
                            ssoNameList.add(ssoname);
                            addressList.add(desc);
                            profilepicList.add(uid);
                            counter++;
                        }
                        else if(desc.toLowerCase().contains(searchstring.toLowerCase()))
                        {
                            ssoNameList.add(ssoname);
                            addressList.add(desc);
                            profilepicList.add(uid);
                            counter++;
                        }

                        if(counter==10)
                            break;
                    }

                    searchAdapter=new SearchAdapter(getContext(),ssoNameList,addressList,profilepicList);
                    recyclerView.setAdapter(searchAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




        }
    }
}




