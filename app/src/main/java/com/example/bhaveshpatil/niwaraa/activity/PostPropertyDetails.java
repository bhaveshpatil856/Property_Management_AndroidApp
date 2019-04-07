package com.example.bhaveshpatil.niwaraa.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bhaveshpatil.niwaraa.R;

import java.util.ArrayList;

public class PostPropertyDetails extends AppCompatActivity {

    Spinner spinner_bedrooms,spinner_balcony,spinner_bathrooms,spinner_condition,spinner_totalfloor,spinner_floor,spinner_furnishing,spinner_status;
    EditText editText_desc;
    CheckBox CB_studyRoom,CB_poojaRoom,CB_servantRoom,CB_storeRoom,CB_covered,CB_open,CB_none;
    Button button_next;
    GridView LV_amenities,LV_parking;
    ArrayAdapter<String> adapter9,adapter10;
    String Amenities;
    ArrayList<String> list ;


    String Bedroom_no [] = {"Select","1","2","3","4","5","5+"};
    String floor []= {"Select","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","25+"};
    String furnishing [] ={"Select","Fully Furnished","Partial Furnished","Un-Furnished"};
    String status [] = {"Select","Ready to Move","Under Construction"};
    String condition [] = {"Select","New","Resale"};
    String amenities [] = {"Lift","Intercom","Security","Swimming Pool","Indoor Games","Jogging Track","Gas Pipeline","Power Backup","Club House","Garden"};
    String parking []= {"Covered","Open","None"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_property_details);


        list = new ArrayList<String>();


        spinner_bedrooms=findViewById(R.id.spinner_bedrooms);
        spinner_bathrooms=findViewById(R.id.spinner_bathrooms);
        spinner_balcony=findViewById(R.id.spinner_balcony);
        spinner_totalfloor=findViewById(R.id.spinner_totalfloor);
        spinner_floor=findViewById(R.id.spinner_floor);
        spinner_furnishing=findViewById(R.id.spinner_furnishing);
        spinner_status=findViewById(R.id.spinner_status);
        spinner_condition=findViewById(R.id.spinner_condition);

        editText_desc=findViewById(R.id.editText_desc);

        LV_amenities=findViewById(R.id.LV_amenities);
        LV_parking=findViewById(R.id.LV_parking);

        button_next=findViewById(R.id.button_next);



        ArrayAdapter<String> adapter1= new ArrayAdapter(getApplicationContext(),R.layout.spinner_item,R.id.textView_item, Bedroom_no);
        spinner_bedrooms.setAdapter(adapter1);

        ArrayAdapter<String> adapter2= new ArrayAdapter(getApplicationContext(),R.layout.spinner_item,R.id.textView_item, Bedroom_no);
        spinner_bathrooms.setAdapter(adapter2);

        ArrayAdapter<String> adapter3= new ArrayAdapter(getApplicationContext(),R.layout.spinner_item,R.id.textView_item, Bedroom_no);
        spinner_balcony.setAdapter(adapter3);

        ArrayAdapter<String> adapter4= new ArrayAdapter(getApplicationContext(),R.layout.spinner_item,R.id.textView_item, floor);
        spinner_totalfloor.setAdapter(adapter4);

        ArrayAdapter<String> adapter5= new ArrayAdapter(getApplicationContext(),R.layout.spinner_item,R.id.textView_item, floor);
        spinner_floor.setAdapter(adapter5);

        ArrayAdapter<String> adapter6= new ArrayAdapter(getApplicationContext(),R.layout.spinner_item,R.id.textView_item, furnishing);
        spinner_furnishing.setAdapter(adapter6);

        ArrayAdapter<String> adapter7= new ArrayAdapter(getApplicationContext(),R.layout.spinner_item,R.id.textView_item, status);
        spinner_status.setAdapter(adapter7);

        ArrayAdapter<String> adapter8= new ArrayAdapter(getApplicationContext(),R.layout.spinner_item,R.id.textView_item, condition);
        spinner_condition.setAdapter(adapter8);

        adapter9=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_multiple_choice, amenities);
        LV_amenities.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
        LV_amenities.setAdapter(adapter9);

        adapter10=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_checked, parking);
        LV_parking.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
        LV_parking.setAdapter(adapter10);



        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bedroom=spinner_bedrooms.getSelectedItem().toString();
                String bathroom=spinner_bathrooms.getSelectedItem().toString();
                String balcony=spinner_balcony.getSelectedItem().toString();
                String totalFloor=spinner_totalfloor.getSelectedItem().toString();
                String floorNo=spinner_floor.getSelectedItem().toString();
                String furnishing=spinner_furnishing.getSelectedItem().toString();
                String status=spinner_status.getSelectedItem().toString();
                String condition=spinner_condition.getSelectedItem().toString();

                String property_desc=editText_desc.getText().toString();

                SparseBooleanArray checked = LV_amenities.getCheckedItemPositions();
                ArrayList<String> selectedItems = new ArrayList<String>();
                for (int i = 0; i < checked.size(); i++) {
                    // Item position in adapter
                    int position = checked.keyAt(i);
                    // Add sport if it is checked i.e.) == TRUE!
                    if (checked.valueAt(i))
                        selectedItems.add(adapter9.getItem(position));
                }


                Amenities = "";

                for (int i = 0; i < selectedItems.size(); i++) {
                    Amenities = Amenities + selectedItems.get(i)+"\n";
                }

                Toast.makeText(getApplicationContext(),Amenities,Toast.LENGTH_LONG).show();


                SparseBooleanArray checkedparking = LV_parking.getCheckedItemPositions();
                ArrayList<String> selectedItemsParking = new ArrayList<String>();
                for (int i = 0; i < checkedparking.size(); i++) {
                    // Item position in adapter
                    int position = checkedparking.keyAt(i);
                    // Add sport if it is checked i.e.) == TRUE!
                    if (checkedparking.valueAt(i))
                        selectedItemsParking.add(adapter10.getItem(position));
                }

                String Parking = "";

                for (int i = 0; i < selectedItemsParking.size(); i++) {
                    Parking = Parking + selectedItemsParking.get(i)+"\n";
                }




                if(bedroom.contains("Select")||bathroom.contains("Select")||balcony.contains("Select")||
                        totalFloor.contains("Select")||floorNo.contains("Select")||furnishing.contains("Select")||status.contains("Select")||
                        condition.contains("Select")||property_desc.isEmpty()||Amenities.isEmpty()||Parking.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"enter details",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("DETAILS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit=sharedPreferences.edit();

                    edit.putString("bedroom",bedroom);
                    edit.putString("bathroom",bathroom);
                    edit.putString("balcony",balcony);
                    edit.putString("totalFloor",totalFloor);
                    edit.putString("floorNo",floorNo);
                    edit.putString("furnishing",furnishing);
                    edit.putString("status",status);
                    edit.putString("condition",condition);
                    edit.putString("propertyDesc",property_desc);
                    edit.putString("Amenities", Amenities);
                    edit.putString("Parking",Parking);

                    edit.apply();

                    startActivity(new Intent(getApplicationContext(),ContactDetails.class));

                }



            }
        });




    }
}
