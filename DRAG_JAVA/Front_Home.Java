package com.example.vasu.projectdrag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
// Ye Mayank ki additonal libraries hai.....
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;


public class Home extends AppCompatActivity {

    Button register,login;
   // Mayank's Code ...
    Button b;
    private ConstraintLayout constraintLayout;
    private AnimationDrawable animationDrawable;
    
    //Mayank's Code ends here..

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // Animation Starts 

        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        b=(Button)findViewById(R.id.id_login);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.setShadowLayer(2,5,5,156548);
            }
        });
        
        // Animation ends

        //Backend Code Starts
        register=(Button)findViewById(R.id.id_register);
        login=(Button)findViewById(R.id.id_login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });
    }
}
