package com.example.shivamdhammi.drag;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity{
    static int splashtime=1000;
    FirebaseAuth auth;
    public static final String EXTRA_TEXT="com.example.vasu.projectdrag.EXTRA_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        auth=FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //  Shared Preference Start ho rha hai Bhai....
                final String user,pass,client;
                SharedPreferences sharedPref=getSharedPreferences("MyData", Context.MODE_PRIVATE);
                user=sharedPref.getString("Email","");
                pass=sharedPref.getString("Password","");
                client=sharedPref.getString("Client","");
                if(!user.equals("") && !pass.equals("") && !client.equals(""))
                {
                    //  Agar pehle Login krchuka hai tab andar aayega
                    //Toast.makeText(getApplicationContext(),user,Toast.LENGTH_SHORT).show();
                    auth.signInWithEmailAndPassword(user,pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<AuthResult> task) {
                                                           if(task.isSuccessful())
                                                           {
                                                               //Toast.makeText(getApplicationContext(),"Hey Vasu",Toast.LENGTH_SHORT).show();
                                                               if(client.equals("SSO"))
                                                               {
                                                                   // agar SSO hai tab SSO khulega....

                                                                   // Dhammi mein isko Main  krna hai, SSO ki jagah
                                                                   Intent intent=new Intent(getApplicationContext(),Main.class);
                                                                   startActivity(intent);
                                                                   finish();
                                                               }
                                                               else
                                                               {
                                                                   // Agar Donor hai tab Donor khulega....

                                                                   // Dhammi mein MainD banana padega.
                                                                   Intent intent=new Intent(getApplicationContext(),DMain.class);
                                                                   //intent.putExtra(EXTRA_TEXT,state);
                                                                   startActivity(intent);
                                                                   finish();
                                                               }
                                                           }
                                                           else
                                                           {
                                                               // Agar koi error aati hai tab Home Page hi khulega
                                                               //Toast.makeText(getApplicationContext(),"Hey Dhammi",Toast.LENGTH_SHORT).show();
                                                               Intent intent = new Intent(Splash.this, Home.class);
                                                               startActivity(intent);
                                                               finish();
                                                           }
                                                       }
                                                   }
                            );
                }
                else
                {
                    // Agar ek baar bhi koi login nhi hua hai tab Login page yaha se khulega..
                    //Toast.makeText(getApplicationContext(),"Hey Dhammi",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Splash.this, Home.class);
                    startActivity(intent);
                    finish();
                }
            }



        },splashtime);
    }

}

