//bhai 106 line se check kr ek baar.


package com.example.shivamdhammi.drag;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class SProfileEdit extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101 ;
    ImageView profilePic;//using bitmap.
    EditText SSOName,ISOnumber,Address,Contact,AccountNo;
    TextView Email;
    Button Save;
    Uri uriProfileImage;//uriZProfileImage = data.getData();[inside startActivityForResult()]
    String profileImageUrl;//To store the Downloaded URL of the image
    FirebaseAuth auth;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprofile_edit);

        profilePic = (ImageView)findViewById(R.id.id_pic);
        SSOName = (EditText)findViewById(R.id.id_ssoname);
        ISOnumber = (EditText)findViewById(R.id.id_isonumber);
        Email = (TextView)findViewById(R.id.id_email);
        Address = (EditText)findViewById(R.id.id_address);
        Contact = (EditText)findViewById(R.id.id_contact);
        AccountNo = (EditText)findViewById(R.id.id_account);

        Save = (Button)findViewById(R.id.id_donate);

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

                myRef.child(auth.getCurrentUser().getUid()).setValue(newInfo);
                /////
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showImageChooser();
                //to select the image from the device.

            }
        });

    }


    //onStart will retrieve the data from realTime Database and set the value to the corresponding fields.
    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(getApplicationContext(),"Ye chl rha hai",Toast.LENGTH_LONG).show();

        Log.d("dikkat","Conatct"+myRef.child(auth.getCurrentUser().getUid()).child("contact"));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                SSOInfo ssoInfo = dataSnapshot.child(auth.getCurrentUser().getUid()).getValue(SSOInfo.class);

                Log.d("dikkat1",dataSnapshot.child(auth.getCurrentUser().getUid()).getValue(SSOInfo.class).toString());
                Log.d("dikkat2",dataSnapshot.child(auth.getCurrentUser().getUid()).getValue().toString());
                Log.d("dikkat3",dataSnapshot.child(auth.getCurrentUser().getUid()).toString());
                Log.d("dikkat4",dataSnapshot.toString());
                Log.d("dikkat5","$$"+dataSnapshot.child(auth.getCurrentUser().getUid()).getValue(SSOInfo.class).getContact());

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

                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();

            }
        });


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
                       Toast.makeText(getApplicationContext(),"Profile updated",Toast.LENGTH_LONG).show();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            uriProfileImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriProfileImage);
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
                     Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                 }
             });
        }
    }
}
