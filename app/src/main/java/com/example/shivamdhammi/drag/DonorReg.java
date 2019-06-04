package com.example.shivamdhammi.drag;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.xml.transform.sax.SAXTransformerFactory;

public class DonorReg extends AppCompatActivity {

    private EditText Name,Password,RePassword,Address,Contact,Email;
    private Button register;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference myref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_reg);

        getSupportActionBar().setTitle("Donor Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Name=(EditText)findViewById(R.id.id_name);
        Password=(EditText)findViewById(R.id.id_password);
        RePassword=(EditText)findViewById(R.id.id_repassword);
        Address=(EditText)findViewById(R.id.id_address);
        Contact=(EditText)findViewById(R.id.id_contact);
        Email=(EditText)findViewById(R.id.id_email);
        //State=(EditText)findViewById(R.id.id_state);
        //Username=(EditText)findViewById(R.id.id_username);
        register=(Button)findViewById(R.id.id_register);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        myref=database.getReference("DONOR");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adduser();
            }
        });


    }


    private void adduser()
    {
        //String username=Username.getText().toString().trim();
        String name=Name.getText().toString().trim();
        String password=Password.getText().toString().trim();
        String repassword=RePassword.getText().toString().trim();
        String address=Address.getText().toString().trim();
        String contact=Contact.getText().toString().trim();
        String email=Email.getText().toString().trim();
        //final String state=State.getText().toString().trim();



        if(!TextUtils.isEmpty(name))
        {
            if(Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                if(password.equals(repassword) && !password.equals(""))
                {
                    if(password.length()>7)
                    {
                        if(!contact.isEmpty() && contact.length()==10)
                        {
                            //if(!state.isEmpty())
                            //{
                            final DonorInfo info=new DonorInfo(name,email,address,contact);

                            auth.createUserWithEmailAndPassword(email,password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful())
                                            {   EventInfo eventInfo=new EventInfo("Empty","Empty");
                                                myref.child(auth.getCurrentUser().getUid()).child("Info").setValue(info);
                                                myref.child(auth.getCurrentUser().getUid()).child("Event").setValue(eventInfo);
                                                Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else
                                            {
                                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                                    Toast.makeText(getApplicationContext(), "Email is already registered", Toast.LENGTH_LONG).show();
                                                } else if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                                    Toast.makeText(getApplicationContext(), "Password is too weak", Toast.LENGTH_LONG).show();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }
                                    });
                            // }
                            //else
                            //{
                            //   State.setError("Please Select State.");
                            //  State.requestFocus();
                            // return;
                            //}

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
                        Password.setError("Minimum Password Length is 8");
                        Password.requestFocus();
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
            Name.setError("Please Enter the Name");
            Name.requestFocus();
            return;
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
