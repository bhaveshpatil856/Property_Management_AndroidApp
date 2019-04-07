package com.example.bhaveshpatil.niwaraa.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bhaveshpatil.niwaraa.R;
import com.example.bhaveshpatil.niwaraa.activity.EMICalcutator;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    TextView TV_emiCalculator;


    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_menu, container, false);

       TV_emiCalculator=view.findViewById(R.id.TV_emiCalculator);

       TV_emiCalculator.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               startActivity(new Intent(getActivity(),EMICalcutator.class));

           }
       });

        return view;
    }

}
