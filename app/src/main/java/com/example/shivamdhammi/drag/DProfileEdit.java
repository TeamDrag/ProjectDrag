package com.example.shivamdhammi.drag;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class DProfileEdit extends Fragment {

    ImageView profilepic;
    private static final int choose_Image=101;
    private EditText Name,Address,Contact;
    private TextView Email,verify,resetpassword;
    Uri uriProfileImage;
    //String stt;
    //ProgressBar progressBar;
    DatabaseReference myref;
    String profileImageUrl;
    Button save;
    FirebaseAuth auth;
    StorageReference storageReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dprofile_edit, container, false);
        //Intent intent=getIntent();
        //final String state1=intent.getStringExtra(Donor.EXTRA_TEXT);
        //Toast.makeText(getApplicationContext(),state1+"Mridul",Toast.LENGTH_SHORT).show();
        //stt=Statename(state1);

        verify=(TextView)view.findViewById(R.id.id_verify);
        resetpassword=(TextView)view.findViewById(R.id.id_reset);
        Name=(EditText)view.findViewById(R.id.id_name);
        Email=(TextView) view.findViewById(R.id.id_email);
        Address=(EditText)view.findViewById(R.id.id_address);
        Contact=(EditText)view.findViewById(R.id.id_contact);
        profilepic=(ImageView)view.findViewById(R.id.id_editpic);
        //State=(TextView)findViewById(R.id.id_state);
        save=(Button)view.findViewById(R.id.id_save);
       // if(auth.getCurrentUser().isEmailVerified())
                    verify.setText("Verified Account");
        /*
        else
        {
            verify.setText("Account is not Verified");
        }*/
        auth=FirebaseAuth.getInstance();
        myref=FirebaseDatabase.getInstance().getReference("DONOR");
        //Toast.makeText(getApplicationContext(), profileImageUrl, Toast.LENGTH_SHORT).show();
        String email=auth.getCurrentUser().getEmail().toString();
        Email.setText(email);
        //State.setText(state1);
        //Image OnClick
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });


        //Saving Profile
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Hey Anant",Toast.LENGTH_SHORT).show();
                saveUserInfo();

                DonorInfo newinfo= new DonorInfo(Name.getText().toString(),
                        Email.getText().toString(),
                        Address.getText().toString(),
                        Contact.getText().toString());

                myref.child(auth.getCurrentUser().getUid()).child("Info").setValue(newinfo);

            }
        });


        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.sendPasswordResetEmail(Email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getContext(),"Visit your Email to reset your Password",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(auth.getCurrentUser().isEmailVerified())
                {
                    verify.setText("Verified Account");
                }
                else
                {
                    verify.setText("Email is not Verified");
                    auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(),"Verification Email has been Sent",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    return view;
    }

    private String Statename(String state1) {
        return state1;

    }


    @Override
    public void onStart() {
        super.onStart();


        //Log.d("dikkat","Contact"+myref.child(auth.getCurrentUser().getUid()).child("contact"));

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DonorInfo donorInfo=dataSnapshot.child(auth.getCurrentUser().getUid()).child("Info").getValue(DonorInfo.class);


                //For Testing Purpose
                /*
                Log.d("dikkat1",dataSnapshot.child(auth.getCurrentUser().getUid()).getValue(DonorInfo.class).toString());
                Log.d("dikkat2",dataSnapshot.child(auth.getCurrentUser().getUid()).getValue().toString());
                Log.d("dikkat3",dataSnapshot.child(auth.getCurrentUser().getUid()).toString());
                Log.d("dikkat4",dataSnapshot.toString());
                Log.d("dikkat5","$$"+dataSnapshot.child(auth.getCurrentUser().getUid()).getValue(DonorInfo.class).getContact());
                 */
                //Testing Over


                Name.setText(donorInfo.getName());
                Address.setText(donorInfo.getAddress());
                Contact.setText(donorInfo.getContact());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
            }
        });


        try{
            storageReference.child("profilepics/"+auth.getCurrentUser().getUid()+".jpg")
                    .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(getContext()).load(uri).into(profilepic);
                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(),"@@@@@@@@@@@@@@@@",Toast.LENGTH_LONG).show();
            return;
        }


    }


    private void  saveUserInfo()
    {
        FirebaseUser user=auth.getCurrentUser();
        if(user!=null && profileImageUrl!=null)
        {
            UserProfileChangeRequest profile=new UserProfileChangeRequest.Builder()
                    .setDisplayName(Name.getText().toString())
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build();
            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getContext(),"Profile Updated",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==choose_Image && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            uriProfileImage=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),uriProfileImage);
                profilepic.setImageBitmap(bitmap);

                uploadImagetoFirebaseStorage();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private void uploadImagetoFirebaseStorage() {
        final StorageReference profileImageRef=
                FirebaseStorage.getInstance().getReference("profilepics/"+auth.getCurrentUser().getUid()+".jpg");

        if(uriProfileImage!=null)
        {
            profileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            profileImageUrl=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                            //Toast.makeText(getApplicationContext(),profileImageUrl,Toast.LENGTH_SHORT).show();

                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(),"Image failed to load",Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    private void showImageChooser()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile image"),choose_Image);
    }
}
