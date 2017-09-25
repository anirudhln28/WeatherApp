package com.example.weatherapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anirudh on 10/9/2016.
 */
public class FavoriteAdapter extends ArrayAdapter<FavObject> {

    Context context;
    int resource;
    ArrayList<FavObject> weatherList;
    int i=0;

    public FavoriteAdapter(Context context, int resource, ArrayList<FavObject> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        weatherList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Vholder holder;
        if(convertView==null){

            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(resource,parent,false);
            holder=new Vholder();
           // holder.place=(TexView)convertView.findViewById(R.id.imageView2);
            holder.place=(TextView)convertView.findViewById(R.id.tcstate);
            holder.date=(TextView)convertView.findViewById(R.id.txtdate);
            holder.temp=(TextView)convertView.findViewById(R.id.txttemp);
            convertView.setTag(holder);
        }
        holder=(Vholder)convertView.getTag();
        TextView date1=holder.date;
        TextView place1=holder.place;
        TextView temp1=holder.temp;

            String city = weatherList.get(position).city;
            String state = weatherList.get(position).state;
            String temp = weatherList.get(position).temp;
            String date = weatherList.get(position).date;
            place1.setText(city + "," + state);
            date1.setText("Updated on: "+date);
            temp1.setText(temp);
           // i=i+4;

//        if(!weatherList.get(position).colorofrow)
//        {
//            convertView.setBackgroundColor(Color.parseColor("Blue"));
//        }

        return convertView;
        //return super.getView(position, convertView, parent);

    }
    static class Vholder{;
        TextView place;
        TextView temp;
        TextView date;

    }
}

