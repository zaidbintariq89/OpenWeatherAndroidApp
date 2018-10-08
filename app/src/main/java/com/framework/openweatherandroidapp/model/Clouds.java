package com.framework.openweatherandroidapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clouds {

    public Clouds(int all) {
        this.all = all;
    }

    @SerializedName("all")
    @Expose
    private int all;

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }


    @Override
    public String toString() {
        return
                "Clouds{" +
                        "all = '" + all + '\'' +
                        "}";
    }
}