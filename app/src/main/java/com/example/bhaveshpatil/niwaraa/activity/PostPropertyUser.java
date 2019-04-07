package com.example.bhaveshpatil.niwaraa.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bhaveshpatil.niwaraa.R;

public class PostPropertyUser extends AppCompatActivity {

    RadioGroup RG_sellerType;
    RadioButton radioButton_owner,radioButton_agent,radioButton_builder;

    Button button_nxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_property_user);

        RG_sellerType=findViewById(R.id.RG_sellerType);
        radioButton_agent=findViewById(R.id.radioButton_agent);
        radioButton_builder=findViewById(R.id.radioButton_builder);
        radioButton_owner=findViewById(R.id.radioButton_owner);

        button_nxt=findViewById(R.id.button_nxt);

        button_nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int sellerType=RG_sellerType.getCheckedRadioButtonId();
                RadioButton seller=findViewById(sellerType);

                if (RG_sellerType.getCheckedRadioButtonId()== -1) {
                    Toast.makeText(getApplicationContext(), "Select Option", Toast.LENGTH_SHORT).show();
                }       else {

                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DETAILS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("sellerType", seller.getText().toString());
                    edit.commit();

                    startActivity(new Intent(getApplicationContext(),PostPropertyType.class));
                }
// Toast.makeText(getActivity(),seller.getText().toString(),Toast.LENGTH_LONG).show();

            }
        });


    }
}
