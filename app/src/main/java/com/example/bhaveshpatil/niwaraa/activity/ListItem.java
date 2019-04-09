package com.example.bhaveshpatil.niwaraa.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bhaveshpatil.niwaraa.R;
import com.example.bhaveshpatil.niwaraa.adapter.CustomRecyclerviewAdaptor;
import com.example.bhaveshpatil.niwaraa.model.PropertyListRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListItem extends AppCompatActivity {

    RecyclerView RV_list;
    CustomRecyclerviewAdaptor adapter;
    private ArrayList<PropertyListRepo> propertyList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        RV_list=findViewById(R.id.RV_list);

        propertyList= new ArrayList<>();

        showproperty();

    }

    private void showproperty() {

        String PATH="https://bhaveshpatil.000webhostapp.com/displayProperty.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, PATH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.v("DATA",response);
                try {

                    // Log.i("tagconvertstr", "["+response+"]");
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result1");
                    for(int i=0;i < jsonArray.length();i++)
                    {
                        JSONObject Property =jsonArray.getJSONObject(i);
                        PropertyListRepo propertyListRepo = new PropertyListRepo();
                        propertyListRepo.setId(Property.getString("id"));
                        propertyListRepo.setProject(Property.getString("project"));
                        propertyListRepo.setCity(Property.getString("city"));
                        propertyListRepo.setLocality(Property.getString("locality"));
                        propertyListRepo.setAddress(Property.getString("address"));
                        propertyListRepo.setPrice(Property.getString("price"));
                        propertyListRepo.setNumber(Property.getString("number"));
                        propertyListRepo.setImage_name(Property.getString("image_name"));
                        propertyListRepo.setImage_path(Property.getString("image_path"));
                        propertyList.add(propertyListRepo);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter = new CustomRecyclerviewAdaptor(ListItem.this,propertyList);
                RV_list.setLayoutManager(new LinearLayoutManager(ListItem.this));
                RV_list.setHasFixedSize(true);
                RV_list.setAdapter(adapter);




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();


            }

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }


}
