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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anirudhln on 10/8/2016.
 */
public class WeatherArrayAdpater extends ArrayAdapter<Weather> {
    Context context;
    int resource;
    ArrayList<Weather> weatherList;
    public WeatherArrayAdpater(Context context, int resource, ArrayList<Weather> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        weatherList=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Vholder holder;
        if(convertView==null){

            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(resource,parent,false);
           // convertView.setBackgroundColor();
            holder=new Vholder();
            holder.img=(ImageView)convertView.findViewById(R.id.imageView2);
            holder.time=(TextView)convertView.findViewById(R.id.txttemp);
            holder.condition=(TextView)convertView.findViewById(R.id.txtCond);
            holder.temp=(TextView)convertView.findViewById(R.id.txtFah);
            convertView.setTag(holder);
        }
        holder=(Vholder)convertView.getTag();
        ImageView img1=holder.img;
        TextView time1=holder.time;
        TextView cond1=holder.condition;
        TextView temp1=holder.temp;
       Picasso.with(context).load(weatherList.get(position).iconUrl).into(img1);
        String s=weatherList.get(position).temp + "°F";
        time1.setText(weatherList.get(position).time.toLowerCase());
        cond1.setText(weatherList.get(position).clouds);
        temp1.setText(s);
        convertView.setBackgroundColor(Color.parseColor("#e6e6e6"));
       // temp1.setText(weatherList.get(position).temp);
        return convertView;
        //return super.getView(position, convertView, parent);

    }
    static class Vholder{
        ImageView img;
        TextView temp;
        TextView condition;
        TextView time;

    }
}
