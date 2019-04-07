package com.example.bhaveshpatil.niwaraa.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bhaveshpatil.niwaraa.R;
import com.example.bhaveshpatil.niwaraa.activity.DetailsPage;
import com.example.bhaveshpatil.niwaraa.model.PropertyListRepo;

import java.util.ArrayList;

public class CustomRecyclerviewAdaptor extends RecyclerView.Adapter<CustomRecyclerviewAdaptor.ViewHolder> {

    private Context context;
    private PropertyListRepo[] data;

    public CustomRecyclerviewAdaptor(Context context, ArrayList<PropertyListRepo> data) {
        this.context = context;
        this.data = data.toArray(new PropertyListRepo[0]);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater l = LayoutInflater.from(parent.getContext());
        View v = l.inflate(R.layout.item_list,parent,false);
        //now new view holder is created
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final PropertyListRepo user=data[position];

        holder.textView_name.setText(user.getProject());
        holder.textView_desc.setText( user.getCity());
        holder.textView_price.setText(user.getPrice());
        //holder.duration.setText(user.getCourseDuration());
        //holder.description.setText(user.getCourseDetail());
        //to bind the image view we will use glide library
        // Glide.with(holder.imguser.getContext()).load(user.getUrl()).into(holder.imguser);
        //setting up an onclick listener

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, DetailsPage.class);
                intent.putExtra("id",user.getId());

                context.startActivity(intent);

            }
        });




    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView_name;
        TextView textView_desc;
        TextView textView_price;
        LinearLayout parent;
        public ViewHolder(View itemView) {
            super(itemView);

            parent=itemView.findViewById(R.id.parent);
            textView_name= itemView.findViewById(R.id.textView_name);
            textView_desc=itemView.findViewById(R.id.textView_desc);
            textView_price=itemView.findViewById(R.id.textView_price);

        }
    }
}
