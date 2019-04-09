package com.example.bhaveshpatil.niwaraa.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.bhaveshpatil.niwaraa.MainActivity;
import com.example.bhaveshpatil.niwaraa.R;

public class StatusCheck extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_check);

        SharedPreferences setting = getSharedPreferences("Check_status", Context.MODE_PRIVATE);

        //getting the value from hasLoggedIn value if there is no value present then the default value would be false

        boolean hasLoggedIn = setting.getBoolean("hasLoggedIn",false);

   //     boolean hasStudentLoggedIn = setting_student.getBoolean("hasStudentLoggedIn",false);

        if(hasLoggedIn)
        {
            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            StatusCheck.this.finish();
        }
        else
        {
            Intent intent= new Intent(getApplicationContext(), SplashScreen.class);
            startActivity(intent);
            StatusCheck.this.finish();

        }

    }
}
