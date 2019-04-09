package com.example.bhaveshpatil.niwaraa.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

                        if(response.contains("invalid user"))
                        {
                            Toast.makeText(LoginPage.this, "Invalid user! \n try again", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                            Log.v("DATA", response);
                            try {
                                JSONObject jsonObjectS = new JSONObject(response);
                                JSONArray jsonArray = jsonObjectS.getJSONArray("result");
                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                String id = jsonObject1.getString("id");
                                //Toast.makeText(MainActivity.this, "the id is "+id, Toast.LENGTH_SHORT).show();
                                String name = jsonObject1.getString("uname");
                                String email = jsonObject1.getString("uemail");
                                String password = jsonObject1.getString("upass");
                                String contact = jsonObject1.getString("umobile");



                                SharedPreferences shared = getSharedPreferences("Mypref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = shared.edit();
                                editor.putString("id",id);
                                editor.putString("name",name);
                                editor.putString("email",email);
                                editor.putString("pass",password);
                                editor.putString("contact",contact);
                                editor.apply();

                                // Intent login = new Intent(getApplicationContext(),Profilepage.class);
                                //startActivity(login);
                                //to prevent back click from user



                                //now if the user logs in successfully we must store the result

                                SharedPreferences setting = getSharedPreferences("Check_status", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor1 = setting.edit();
                                editor1.putBoolean("hasLoggedIn",true);
                                editor1.apply();



                                startActivity(new Intent(getApplicationContext(),MainActivity.class));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), "opps!", Toast.LENGTH_SHORT).show();

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
