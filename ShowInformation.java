package com.example.harjoitustyfinnkino;

import androidx.annotation.NonNull;

public class ShowInformation {
    public String start;
    public String length;
    public String theatre;
    public String title;
    public String ID;
    private final String jpg;

    public String getID() {
        return ID;
    }

    public String getStart() {
        return ("Elokuva alkaa: " + start);
    }

    public String getTitle() {
        return title;
    }

    public String getLength() {
        return ("Pituus: " + length + "min");
    }

    public String getTheatre() {
        return theatre;
    }
    public String getJpg() {
        return jpg;
    }

    public ShowInformation(String start,String title,String length, String theatre, String ID, String jpg) {
        this.start = start;
        this.title = title;
        this.length = length;
        this.theatre = theatre;
        this.ID = ID;
        this.jpg = jpg;
    }

    @NonNull
    @Override
    public String toString() {
        return start + "\n" + title + "\n" + length + "\n" + theatre;
    }


}
