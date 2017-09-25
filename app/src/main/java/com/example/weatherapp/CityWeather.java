package com.example.weatherapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class CityWeather extends AppCompatActivity implements ParseJsonAsync.IData{
    String city,citygiven;
    String state,stategiven;
    String url;
    ArrayList<Weather> wmainList=new ArrayList<>();
    ListView mylist;
    TextView location;
    int flag=0;
    ProgressDialog pd;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
     {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
     {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        citygiven=getIntent().getExtras().getString("City");
        stategiven=getIntent().getExtras().getString("State");
        city=citygiven.substring(0,1).toUpperCase()+citygiven.substring(1).toLowerCase();
        state=stategiven.toUpperCase();
         pd=new ProgressDialog(this);
        pd.setMessage("Loading Hourly Data");
        pd.show();

            String city1=city.replaceAll(" ","_");
        //city.matches("\s")

        url="http://api.wunderground.com/api/998f6ebcc0f45ec3/hourly/q/" + state + "/" + city1 + ".json";
        Log.d("url",url);
        mylist= (ListView) findViewById(R.id.listView2);
        location= (TextView) findViewById(R.id.city_lbl);
        location.setText(city+","+state);
        new ParseJsonAsync(this).execute(url);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.about:
                //add the function to perform here
               // Weather w=new Weather();
                SharedPreferences sharedpreferences = getSharedPreferences("myprefs4", Context.MODE_PRIVATE);
                String s1=sharedpreferences.getString("object",null);
                Gson gson = new Gson();
                FavObject favobj=new FavObject();
                Type type = new TypeToken<List<FavObject>>(){}.getType();
                List<FavObject> list = gson.fromJson(s1, type);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                if(list==null)
                {
                    list=new ArrayList<>();

                }
                for(int k=0;k<list.size();k++)
                {
                    if(city.toLowerCase().equals(list.get(k).city.toLowerCase() )&& state.toLowerCase().equals(list.get(k).state.toLowerCase()))
                    {
                        list.get(k).setTemp((wmainList.get(0).temp)+"°F");
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                        String formattedDate = df.format(c.getTime());
                        list.get(k).setDate(formattedDate);
                        Toast.makeText(CityWeather.this,"Updated Favorites Record",Toast.LENGTH_LONG).show();

                        flag=1;
                    }
                }
                if(flag==0)
                {
                    favobj.setCity(city);
                    favobj.setState(state);
                    favobj.setTemp((wmainList.get(0).temp)+"°F");
                    favobj.setCity(city);
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    String formattedDate = df.format(c.getTime());
                    favobj.setDate(formattedDate);
                    list.add(favobj);
                    Toast.makeText(CityWeather.this,"Added to Favorites",Toast.LENGTH_LONG).show();
                }
                String s=gson.toJson(list);
                editor.putString("object", s);
                editor.commit();
                return (true);
        }
            return super.onOptionsItemSelected(item);
        }


        @Override
    public void setUpData(ArrayList<Weather> wt) {
        wmainList=wt;
            pd.dismiss();
            if(wmainList.isEmpty())
            {
                Toast.makeText(this,"No cities match your query",Toast.LENGTH_LONG).show();
                Timer timer=new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        finish();
                    }
                },5000);

            }
            else {
                WeatherArrayAdpater wadapter = new WeatherArrayAdpater(this, R.layout.item_row_layout, wmainList);
                mylist.setAdapter(wadapter);
                mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(CityWeather.this, DetailsActivity.class);
                        i.putExtra("weatherlist", wmainList.get(position));
                        i.putExtra("city", city);
                        i.putExtra("state", state);
                        startActivity(i);
                    }
                });

            }
    }
}
