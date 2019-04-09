package com.example.bhaveshpatil.niwaraa.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bhaveshpatil.niwaraa.R;
import com.example.bhaveshpatil.niwaraa.activity.EMICalcutator;
import com.example.bhaveshpatil.niwaraa.activity.SplashScreen;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    TextView TV_emiCalculator,TV_logout;
    AlertDialog alertdialog;


    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_menu, container, false);

       TV_emiCalculator=view.findViewById(R.id.TV_emiCalculator);
       TV_logout=view.findViewById(R.id.TV_logout);

       TV_emiCalculator.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               startActivity(new Intent(getActivity(),EMICalcutator.class));

           }
       });

       TV_logout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               alertdialog = new AlertDialog.Builder(getActivity()).create();

               alertdialog.setTitle("Logout");
               alertdialog.setMessage("Are you sure ! logout ?");
               alertdialog.setCancelable(false);
               alertdialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                       alertdialog.dismiss();

                   }
               });

               alertdialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       SharedPreferences shared = getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
                       SharedPreferences.Editor editor = shared.edit();
                       editor.clear();
                       editor.commit();
                       Intent intent = new Intent(getActivity(), SplashScreen.class);
                       startActivity(intent);
                       //if the user is logged out then change the following

                       SharedPreferences setting = getActivity().getSharedPreferences("Check_status", Context.MODE_PRIVATE);
                       SharedPreferences.Editor editor1 = setting.edit();
                       editor1.putBoolean("hasLoggedIn",false);
                       editor1.apply();
                       getActivity().finish();
                   }
               });
               alertdialog.show();


           }
       });

        return view;
    }

}
