package com.example.harjoitustyfinnkino;

import androidx.annotation.NonNull;

public class TheatreInformation {
    public String theatre_name;
    public String id;


    public TheatreInformation(String id, String theatre_name) {
        this.id = id;
        this.theatre_name = theatre_name;
    }

    @NonNull
    @Override
    public String toString(){
        return theatre_name;
    }
}
