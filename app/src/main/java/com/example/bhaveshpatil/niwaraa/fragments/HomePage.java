package com.example.bhaveshpatil.niwaraa.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bhaveshpatil.niwaraa.R;
import com.example.bhaveshpatil.niwaraa.activity.ListItem;
import com.example.bhaveshpatil.niwaraa.adapter.newPropertyAdapter;
import com.example.bhaveshpatil.niwaraa.adapter.topPropertyAdapter;
import com.example.bhaveshpatil.niwaraa.model.PropertyListRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePage extends Fragment {

    TextView textView_new;

    RecyclerView recycler_new,recycler_ownersProp;
    private ArrayList<PropertyListRepo> propertyList,propertyOwnList;
  //  newPropertyAdapter adapter;


    public HomePage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home_page, container, false);



        textView_new=view.findViewById(R.id.textView_new);
        recycler_new=view.findViewById(R.id.recycler_new);
        recycler_ownersProp=view.findViewById(R.id.recycler_ownersProp);

        propertyList= new ArrayList<>();
        propertyOwnList=new ArrayList<>();

        showNewProperty();

        showOwnersProperty();

        textView_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(),ListItem.class));

            }
        });

        return view;
    }




    private void showNewProperty() {

        String PATH="https://bhaveshpatil.000webhostapp.com/displayNewProperty.php";

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

               topPropertyAdapter adapter = new topPropertyAdapter(getActivity(),propertyList);
               // LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
              //  recycler_new.setLayoutManager(layoutManager);
               // recycler_new.setHasFixedSize(true);
               // recycler_new.setAdapter(adapter);

                recycler_new.setLayoutManager(new GridLayoutManager(getActivity(),3, LinearLayoutManager.HORIZONTAL,false));
                recycler_new.setHasFixedSize(true);
                recycler_new.setAdapter(adapter);




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();


            }

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }

    private void showOwnersProperty() {
        String PATH="https://bhaveshpatil.000webhostapp.com/displayOwnProperties.php";


        StringRequest stringRequest=new StringRequest(Request.Method.POST, PATH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.v("DATA",response);
                try {

                    // Log.i("tagconvertstr", "["+response+"]");
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
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
                        propertyOwnList.add(propertyListRepo);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                newPropertyAdapter adapter = new newPropertyAdapter(getActivity(),propertyOwnList);
                LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                recycler_ownersProp.setLayoutManager(layoutManager);
                recycler_ownersProp.setHasFixedSize(true);
                recycler_ownersProp.setAdapter(adapter);




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();


            }

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }


}
