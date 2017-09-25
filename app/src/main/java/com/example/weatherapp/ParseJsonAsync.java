package com.example.weatherapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by anirudhln on 10/8/2016.
 */
public class ParseJsonAsync extends AsyncTask<String,Void,ArrayList<Weather>>{
    IData data;

    public ParseJsonAsync(IData data){
        this.data=data;
    }
    String read;
    @Override
    protected ArrayList<Weather> doInBackground(String... params) {
        try {
            URL url=new URL(params[0]);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.connect();
            BufferedReader buf=new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer sb=new StringBuffer();
            while((read=buf.readLine())!=null)
            {
                sb.append(read);
            }
            return WeatherUtil.Getjson(sb.toString());


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Weather> weathers) {
        super.onPostExecute(weathers);
//        Log.d("array list",weathers.toString());
            data.setUpData(weathers);


    }
    public interface IData{
        public void setUpData(ArrayList<Weather> wt);
    }
}
