package com.example.bhaveshpatil.niwaraa.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bhaveshpatil.niwaraa.R;

public class EMICalcutator extends AppCompatActivity {


    EditText principal,interest,years,emi,total_interest;
    Button btn_calculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emicalcutator);


        principal=findViewById(R.id.principal);
        interest=findViewById(R.id.interest);
        years=findViewById(R.id.years);
        emi=findViewById(R.id.emi);
        total_interest=findViewById(R.id.total_interest);

        btn_calculate=findViewById(R.id.btn_calculate);

        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GET TEXT AND CONVERT TO STRING
                String str1=principal.getText().toString();
                String str2=interest.getText().toString();
                String str3=years.getText().toString();

                if (TextUtils.isEmpty(str1))
                {
                    principal.setError("Enter Principle Amount");
                    principal.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(str2))
                {
                    interest.setError("Enter Intrest Amount");
                    interest.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(str3))
                {
                    years.setError("Enter Years");
                    years.requestFocus();
                    return;
                }

                //CONVERT STRING TO FLOAT
                float principal=Float.parseFloat(str1);
                float interest=Float.parseFloat(str2);
                float years=Float.parseFloat(str3);


                //IMPLEMENT METHODS
                float Princ=calPrinc(principal);
                float Rate=calInt(interest);
                float Month=calMonth(years);
                float Dividend=calDiv(Rate,Month);
                float DiviTotal=calTotal(Princ,Rate,Dividend);
                float Divider=calDivider(Dividend);
                float Emi=calEmi(DiviTotal,Divider);
                float TotalAmt=calTotalAmt(Emi,Month);
                float TotalInterest=calTotalInt(TotalAmt,Princ);

                //SET TEXT TO EMI AND TOTAL INTEREST
                emi.setText(String.valueOf(Emi));
                total_interest.setText(String.valueOf(TotalInterest));

            }
        });
    }


    private float calPrinc(float principal) {

        return (float)(principal);
    }

    private float calInt(float interest) {
        return (float)(interest/12/100);
    }

    private float calMonth(float years) {
        return (float)(years*12);
    }

    private float calDiv(float Rate, float Month) {
        return (float)(Math.pow(1+Rate,Month));
    }

    private float calTotal(float princ, float rate, float dividend) {
        return (float)(princ*rate*dividend);
    }


    private float calDivider(float dividend) {
        return (float)(dividend-1);
    }

    private float calEmi(float diviTotal, float divider) {
        return (float)(diviTotal/divider);
    }

    private float calTotalAmt(float emi, float month) {
        return (float) (emi*month);
    }

    private float calTotalInt(float totalAmt, float princ) {
        return (float)(totalAmt-princ);
    }
    }

