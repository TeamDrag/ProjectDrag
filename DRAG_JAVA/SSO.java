package com.example.shivamdhammi.drag;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

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

public class SSO extends AppCompatActivity {

    private EditText searchbar;
    private Button search;
    private RecyclerView recyclerView;

    private ArrayList<String> ssoNameList;
    private ArrayList<String> addressList;
    private ArrayList<String> profilepicList;

    private SearchAdapter searchAdapter;

    private DatabaseReference ref;
    private StorageReference storageReference;
    private Query query;
    private FirebaseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sso);

        searchbar = (EditText) findViewById(R.id.id_searchbar);
        recyclerView = (RecyclerView)findViewById(R.id.id_recyclerView);

        ref = FirebaseDatabase.getInstance().getReference("SSO");
        storageReference = FirebaseStorage.getInstance().getReference();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

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

                if(!s.toString().isEmpty()){

                    setAdapter(s.toString());

                }else{
                    ssoNameList.clear();
                    addressList.clear();
                    profilepicList.clear();
                    recyclerView.removeAllViews();
                }

            }
        });


    }

    private void setAdapter(final String searchstring) {


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ssoNameList.clear();
                addressList.clear();
                profilepicList.clear();
                recyclerView.removeAllViews();

                int counter = 0;

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    String uid = snapshot.getKey();
                    String ssoname = snapshot.child("ssoname").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String address = snapshot.child("address").getValue(String.class);
                    String profilepic = uid;

                    if(ssoname.toLowerCase().contains(searchstring.toLowerCase())){
                        ssoNameList.add(ssoname);
                        addressList.add(address);
                        profilepicList.add(uid);
                        counter++;

                    }
                    else if(email.toLowerCase().contains(searchstring.toLowerCase())){
                        ssoNameList.add(ssoname);
                        addressList.add(address);
                        profilepicList.add(uid);
                        counter++;
                    }

                    if(counter==30)
                        break;

                }


                searchAdapter = new SearchAdapter(getApplicationContext(),ssoNameList,addressList,profilepicList);
                recyclerView.setAdapter(searchAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}

