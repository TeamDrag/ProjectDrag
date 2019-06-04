
package com.example.shivamdhammi.drag;


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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class SProfileEdit extends Fragment {

    private static final int CHOOSE_IMAGE = 101 ;
    ImageView profilePic;//using bitmap.
    EditText SSOName,ISOnumber,Address,Contact,AccountNo;
    TextView Email,verify,resetPassword;
    Button Save;
    Uri uriProfileImage;//uriZProfileImage = data.getData();[inside startActivityForResult()]
    String profileImageUrl;//To store the Downloaded URL of the image
    FirebaseAuth auth;
    DatabaseReference myRef;
    StorageReference storageReference;
    String Chooser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sprofile_edit, container, false);

        profilePic = (ImageView)view.findViewById(R.id.id_pictue);
        SSOName = (EditText)view.findViewById(R.id.id_ssoname1);
        ISOnumber = (EditText)view.findViewById(R.id.id_isonumber);
        Email = (TextView)view.findViewById(R.id.id_email);
        Address = (EditText)view.findViewById(R.id.id_address);
        Contact = (EditText)view.findViewById(R.id.id_contact1);
        AccountNo = (EditText)view.findViewById(R.id.id_account);
        verify = (TextView)view.findViewById(R.id.id_verify);
        resetPassword = (TextView)view.findViewById(R.id.id_reset);
//        State = (EditText)findViewById(R.id.id_state);

       // if(auth.getCurrentUser().isEmailVerified()){
            verify.setText("Verified Account");
     //   }
  /*      else{
            verify.setText("Account is not verified");
        }*/

        Save = (Button)view.findViewById(R.id.id_donate);

        auth = FirebaseAuth.getInstance();

        Email.setText(auth.getCurrentUser().getEmail());
        //Toast.makeText(getApplicationContext(),test,Toast.LENGTH_LONG).show();

        myRef = FirebaseDatabase.getInstance().getReference("SSO");

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
                //Store the image and display name in Firebase Storage.
                //we use profileImageURL to store it to Storage.

                //////

                SSOInfo newInfo = new SSOInfo
                        (SSOName.getText().toString(),ISOnumber.getText().toString(),Email.getText().toString()
                                ,Address.getText().toString(),Contact.getText().toString(),AccountNo.getText().toString());

                myRef.child(auth.getCurrentUser().getUid()).child("Info").setValue(newInfo);
                /////


                //Test krne ke liye
                /*Intent intent = new Intent(getApplicationContext(),SProfile.class);
                startActivity(intent);*/
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showImageChooser();

                //to select the image from the device.

            }
        });


        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.sendPasswordResetEmail(Email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(),"Visit your email to reset your password.",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(auth.getCurrentUser().isEmailVerified()){
                    verify.setText("Verified Account");
                }
                else {
                    verify.setText("Account is not verified.");
                    auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "Verification Email has been sent", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });


        return view;


    }


    //onStart will retrieve the data from realTime Database and set the value to the corresponding fields.
    @Override
    public void onStart() {
        super.onStart();
        //Toast.makeText(getApplicationContext(),"Ye chl rha hai",Toast.LENGTH_LONG).show();

        //myRef.child(auth.getCurrentUser().getDisplayName().toString());

        //Log.d("dikkat","Conatct"+myRef.child(auth.getCurrentUser().getUid()).child("contact"));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                SSOInfo ssoInfo = dataSnapshot.child(auth.getCurrentUser().getUid()).child("Info").getValue(SSOInfo.class);


                //below code was used for testing . from here...

                //...till here.


                SSOName.setText(ssoInfo.getSSOName());
                ISOnumber.setText(ssoInfo.getISOnumber());
                Email.setText(ssoInfo.getEmail());
                Address.setText(ssoInfo.getAddress());
                Contact.setText(ssoInfo.getContact());
                AccountNo.setText(ssoInfo.getAccountno());

                //Toast.makeText(getApplicationContext(),dataSnapshot.getValue().toString(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();

            }
        });


        try {
            storageReference.child("profilepics/"+auth.getCurrentUser().getUid()+".jpg")
                    .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //Load image in the image view from Firebase Storage.
                    Glide.with(getContext()).
                            load(uri).
                            into(profilePic);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(),"@@@@@@@@@@@@@@@@",Toast.LENGTH_LONG).show();
            return;

        }


    }


    //saves the users info to the Storage.
    private void saveUserInfo() {

        FirebaseUser user = auth.getCurrentUser();

       if(user !=null && profileImageUrl!=null){

           UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                   .setDisplayName(SSOName.getText().toString())
                   .setPhotoUri(Uri.parse(profileImageUrl))
                   .build();



            //user.updateProfile() default function to update user's info.
           user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                   if(task.isSuccessful()){
                       Toast.makeText(getContext(),"Profile updated",Toast.LENGTH_LONG).show();
                   }
               }
           });
       }

    }




    //this function let us choose a image from the images in the device.
    private void showImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //to Upload a image of our choice.
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);
    }


    //We override the onActivityResult to set the selected image to imageView(in this case , profilePic).
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            uriProfileImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),uriProfileImage);
                profilePic.setImageBitmap(bitmap);

                //To upload image to Firebase Storage.
                uploadImageToFirebaseStorage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage() {
        final StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("profilepics/"+auth.getCurrentUser().getUid()+ ".jpg");

        if(uriProfileImage!=null){

             profileImageRef.putFile(uriProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                     profileImageUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                 }
             });
        }
    }
}
