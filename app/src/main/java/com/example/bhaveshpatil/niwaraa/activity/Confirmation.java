package com.example.bhaveshpatil.niwaraa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bhaveshpatil.niwaraa.R;

public class Confirmation extends AppCompatActivity {

//    FirebaseAuth auth;
    EditText editText_otp;
    TextView textView_resend;
//    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    Button button_verify, button_finish;
    private String verificationCode;
    String code;
    String number;

    public static String UPLOAD_URL = "https://bhaveshpatil.000webhostapp.com/uploadProperty.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        editText_otp = findViewById(R.id.editText_otp);
        textView_resend = findViewById(R.id.textView_resend);
        button_finish = findViewById(R.id.button_finish);
        button_verify = findViewById(R.id.button_verify);

        button_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // upload();

                startActivity(new Intent(getApplicationContext(), DetailsPage.class));
            }
        });

    }
}
