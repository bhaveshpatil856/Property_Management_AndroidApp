package com.example.bhaveshpatil.niwaraa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bhaveshpatil.niwaraa.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterPage extends AppCompatActivity {

    EditText editText_name,editText_email,editText_pass,editText_no;
    Button button_submit;
    CheckBox CB_terms;
    TextView textView_login;

    public static String RURL = "https://bhaveshpatil.000webhostapp.com/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        //Casting
        editText_name=findViewById(R.id.editText_name);
        editText_email=findViewById(R.id.editText_email);
        editText_pass=findViewById(R.id.editText_pass);
        editText_no=findViewById(R.id.editText_no);
        button_submit=findViewById(R.id.button_submit);
        CB_terms=findViewById(R.id.CB_terms);
        textView_login=findViewById(R.id.textView_login);

        //LoginButton
        textView_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),LoginPage.class));
            }
        });

        //Submit registered data
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = editText_name.getText().toString();
                final String email = editText_email.getText().toString();
                final String pass = editText_pass.getText().toString();
                final String number = editText_no.getText().toString();

                if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || number.isEmpty()) {
                    Toast.makeText(RegisterPage.this, "Enter details", Toast.LENGTH_SHORT).show();
                } else {

                    if (CB_terms.isChecked()) {

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, RURL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.contains(" DataInsertedSuccessfully")) {
                                    Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), LoginPage.class));
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> stringMap = new HashMap<>();

                                stringMap.put("uemail", email);

                                stringMap.put("uname", name);

                                stringMap.put("upass", pass);

                                stringMap.put("umobile", number);

                                return stringMap;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        stringRequest.setShouldCache(true);
                        requestQueue.add(stringRequest);

                    } else {
                        Toast.makeText(RegisterPage.this, "Please accept Terms & Conditions", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });




    }
}
