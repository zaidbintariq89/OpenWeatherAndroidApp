package com.framework.openweatherandroidapp.model;

import com.google.gson.annotations.SerializedName;

public class WeatherItem {

    public WeatherItem(){}

    public WeatherItem(String icon, String description, String main, int id) {
        this.icon = icon;
        this.description = description;
        this.main = main;
        this.id = id;
    }

    @SerializedName("icon")
    private String icon;

    @SerializedName("description")
    private String description;

    @SerializedName("main")
    private String main;

    @SerializedName("id")
    private int id;

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getMain() {
        return main;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return
                "WeatherItem{" +
                        "icon = '" + icon + '\'' +
                        ",description = '" + description + '\'' +
                        ",main = '" + main + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}