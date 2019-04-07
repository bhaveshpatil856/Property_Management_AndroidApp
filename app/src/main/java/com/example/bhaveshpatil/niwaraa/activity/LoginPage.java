package com.example.bhaveshpatil.niwaraa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bhaveshpatil.niwaraa.MainActivity;
import com.example.bhaveshpatil.niwaraa.R;
import com.example.bhaveshpatil.niwaraa.fragments.HomePage;

import java.util.HashMap;
import java.util.Map;

public class LoginPage extends AppCompatActivity {

    TextView textView_back,textView_login,textView_forgetPass,textView_register;
    EditText editText_username,editText_password;
    ImageView imageView_profile;

    public static String LURL ="https://bhaveshpatil.000webhostapp.com/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //casting
        textView_login=findViewById(R.id.textView_login);
        textView_back=findViewById(R.id.textView_back);
        textView_forgetPass=findViewById(R.id.textView_forgetPass);
        textView_register=findViewById(R.id.textView_register);
        editText_username=findViewById(R.id.editText_username);
        editText_password=findViewById(R.id.editText_password);
        imageView_profile=findViewById(R.id.imageView_profile);


        //back to homePage
        textView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        //register button
        textView_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),RegisterPage.class));

            }
        });

        //Login
        textView_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String user=editText_username.getText().toString();
                final String pass=editText_password.getText().toString();

                if(user.isEmpty() || pass.isEmpty())
                {
                    Toast.makeText(LoginPage.this, "Fill Details", Toast.LENGTH_SHORT).show();
                }

                StringRequest stringRequest=new StringRequest(Request.Method.POST, LURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.contains("success"))
                        {
                            Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }

                        if (response.contains("invalid user"))
                        {
                            Toast.makeText(getApplicationContext(), "Invalid User! try again", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),LoginPage.class));
                            finish();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> stringMap = new HashMap<>();

                        stringMap.put("uemail",user);

                        stringMap.put("upass",pass);

                        return stringMap;

                    }
                };

                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                stringRequest.setShouldCache(false);
                requestQueue.add(stringRequest);




            }
        });

        //forgetPassword
        textView_forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"nantar karu chalel ka",Toast.LENGTH_LONG).show();
            }
        });



    }
}
