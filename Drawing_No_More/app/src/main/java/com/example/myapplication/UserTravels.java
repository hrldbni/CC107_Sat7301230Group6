package com.example.myapplication;

public class UserTravels {

    private final int id;
    private final String placeTitle;
    private final String travelDate;
    private final String currentFund;
    private final String availableFund;
    private final String placeImage;


    public UserTravels(int id, String placeTitle, String travelDate, String currentFund, String availableFund, String placeImage) {
        this.id = id;
        this.placeTitle = placeTitle;
        this.travelDate = travelDate;
        this.currentFund = currentFund;
        this.availableFund = availableFund;
        this.placeImage = placeImage;
    }

    public int getId() {
        return id;
    }

    public String getPlaceTitle() {
        return placeTitle;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public String getCurrentFund() {
        return currentFund;
    }

    public String getAvailableFund() {
        return availableFund;
    }

    public String getPlaceImage() {
        return placeImage;
    }
}
