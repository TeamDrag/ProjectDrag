package com.example.shivamdhammi.drag;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SProfile extends AppCompatActivity {

    TextView Email,SSOName,ISOnumber,Address,Contact,AccountNo,verify;
    DatabaseReference ref;
    Button donate;
    StorageReference storageReference;
    ImageView pic;
    String picURL;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprofile);

        getIncomingIntent();

        Email =(TextView)findViewById(R.id.id_email);
        SSOName =(TextView)findViewById(R.id.id_ssoname1);
        ISOnumber =(TextView)findViewById(R.id.id_isonumber);
        Address =(TextView)findViewById(R.id.id_address);
        Contact =(TextView)findViewById(R.id.id_contact1);
        AccountNo =(TextView)findViewById(R.id.id_account);
        verify = (TextView)findViewById(R.id.id_verify);

        pic = (ImageView)findViewById(R.id.id_pictue);

        donate = (Button)findViewById(R.id.id_donate);

        ref = FirebaseDatabase.getInstance().getReference("SSO");
        storageReference = FirebaseStorage.getInstance().getReference();

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplicationContext(),);
                startActivity(intent);*/
            }
        });

        /*if(auth.getCurrentUser().isEmailVerified()){
            verify.setText("Verified Account");
        }
        else {
            verify.setText("Account is not verified.");
            auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), "Verification Email has been sent", Toast.LENGTH_LONG).show();
                }
            });
        }*/

    }

    @Override
    protected void onStart() {
        super.onStart();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SSOInfo ssoInfo = dataSnapshot.child(userID).getValue(SSOInfo.class);

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

       storageReference.child("profilepics/"+userID+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
           @Override
           public void onSuccess(Uri uri) {
               //Load image in the image view from Firebase Storage.
               Glide.with(getApplicationContext()).
                       load(uri).
                       into(pic);
           }
       });

    }


    private void getIncomingIntent(){

        if(getIntent().hasExtra("Uid")){

            userID = getIntent().getStringExtra("Uid");
        }

    }

}
