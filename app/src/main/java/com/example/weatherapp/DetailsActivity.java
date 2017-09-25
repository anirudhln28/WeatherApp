package com.example.weatherapp;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    Weather w;
    TextView location;
    TextView time;
    TextView maxtemp;
    TextView mintemp;
    ImageView img;
    TextView temp;
    TextView condition;
    TextView feelslike,humidity,dewpt,pressure,clouds,winds;
    String city,state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        city=getIntent().getExtras().getString("city");
        state=getIntent().getExtras().getString("state");
        w= (Weather) getIntent().getExtras().getSerializable("weatherlist");
        location= (TextView) findViewById(R.id.txtLocn);
        time=(TextView)findViewById(R.id.txttime);
        img=(ImageView)findViewById(R.id.img1);
        temp=(TextView)findViewById(R.id.txttemp);
        condition=(TextView)findViewById(R.id.txtcon);
        maxtemp=(TextView)findViewById(R.id.showMin);
        mintemp=(TextView)findViewById(R.id.showmin);
        feelslike=(TextView)findViewById(R.id.tshowfeel);
        humidity=(TextView)findViewById(R.id.txtshowHum);
        dewpt=(TextView)findViewById(R.id.txtshowdp);
        pressure=(TextView)findViewById(R.id.txtSpressure);
        clouds=(TextView)findViewById(R.id.txtshowcloud);
        winds=(TextView)findViewById(R.id.txtshowWind);
        location.setText(city+","+state);
        String st=" ("+w.time.toLowerCase()+")";
        time.setText(st);
        Picasso.with(this).load(w.iconUrl).into(img);
        temp.setText(w.temp+ "°F");
        condition.setText(w.climateType);
        feelslike.setText(w.feelsLike+" Fahrenheit");
        humidity.setText(w.humidity+"%");
        dewpt.setText(w.dewpoint +" Fahrenheit");
        pressure.setText(w.pressure+ " hPa");
        clouds.setText(w.clouds);
        winds.setText(w.windspeed+" mph"+","+w.windDirection);
        maxtemp.setText(" "+w.maximumTemp +" Fahrenheit");mintemp.setText(" "+w.minimumTemp+" Fahrenheit");



    }
}
