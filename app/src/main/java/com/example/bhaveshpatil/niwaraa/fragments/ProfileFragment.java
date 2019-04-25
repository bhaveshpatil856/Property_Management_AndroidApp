package com.example.bhaveshpatil.niwaraa.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bhaveshpatil.niwaraa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    TextView umail,uno,uname;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        umail=view.findViewById(R.id.umail);
        uno=view.findViewById(R.id.uno);
        uname=view.findViewById(R.id.uname);

        SharedPreferences shared = getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);

        String id=shared.getString("id","");
        String name=shared.getString("name","");
        String email=shared.getString("email","");
        String pass=shared.getString("pass","");
        String contact=shared.getString("contact","");

        uname.setText(name);
        umail.setText(email);
        uno.setText(contact);


                return view;
    }

}
