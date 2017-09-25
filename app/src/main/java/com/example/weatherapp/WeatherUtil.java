package com.example.weatherapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by anirudh on 10/8/2016.
 */
 class WeatherUtil{



    public static ArrayList<Weather> Getjson(String sb)
    {
        ArrayList<Weather> wlist;
        int min=0,max=0;

        try {
            wlist=new ArrayList<>();
            JSONObject job=new JSONObject(sb);
            JSONObject res = job.getJSONObject("response");
            if(res.has("error")) {
             JSONObject error=res.getJSONObject("error");
                String desc=error.getString("description");
                Log.d("error","desc");
                return wlist;
            }
            else {


                JSONArray jsonArray = job.getJSONArray("hourly_forecast");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject elements = jsonArray.getJSONObject(i);
                    Weather w = new Weather();
                    JSONObject fctelement = elements.getJSONObject("FCTTIME");
                    w.setTime(fctelement.getString("civil"));
                    JSONObject temp = elements.getJSONObject("temp");
                    w.setTemp(temp.getString("english"));
                    JSONObject dewpoint = elements.getJSONObject("dewpoint");
                    w.setDewpoint(dewpoint.getString("english"));
                    w.setClouds(elements.getString("condition"));
                    w.setIconUrl(elements.getString("icon_url"));
                    JSONObject wspd = elements.getJSONObject("wspd");
                    w.setWindspeed(wspd.getString("english"));
                    JSONObject dir = elements.getJSONObject("wdir");
                    w.setWindDirection(dir.getString("degrees")+"°"+(dir.getString("dir")));
                    w.setClimateType(elements.getString("wx"));
                    w.setHumidity(elements.getString("humidity"));
                    JSONObject feelslike = elements.getJSONObject("feelslike");
                    w.setFeelsLike(feelslike.getString("english"));
                    JSONObject pressure = elements.getJSONObject("mslp");
                    w.setPressure(pressure.getString("metric"));
                    wlist.add(w);
                    if (i == 0) //68
                    {
                        min = Integer.parseInt(w.getTemp());
                    } else {
                        if (min > Integer.parseInt(w.getTemp())) {
                            min = Integer.parseInt(w.getTemp());
                        }
                    }
                    if (max < Integer.parseInt(w.getTemp())) {
                        max = Integer.parseInt(w.getTemp());
                    }
                }
                for (int j = 0; j < wlist.size(); j++) {
                    Weather w = wlist.get(j);
                    w.setMinimumTemp(String.valueOf(min));
                    w.setMaximumTemp(String.valueOf(max));

                }


                return wlist;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }
}
