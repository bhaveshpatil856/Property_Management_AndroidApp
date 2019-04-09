package com.example.bhaveshpatil.niwaraa.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bhaveshpatil.niwaraa.R;
import com.example.bhaveshpatil.niwaraa.activity.DetailsPage;
import com.example.bhaveshpatil.niwaraa.model.PropertyListRepo;

import java.util.ArrayList;

public class newPropertyAdapter extends RecyclerView.Adapter<newPropertyAdapter.ViewHolder> {

    private Context context;
    private PropertyListRepo[] data;

    public newPropertyAdapter(Context context, ArrayList<PropertyListRepo> data) {
        this.context = context;
        this.data = data.toArray(new PropertyListRepo[0]);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater l = LayoutInflater.from(parent.getContext());
        View v = l.inflate(R.layout.item_top,parent,false);
        //now new view holder is created
        return new newPropertyAdapter.ViewHolder(v);}

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        final PropertyListRepo user=data[position];

        holder.textView_projectName.setText(user.getProject());
        holder.textView_projectCity.setText( user.getCity());
        holder.textView_proPrice.setText(user.getPrice());
        //holder.duration.setText(user.getCourseDuration());
        //holder.description.setText(user.getCourseDetail());
        //to bind the image view we will use glide
        Glide.with(holder.imageView_top.getContext()).load(user.getImage_path()).into(holder.imageView_top);

        //setting up an onclick listener

       holder.CV_parent.setOnClickListener(new View.OnClickListener() {
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



        return data.length ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView_projectName;
        TextView textView_projectCity;
        TextView textView_proPrice;

        ImageView imageView_top;

        LinearLayout CV_parent;

        public ViewHolder(View itemView) {
            super(itemView);

            CV_parent=itemView.findViewById(R.id.CV_parent);
            textView_projectName= itemView.findViewById(R.id.textView_projectName);
            textView_projectCity=itemView.findViewById(R.id.textView_projectCity);
            textView_proPrice=itemView.findViewById(R.id.textView_proPrice);
            imageView_top=itemView.findViewById(R.id.imageView_top);


        }
    }
}
