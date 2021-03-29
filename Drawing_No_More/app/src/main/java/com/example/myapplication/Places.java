package com.example.myapplication;

public class Places {
    private int id;
    private String title, shortdesc;
    private double rating;
    private String button;
    private int image;

    public Places(int id, String title, String shortdesc, double rating, String button, int image) {
        this.id = id;
        this.title = title;
        this.shortdesc = shortdesc;
        this.rating = rating;
        this.button = button;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public double getRating() {
        return rating;
    }

    public String getButton() {
        return button;
    }

    public int getImage() {
        return image;
    }
}
