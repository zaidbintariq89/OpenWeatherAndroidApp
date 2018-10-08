package com.framework.openweatherandroidapp.model;

import com.google.gson.annotations.SerializedName;

public class Sys {

    public Sys(int type,int id,double message,String country,int sunrise,int sunset) {
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.id = id;
        this.type = type;
        this.message = message;
    }

    @SerializedName("country")
    private String country;

    @SerializedName("sunrise")
    private int sunrise;

    @SerializedName("sunset")
    private int sunset;

    @SerializedName("id")
    private int id;

    @SerializedName("type")
    private int type;

    @SerializedName("message")
    private double message;

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public int getSunrise() {
        return sunrise;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }

    public int getSunset() {
        return sunset;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public double getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return
                "Sys{" +
                        "country = '" + country + '\'' +
                        ",sunrise = '" + sunrise + '\'' +
                        ",sunset = '" + sunset + '\'' +
                        ",id = '" + id + '\'' +
                        ",type = '" + type + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}