package com.company.demoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UserInfo extends AppCompatActivity {
    TextView t1,t2,t3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        t1 = findViewById(R.id.name);
        t2 =  findViewById(R.id.email);
        t3 =  findViewById(R.id.creA);

        Intent i = getIntent();
        String n = i.getStringExtra("Name");
        String em = i.getStringExtra("Email");
        String cr = i.getStringExtra("Created");

        t1.setText("Name : "+n);
        t2.setText("E-mail : "+em);
        t3.setText("Created At : "+cr);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(),HomeScreen.class);
        startActivity(i);
        finish();
    }
}