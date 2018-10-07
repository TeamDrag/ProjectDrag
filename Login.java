package com.example.shivamdhammi.drag;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Login extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText get_username,get_password;
    Button forgot_password,login,noaccount;
    int selectId;


    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        radioGroup =findViewById(R.id.id_radiogroup);
        get_username=(EditText)findViewById(R.id.id_getusername);
        get_password=(EditText)findViewById(R.id.id_getpassword);
        forgot_password=(Button)findViewById(R.id.id_forgotpassword);
        login=(Button)findViewById(R.id.id_login);
        noaccount=(Button)findViewById(R.id.id_noaccount);

        auth=FirebaseAuth.getInstance();



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        noaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
            }
        });
    }


    public void loginUser()
    {
        //Bhai maine yaha changes kiye hain.
        //Toast vale statement bhi hta diye hain. 
        
        String username = get_username.getText().toString().trim();
        String password = get_password.getText().toString().trim();

        if(username.isEmpty()){

            get_username.setError("Email is Required");
            get_username.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){

            get_username.setError("Enter a valid Email Address");
            get_username.requestFocus();
            return;
        }


        if(password.isEmpty()){

            get_password.setError("Password is Required");
            get_password.requestFocus();
            return;
        }

        if(password.length()<8){
            get_password.setError("Minimum Password Length is 8.");
            get_password.requestFocus();
            return;
        }

        auth.signInWithEmailAndPassword(username,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                //Toast.makeText(getApplicationContext(),"Hey Vasu",Toast.LENGTH_SHORT).show();
                                if(radioButton.getText().toString()=="SSO")
                                {
                                    //Toast.makeText(getApplicationContext(),"Hey Dhammi",Toast.LENGTH_SHORT).show();

                                    Intent intent=new Intent(getApplicationContext(),SSO.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    //Toast.makeText(getApplicationContext(),"Hey Mayank",Toast.LENGTH_SHORT).show();

                                    Intent intent=new Intent(getApplicationContext(),Donor.class);
                                    startActivity(intent);
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"LoggedIn Failed",Toast.LENGTH_LONG).show();

                            }
                        }
                    });
    }


    public void Client(View v)
    {
        //Toast.makeText(getApplicationContext(),"Hey Sejal",Toast.LENGTH_SHORT).show();

        selectId=radioGroup.getCheckedRadioButtonId();
        radioButton=(RadioButton)findViewById(selectId);

    }



}
