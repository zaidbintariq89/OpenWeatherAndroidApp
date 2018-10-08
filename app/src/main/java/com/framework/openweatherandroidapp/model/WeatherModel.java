package com.framework.openweatherandroidapp.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class WeatherModel {

    public WeatherModel(int dt, Coord coord, int visibility, List<WeatherItem> weather, String name, int cod, Main main, Clouds clouds, int id, Sys sys, String base, Wind wind) {
        this.dt = dt;
        this.coord = coord;
        this.visibility = visibility;
        this.weather = weather;
        this.name = name;
        this.cod = cod;
        this.main = main;
        this.clouds = clouds;
        this.id = id;
        this.sys = sys;
        this.base = base;
        this.wind = wind;
    }

    @SerializedName("dt")
    private int dt;

    @SerializedName("coord")
    private Coord coord;

    @SerializedName("visibility")
    private int visibility;

    @SerializedName("weather")
    private List<WeatherItem> weather;

    @SerializedName("name")
    private String name;

    @SerializedName("cod")
    private int cod;

    @SerializedName("main")
    private Main main;

    @SerializedName("clouds")
    private Clouds clouds;

    @SerializedName("id")
    private int id;

    @SerializedName("sys")
    private Sys sys;

    @SerializedName("base")
    private String base;

    @SerializedName("wind")
    private Wind wind;

    public void setDt(int dt) {
        this.dt = dt;
    }

    public int getDt() {
        return dt;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setWeather(List<WeatherItem> weather) {
        this.weather = weather;
    }

    public List<WeatherItem> getWeather() {
        return weather;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getCod() {
        return cod;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Main getMain() {
        return main;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Sys getSys() {
        return sys;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getBase() {
        return base;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Wind getWind() {
        return wind;
    }

    @Override
    public String toString() {
        return
                "WeatherModel{" +
                        "dt = '" + dt + '\'' +
                        ",coord = '" + coord + '\'' +
                        ",visibility = '" + visibility + '\'' +
                        ",weather = '" + weather + '\'' +
                        ",name = '" + name + '\'' +
                        ",cod = '" + cod + '\'' +
                        ",main = '" + main + '\'' +
                        ",clouds = '" + clouds + '\'' +
                        ",id = '" + id + '\'' +
                        ",sys = '" + sys + '\'' +
                        ",base = '" + base + '\'' +
                        ",wind = '" + wind + '\'' +
                        "}";
    }
}