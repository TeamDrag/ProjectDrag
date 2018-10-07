package com.example.vasu.projectdrag;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        if(get_username.getText().toString().equals("") || get_password.getText().toString().equals("") || radioGroup.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(getApplicationContext(),"Blank Field Not Allowed",Toast.LENGTH_SHORT).show();
        }
        else
        {
            auth.signInWithEmailAndPassword(get_username.getText().toString(),get_password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),"Hey Vasu",Toast.LENGTH_SHORT).show();
                                if(radioButton.getText().toString()=="SSO")
                                {
                                    Toast.makeText(getApplicationContext(),"Hey Dhammi",Toast.LENGTH_SHORT).show();

                                    Intent intent=new Intent(getApplicationContext(),SSO.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Hey Mayank",Toast.LENGTH_SHORT).show();

                                    Intent intent=new Intent(getApplicationContext(),Donor.class);
                                    startActivity(intent);
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"LoggedIn Failed",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

        }
    }


    public void Client(View v)
    {
        Toast.makeText(getApplicationContext(),"Hey Sejal",Toast.LENGTH_SHORT).show();

        selectId=radioGroup.getCheckedRadioButtonId();
        radioButton=(RadioButton)findViewById(selectId);

    }



}
