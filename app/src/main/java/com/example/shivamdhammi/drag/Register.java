package com.example.shivamdhammi.drag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Register extends AppCompatActivity {

    Button sso,donor,already;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sso = (Button) findViewById(R.id.id_sso);
        donor = (Button) findViewById(R.id.id_donor);
        already = (Button) findViewById(R.id.id_already);

        sso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("shivam","yeah");
                Intent intent = new Intent(getApplicationContext(), SSOReg.class);

                //Log.d("shivam","yeah1");
                startActivity(intent);
                finish();
               // Log.d("shivam","yeah2");
            }
        });

        donor.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DonorReg.class);
                startActivity(intent);
            }
        });

       already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });




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
