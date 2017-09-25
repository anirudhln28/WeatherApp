package com.example.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText city;
    EditText state;
    Button submit;
    ListView fav;
    TextView favlabel;
    TextView nofav;
    List<FavObject> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nofav= (TextView) findViewById(R.id.textView2);
        city= (EditText) findViewById(R.id.City);
        state= (EditText) findViewById(R.id.State_edit);
        submit= (Button) findViewById(R.id.button);
        favlabel= (TextView) findViewById(R.id.textView);
        fav= (ListView) findViewById(R.id.listView);
        favlabel.setVisibility(View.GONE);
        SharedPreferences prefs = getSharedPreferences("myprefs4", Context.MODE_PRIVATE);
        String j=prefs.getString("object","");
        Log.d("j value",j);
        Log.d("j",j);
        Gson gson = new Gson();
       Type type = new TypeToken<List<FavObject>>(){}.getType();
       list = gson.fromJson(j, type);
        if(list==null || list.isEmpty())
        {
           nofav.setVisibility(View.VISIBLE);
            favlabel.setVisibility(View.GONE);
        }
        else {
            ArrayList<FavObject> list1=(ArrayList<FavObject>)list;
            nofav.setVisibility(View.GONE);
            favlabel.setVisibility(View.VISIBLE);
            FavoriteAdapter fapad=new FavoriteAdapter(MainActivity.this,R.layout.itemfav_row_layout,list1);
            fav.setAdapter(fapad);
            fapad.setNotifyOnChange(true);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (city.getText().toString().matches("")){city.setError("Enter City Name");}
                else if(state.getText().toString().matches("")){
                    state.setError("Enter State Name");
                }
                else if(state.getText().toString().length()!=2)
                {
                    state.setError("2 Letters State Initial Expected (ex:CA)");
                }
                else {
                    Intent i = new Intent(MainActivity.this, CityWeather.class);
                    i.putExtra("City", city.getText().toString());
                    i.putExtra("State", state.getText().toString());
                    startActivity(i);
                }
            }
        });
        fav.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("is clicked","is long clicked");

                SharedPreferences prefs = getSharedPreferences("myprefs4", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                String j=prefs.getString("object","");
                Gson gson = new Gson();
                Type type = new TypeToken<List<FavObject>>(){}.getType();
               List<FavObject> list2 = gson.fromJson(j, type);

                    view.setBackgroundColor(Color.parseColor("Blue"));

                //list2.get(position).colorofrow=true;
               list2.remove(position);
                String s=gson.toJson(list2);
                editor.putString("object", s);
                editor.commit();

                ArrayList<FavObject> list3=(ArrayList<FavObject>)list2;

                FavoriteAdapter fapad=new FavoriteAdapter(MainActivity.this,R.layout.itemfav_row_layout,list3);
                fav.setAdapter(fapad);
                Toast.makeText(MainActivity.this,"City Deleted",Toast.LENGTH_LONG).show();
                if(list3.isEmpty())
                {
                    nofav.setVisibility(View.VISIBLE);
                    favlabel.setVisibility(View.GONE);
                }
                fapad.setNotifyOnChange(true);

                return false;
            }
        });
    }
    @Override
    protected void onResume()
     {
        super.onResume();
        Log.d("onresume","here");
        SharedPreferences prefs = getSharedPreferences("myprefs4", Context.MODE_PRIVATE);
        String j=prefs.getString("object","");
        Gson gson = new Gson();
        Type type = new TypeToken<List<FavObject>>(){}.getType();
        List<FavObject> list2 = gson.fromJson(j, type);
        if(list2==null || list2.isEmpty())
        {

        }
        else{
            nofav.setVisibility(View.GONE);
            favlabel.setVisibility(View.VISIBLE);
            FavoriteAdapter fapad = new FavoriteAdapter(MainActivity.this, R.layout.itemfav_row_layout, (ArrayList<FavObject>) list2);
            fav = (ListView) findViewById(R.id.listView);
            fav.setAdapter(fapad);
            city = (EditText) findViewById(R.id.City);
            state = (EditText) findViewById(R.id.State_edit);
            city.setText("");
            state.setText("");
        }
    }
}
