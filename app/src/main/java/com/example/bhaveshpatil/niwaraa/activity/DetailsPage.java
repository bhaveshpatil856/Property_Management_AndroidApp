package com.example.bhaveshpatil.niwaraa.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
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
import com.example.bhaveshpatil.niwaraa.fragments.HomePage;
import com.example.bhaveshpatil.niwaraa.fragments.ProfileFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailsPage extends AppCompatActivity implements OnMapReadyCallback {

    TextView TV_project,TV_address,TV_price,TV_desc,textView_coveredArea,textView_carpetArea,textView_bedrooms,textView_bathrooms,textView_balcony,textView_furnishing,textView_floor,textView_possesion,textView_amenities,textView_parking,textView_condition;
    //    private List<PropertyDetailsRepo> details;
    String id,name,sellerType,dealType,propertyType,coveredArea,carpetArea,city,locality,project,address,price,bedrooms,bathrooms,balcony,totalFloor,floorNo,furnishing,status,propertyCondition,Amenities,parking,propertyDesc,number,email,latitude,longitude;
    //   private PropertyDetailsRepo[] data;
    BottomNavigationView nav_contact;

    MapView mapView_display;

    GoogleMap mGoogleMap;

    double Lat,Long;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        id=getIntent().getStringExtra("id");

        cast();

        displayDetails(id);

        nav_contact.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }


    private void cast() {

        TV_project=findViewById(R.id.TV_project);
        TV_address=findViewById(R.id.TV_address);
        TV_price=findViewById(R.id.TV_price);
        TV_desc=findViewById(R.id.TV_desc);
        textView_coveredArea=findViewById(R.id.textView_coveredArea);
        textView_carpetArea=findViewById(R.id.textView_carpetArea);
        textView_bedrooms=findViewById(R.id.textView_bedrooms);
        textView_bathrooms=findViewById(R.id.textView_bathrooms);
        textView_balcony=findViewById(R.id.textView_balcony);
        textView_furnishing=findViewById(R.id.textView_furnishing);
        textView_floor=findViewById(R.id.textView_floor);
        textView_possesion=findViewById(R.id.textView_possesion);
        textView_parking=findViewById(R.id.textView_parking);
        textView_condition=findViewById(R.id.textView_codition);
        textView_amenities=findViewById(R.id.textView_amenities);

        mapView_display=findViewById(R.id.mapView_display);

        nav_contact = findViewById(R.id.nav_contact);


    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.nav_enquire:
                    return true;
                case R.id.nav_call:
                    call();
                    return true;
                case R.id.nav_details:
                    showContact();
                    return true;
            }
            return false;
        }
    };

    private void showContact() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);



        LayoutInflater li=getLayoutInflater();

        View dialogLayout= li.inflate(R.layout.display_contact,null);

        TextView textView_name=dialogLayout.findViewById(R.id.textView_name);
        textView_name.setText(name);

        TextView textView_type=dialogLayout.findViewById(R.id.textView_type);
        textView_type.setText(sellerType);

        TextView textView_number=dialogLayout.findViewById(R.id.textView_number);
        textView_number.setText(number);

        TextView textView_email=dialogLayout.findViewById(R.id.textView_email);
        textView_email.setText(email);



        builder.setPositiveButton("close",null);

        builder.setView(dialogLayout);

        builder.show();





    }

    private void call() {

        if (ContextCompat.checkSelfPermission(DetailsPage.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 111);
        } else {
            logic();
            }
    }

    private void logic() {

        //num = textView_number.getText().toString();
        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:" + number ));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==111)
        {
            if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                logic();
            }
            else {
                Toast.makeText(this, "Deny", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void displayDetails(final String id) {


        final String path="https://bhaveshpatil.000webhostapp.com/displayDetails.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.v("DATA",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    for(int i=0;i < jsonArray.length();i++)
                    {
                        JSONObject property =jsonArray.getJSONObject(i);
                        // PropertyDetailsRepo detailsRepo = new PropertyDetailsRepo();
                        String id= property.getString("id").trim();
                        name = property.getString("name").trim();
                        sellerType = property.getString("sellerType").trim();
                        dealType = property.getString("dealType").trim();
                        propertyType = property.getString("propertyType").trim();
                        coveredArea = property.getString("coveredArea").trim();
                        carpetArea = property.getString("carpetArea").trim();
                        city = property.getString("city").trim();
                        locality = property.getString("locality").trim();
                        project = property.getString("project").trim();
                        address = property.getString("address").trim();
                        price = property.getString("price").trim();
                        bedrooms = property.getString("bedroom").trim();
                        bathrooms = property.getString("bathroom").trim();
                        balcony = property.getString("balcony").trim();
                        totalFloor = property.getString("totalFloor").trim();
                        floorNo = property.getString("floorNo").trim();
                        furnishing = property.getString("furnishing").trim();
                        status = property.getString("status").trim();
                        Amenities = property.getString("Amenities").trim();
                        parking = property.getString("Parking").trim();
                        propertyCondition = property.getString("PropertyCondition").trim();
                        propertyDesc = property.getString("propertyDesc").trim();
                        number = property.getString("number").trim();
                        email = property.getString("email").trim();
                        latitude=property.getString("Latitude").trim();
                        longitude=property.getString("Longitude").trim();
                        //details.add(detailsRepo);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setdata();
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
                Map<String,String> stringMap = new HashMap<String, String>();
                stringMap.put("id",id);
                return stringMap;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    @SuppressLint("SetTextI18n")
    private void setdata() {

        TV_project.setText(project);
        TV_address.setText(address + "," + locality + "\n" + city);
        TV_price.setText(price);
        textView_coveredArea.setText(coveredArea);
        textView_carpetArea.setText(carpetArea);
        textView_bedrooms.setText(bedrooms);
        textView_bathrooms.setText(bathrooms);
        textView_balcony.setText(balcony);
        textView_furnishing.setText(furnishing);
        textView_floor.setText(floorNo + "out of" + totalFloor);
        textView_possesion.setText(status);
        textView_parking.setText(parking);
        textView_condition.setText(propertyCondition);
        textView_amenities.setText(Amenities);
        TV_desc.setText(propertyDesc);

        Lat= Double.parseDouble(latitude);
        Long= Double.parseDouble(longitude);
        MapSet();


    }


    private void MapSet() {


        mapView_display.onCreate(null);
        mapView_display.onResume();
        mapView_display.getMapAsync(this);



    }

    //@Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(this);

        // locationManager.requestLocationUpdates("gps",5000,0,locationListener);

//        textView.setText("coordinates are :\n" + Lat + " " + Long);


        mGoogleMap = googleMap;

//        MapStyleOptions mapStyleOptions=MapStyleOptions.loadRawResourceStyle(getApplicationContext(),GoogleMap.MAP_TYPE_NORMAL);

        mGoogleMap.getMapType();

        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Lat,Long)).title("this").snippet("this is location"));

        CameraPosition cameraPosition= CameraPosition.builder().target(new LatLng(Lat,Long)).zoom(16).bearing(0).tilt(45).build();

        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }


}
