package com.example.weatherapp;

/**
 * Created by anirudhln on 10/9/2016.
 */
public class FavObject {
    String date,city,state,temp;
    Boolean colorofrow=false;

    public Boolean getColorofrow() {
        return colorofrow;
    }

    public void setColorofrow(Boolean colorofrow) {
        this.colorofrow = colorofrow;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "FavObject{" +
                "date='" + date + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", temp='" + temp + '\'' +
                '}';
    }
}
