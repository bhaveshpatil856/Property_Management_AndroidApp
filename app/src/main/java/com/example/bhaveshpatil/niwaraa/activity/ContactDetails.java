package com.example.bhaveshpatil.niwaraa.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bhaveshpatil.niwaraa.MainActivity;
import com.example.bhaveshpatil.niwaraa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import net.gotev.uploadservice.MultipartUploadRequest;

import java.net.MalformedURLException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ContactDetails extends AppCompatActivity {

    EditText editText_email,editText_contact,editText_name;
    Button button_generateOtp;
    String number;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    FirebaseAuth auth;

    String verificationCode,code;

    EditText editTextDialogUserInput;


    public static String UPLOAD_URL = "https://bhaveshpatil.000webhostapp.com/uploadProperty.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        //casting
        editText_email=findViewById(R.id.editText_email);
        editText_contact=findViewById(R.id.editText_contact);
        editText_name=findViewById(R.id.editText_name);
        button_generateOtp=findViewById(R.id.button_generateOtp);


        StartFirebaseLogin();



        //button for generate otp
        button_generateOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = editText_contact.getText().toString().trim();
                String email = editText_email.getText().toString();
                String name = editText_name.getText().toString();

                if (number.isEmpty() || email.isEmpty()||name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "fill details", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DETAILS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("email", email);
                    edit.putString("number", number);
                    edit.putString("name",name);
                    edit.apply();

                    sendOTP();



                    //   upload();

                    // create custom dialog box
                    final Dialog dialog= new Dialog(ContactDetails.this);

                    // Include dialog.xml file
                    dialog.setContentView(R.layout.propmt);
                    // Set dialog title
                    dialog.setTitle("Verify Otp");

                    dialog.show();

                    Button button_cnfrm=dialog.findViewById(R.id.button_cnfrm);

                    button_cnfrm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            editTextDialogUserInput=findViewById(R.id.editTextDialogUserInput);


                            verifyCode();



                            //   TextView text = (TextView) dialog.findViewById(R.id.textView_d1);
                            // text.setText("Enter Otp & click Verify");

                            //  EditText editTextDialogUserInput= dialog.findViewById(R.id.editTextDialogUserInput);
                            //editTextDialogUserInput.getText();

                            upload();



                            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                            //intent.putExtra("id",user.getId());

                            startActivity(intent);



                        }
                    });






                    //   startActivity(new Intent(getApplicationContext(),DetailsPage.class));
                }
            }

        });

    }

    private void verifyCode() {

        code=editTextDialogUserInput.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, code);
        SigninWithPhone(credential);

    }

    private void sendOTP() {

        String phoneNumber=number;

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
               "+91" + phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallback);        // OnVerificationStateChangedCallbacks


    }


    private void upload() {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DETAILS", Context.MODE_PRIVATE);

        String sellerType = sharedPreferences.getString("sellerType", "");
        String dealType = sharedPreferences.getString("dealType", "");
        String propertyType = sharedPreferences.getString("propertyType", "");
        String coveredArea = sharedPreferences.getString("coveredArea", "");
        String carpetArea = sharedPreferences.getString("carpetArea", "");
        String city = sharedPreferences.getString("city", "");
        String locality = sharedPreferences.getString("locality", "");
        String project = sharedPreferences.getString("project", "");
        String address = sharedPreferences.getString("address", "");
        String price = sharedPreferences.getString("price", "");
        String bedroom = sharedPreferences.getString("bedroom", "");
        String bathroom = sharedPreferences.getString("bathroom", "");
        String balcony = sharedPreferences.getString("balcony", "");
        String totalFloor = sharedPreferences.getString("totalFloor", "");
        String floorNo = sharedPreferences.getString("floorNo", "");
        String Furnishing = sharedPreferences.getString("furnishing", "");
        String Status = sharedPreferences.getString("status", "");
        String Amenities = sharedPreferences.getString("Amenities", "");
        String Parking = sharedPreferences.getString("Parking", "");
        String condition = sharedPreferences.getString("condition", "");
        String propertyDesc = sharedPreferences.getString("propertyDesc", "");
        String number = sharedPreferences.getString("number", "");
        String email = sharedPreferences.getString("email", "");
        String name = sharedPreferences.getString("name", "");
        String latitude=sharedPreferences.getString("Latitude","");
        String longitude=sharedPreferences.getString("Longitude","");

        Toast.makeText(getApplicationContext(), Amenities, Toast.LENGTH_LONG).show();

        try {
            String uploadid = UUID.randomUUID().toString();

            new MultipartUploadRequest(getApplicationContext(), uploadid, UPLOAD_URL)
                    .addParameter("name", name)
                    .addParameter("sellerType", sellerType)
                    .addParameter("dealType", dealType)
                    .addParameter("propertyType", propertyType)
                    .addParameter("coveredArea", coveredArea)
                    .addParameter("carpetArea", carpetArea)
                    .addParameter("city", city)
                    .addParameter("locality", locality)
                    .addParameter("project", project)
                    .addParameter("address", address)
                    .addParameter("price", price)
                    .addParameter("bedroom", bedroom)
                    .addParameter("bathroom", bathroom)
                    .addParameter("balcony", balcony)
                    .addParameter("totalFloor", totalFloor)
                    .addParameter("floorNo", floorNo)
                    .addParameter("furnishing", Furnishing)
                    .addParameter("status", Status)
                    .addParameter("Amenities", Amenities)
                    .addParameter("Parking", Parking)
                    .addParameter("PropertyCondition", condition)
                    .addParameter("propertyDesc", propertyDesc)
                    .addParameter("number", number)
                    .addParameter("email", email)
                    .addParameter("Latitude",latitude)
                    .addParameter("Longitude",longitude)
                    .setMaxRetries(2)
                    .startUpload();

            Toast.makeText(getApplicationContext(), uploadid ,Toast.LENGTH_LONG).show();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }



    private void StartFirebaseLogin() {
        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(getApplicationContext(),"verification completed",Toast.LENGTH_SHORT).show();
                upload();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                Toast.makeText(getApplicationContext(),"Code sent",Toast.LENGTH_SHORT).show();
            }
        };
    }



    protected void SigninWithPhone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Verified no.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),"Incorrect OTP",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
