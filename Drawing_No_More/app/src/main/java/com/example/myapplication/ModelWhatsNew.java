package com.example.myapplication;

public class ModelWhatsNew {

    private String image;
    private String placeTitle;
    private String placeLocation;

    public ModelWhatsNew(String image, String placeTitle, String placeLocation) {
        this.image = image;
        this.placeTitle = placeTitle;
        this.placeLocation = placeLocation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlaceTitle() {
        return placeTitle;
    }

    public void setPlaceTitle(String placeTitle) {
        this.placeTitle = placeTitle;
    }

    public String getPlaceLocation() {
        return placeLocation;
    }

    public void setPlaceLocation(String placeLocation) {
        this.placeLocation = placeLocation;
    }
}
