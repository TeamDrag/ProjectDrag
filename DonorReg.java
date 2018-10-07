package com.example.vasu.projectdrag;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DonorReg extends AppCompatActivity {

    private EditText Name,Password,RePassword,Address,Contact,Email,Username;
    private Button register;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_reg);

        Name=(EditText)findViewById(R.id.id_name);
        Password=(EditText)findViewById(R.id.id_password);
        RePassword=(EditText)findViewById(R.id.id_repassword);
        Address=(EditText)findViewById(R.id.id_address);
        Contact=(EditText)findViewById(R.id.id_contact);
        Email=(EditText)findViewById(R.id.id_email);
        Username=(EditText)findViewById(R.id.id_username);
        register=(Button)findViewById(R.id.id_register);

        auth=FirebaseAuth.getInstance();
        myref=database.getReference("Donor");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adduser();
            }
        });


    }


    private void adduser()
    {
        String username=Username.getText().toString().trim();
        String name=Name.getText().toString().trim();
        String password=Password.getText().toString().trim();
        String repassword=RePassword.getText().toString().trim();
        String address=Address.getText().toString().trim();
        String contact=Contact.getText().toString().trim();
        String email=Email.getText().toString().trim();



       if(!TextUtils.isEmpty(name))
       {
           if(!TextUtils.isEmpty(username))
           {
               if(Patterns.EMAIL_ADDRESS.matcher(email).matches())
               {
                   if(password.equals(repassword))
                   {
                       if(!TextUtils.isEmpty(contact))
                       {
                           DonorInfo info=new DonorInfo(username,name,email,address,contact);
                           myref.child(username).setValue(info);

                           auth.createUserWithEmailAndPassword(email,password)
                                   .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                       @Override
                                       public void onComplete(@NonNull Task<AuthResult> task) {
                                           if(task.isSuccessful())
                                           {
                                               Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_LONG).show();
                                           }
                                           else
                                           {
                                               Toast.makeText(getApplicationContext(),"Some Error occured",Toast.LENGTH_LONG).show();

                                           }
                                       }
                                   });


                       }
                       else
                       {
                           Contact.setError("Please Enter Conatct Number.");
                           Contact.requestFocus();
                           return;
                       }
                   }
                   else
                   {
                       RePassword.setError("Password didn't match. Try Again.");
                       RePassword.requestFocus();
                       return;
                   }
               }
               else
               {
                   Email.setError("Enter a valid Email");
                   Email.requestFocus();
                   return;
               }
           }
           else
           {
               Username.setError("Please Enter the Username");
               Username.requestFocus();
               return;
           }
       }
       else
       {
           Name.setError("Please Enter the Name");
           Name.requestFocus();
           return;
       }

    }

}
