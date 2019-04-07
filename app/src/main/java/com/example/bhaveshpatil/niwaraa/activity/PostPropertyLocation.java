package com.example.bhaveshpatil.niwaraa.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhaveshpatil.niwaraa.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class PostPropertyLocation extends AppCompatActivity implements OnMapReadyCallback {

    protected LocationManager locationManager;
    protected LocationListener locationListener;

    protected Context context;
    TextView txtLat;
    String lat;
    String provider;
    String latitude, longitude;
    protected boolean gps_enabled, network_enabled;

    // TextView TV_sell,TV_rent,TV_pg;
    AutoCompleteTextView TV_city;
    EditText editText_address, editText_locality, editText_project, editText_coveredArea, editText_carpetArea, editText_price;
    Spinner spinner_propertyType;
    Button button_net;
    ImageView imageView_photos;

    String Latitude;
    String Longitude;

    double Lat, Long;
    public static final int RequestPermissionCode = 1;

    Intent intent;

    Bitmap bitmap;

    boolean check = true;

    String GetImageNameFromEditText;

    String ImageNameFieldOnServer = "image_name";

    String ImagePathFieldOnServer = "image_path";

    String ImageUploadPathOnSever = "https://bhaveshpatil.000webhostapp.com/capture_img_upload_to_server.php";

    ImageButton addPhotos;

    String propertyType, city, coveredarea, carpetarea, address, locality, project, price;

    MapView mapView_upload;

    GoogleMap mGoogleMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_property_location);

        TV_city = findViewById(R.id.TV_city);

        editText_address = findViewById(R.id.editText_address);
        editText_locality = findViewById(R.id.editText_locality);
        editText_project = findViewById(R.id.editText_project);
        editText_coveredArea = findViewById(R.id.editText_coveredArea);
        editText_carpetArea = findViewById(R.id.edittext_carpetArea);
        editText_price = findViewById(R.id.edittext_price);

        spinner_propertyType = findViewById(R.id.spinner_propertyType);

        button_net = findViewById(R.id.button_net);

        imageView_photos = findViewById(R.id.imageView_photos);
        addPhotos = findViewById(R.id.addPhotos);

        mapView_upload = findViewById(R.id.mapView_upload);

        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        final String[] prop_type = {"Select Property Type", "Flat", "House", "Villa", "Office Space", "Pent House", "shop"};
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, R.id.textView_item, prop_type);
        spinner_propertyType.setAdapter(arrayAdapter1);

        EnableRuntimePermissionToAccessCamera();

//        MapSet();

        //next button
        button_net.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                address = editText_address.getText().toString();
                locality = editText_locality.getText().toString();
                project = editText_project.getText().toString();
                carpetarea = editText_carpetArea.getText().toString();
                coveredarea = editText_coveredArea.getText().toString();
                price = editText_price.getText().toString();
                propertyType = spinner_propertyType.getSelectedItem().toString();
                city = TV_city.getText().toString();

                if (address.isEmpty() || locality.isEmpty() || project.isEmpty() || carpetarea.isEmpty() || coveredarea.isEmpty() || price.isEmpty() || propertyType.isEmpty() || city.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "fill details", Toast.LENGTH_SHORT).show();

                } else {

                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DETAILS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("propertyType", propertyType);
                    edit.putString("coveredArea", coveredarea);
                    edit.putString("carpetArea", carpetarea);
                    edit.putString("city", city);
                    edit.putString("locality", locality);
                    edit.putString("project", project);
                    edit.putString("address", address);
                    edit.putString("price", price);
                    edit.putString("Latitude", Latitude);
                    edit.putString("Longitude", Longitude);
                    edit.apply();


                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(), PostPropertyDetails.class));

                }


                GetImageNameFromEditText = editText_project.getText().toString();

                ImageUploadToServerFunction();

            }
        });

        addPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                configureButton();

                intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, 7);

                MapSet();

            }
        });


    }

    // Star activity for result method to Set captured image on image view after click.
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == RESULT_OK) {

            Uri uri = data.getData();

            // Adding captured image in bitmap.

            bitmap = (Bitmap) data.getExtras().get("data");


            // adding captured image in imageview.
            imageView_photos.setImageBitmap(bitmap);

        }

    }

    // Upload captured image online on server function.
    public void ImageUploadToServerFunction() {

        ByteArrayOutputStream byteArrayOutputStreamObject;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();

        // Converting bitmap image to jpeg format, so by default image will upload in jpeg format.
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                Log.v("error", String.valueOf(string1));

                // Printing uploading success message coming from server on android app.
                Toast.makeText(getApplicationContext(), string1, Toast.LENGTH_LONG).show();

                // Setting image as transparent after done uploading.
                imageView_photos.setImageResource(android.R.color.transparent);

            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String, String> HashMapParams = new HashMap<String, String>();

                HashMapParams.put(ImageNameFieldOnServer, GetImageNameFromEditText);

                HashMapParams.put(ImagePathFieldOnServer, ConvertImage);

                String FinalData = imageProcessClass.ImageHttpRequest(ImageUploadPathOnSever, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public class ImageProcessClass {

        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {


            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject;
                BufferedReader bufferedReaderObject;
                int RC;

                url = new URL(requestURL);

                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null) {

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();

                Log.v("error", String.valueOf(e));
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            StringBuilder stringBuilderObject;

            stringBuilderObject = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

                if (check)

                    check = false;
                else
                    stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilderObject.append("=");

                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilderObject.toString();
        }

    }


    private void EnableRuntimePermissionToAccessCamera() {

        if ((ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.CAMERA
                }, 10);
            }
        } else {

            MapSet();

            configureButton();
        }
    }


    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case 10:
                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED)
                    return;


        }
    }

    private void configureButton() {

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Lat = location.getLatitude();
                Long = location.getLongitude();

                Latitude = String.valueOf(location.getLatitude());
                Longitude = String.valueOf(location.getLongitude());

                // Toast.makeText(getApplicationContext(), Latitude, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);

        Toast.makeText(getApplicationContext(), Latitude, Toast.LENGTH_SHORT).show();

        //Toast.makeText(getActivity(), Latitude + "\n" + Longitude, Toast.LENGTH_SHORT).show();


    }


    private void MapSet() {

        mapView_upload.onCreate(null);
        mapView_upload.onResume();
        mapView_upload.getMapAsync(this);

    }


    //@Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);

        //        textView.setText("coordinates are :\n" + Lat + " " + Long);


        mGoogleMap = googleMap;

//        MapStyleOptions mapStyleOptions=MapStyleOptions.loadRawResourceStyle(getApplicationContext(),GoogleMap.MAP_TYPE_NORMAL);

        mGoogleMap.getMapType();

        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Lat,Long)).title("this").snippet("this is location"));

        CameraPosition cameraPosition= CameraPosition.builder().target(new LatLng(Lat,Long)).zoom(16).bearing(0).tilt(45).build();

        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }


}
