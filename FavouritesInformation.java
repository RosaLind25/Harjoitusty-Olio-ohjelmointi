package com.example.harjoitustyfinnkino;

public class FavouritesInformation {
    public String name;

    public FavouritesInformation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }

}
