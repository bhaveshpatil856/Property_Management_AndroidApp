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

public class PostPropertyType extends AppCompatActivity {

    RadioGroup RG_dealType;
    RadioButton radioButton_sell, radioButton_rent, radioButton_pg;
    Button button_next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_property_type);

        RG_dealType = findViewById(R.id.RG_dealType);
        radioButton_sell = findViewById(R.id.radioButton_sell);
        radioButton_rent = findViewById(R.id.radioButton_rent);
        radioButton_pg = findViewById(R.id.radioButton_pg);

        button_next = findViewById(R.id.button_next);


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int dealType = RG_dealType.getCheckedRadioButtonId();
                RadioButton deal = findViewById(dealType);

                if (RG_dealType.getCheckedRadioButtonId()== -1)
                {
                    Toast.makeText(getApplicationContext(),"select option",Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DETAILS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("dealType", deal.getText().toString());
                    edit.commit();

                    startActivity(new Intent(getApplicationContext(),PostPropertyLocation.class));

                }
            }
        });


    }
}
