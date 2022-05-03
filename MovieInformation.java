package com.example.harjoitustyfinnkino;



public class MovieInformation {
    public String name;
    public String id;
    public String SSynopsis;
    public String jpg;
    public String rating;

    public String getJpg() {
        return jpg;
    }

    public MovieInformation(String name, String id, String SSynopsis, String jpg){
        this.name = name;
        this.id = id;
        this.SSynopsis = SSynopsis;
        this.jpg = jpg;
    }

    public MovieInformation(String name, String rating, String SSynopsis){
        this.name = name;
        this.SSynopsis = SSynopsis;
        this.rating = rating;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSSynopsis() {
        return SSynopsis;
    }

    public void setSSynopsis(String SSynopsis) {
        this.SSynopsis = SSynopsis;
    }

    @Override
    public String toString() {
        return name;
    }
}
