package com.example.myapplication;

public class UserTravels {

    private final int id;
    private final String placeTitle;
    private final String travelDate;
    private final String currentFund;
    private final String availableFund;
    private final String placeImage;
    private final String travelStatus;
    private final String travelLocation;


    public UserTravels(int id, String placeTitle, String travelDate, String currentFund, String availableFund, String placeImage, String travelStatus, String travelLocation) {
        this.id = id;
        this.placeTitle = placeTitle;
        this.travelDate = travelDate;
        this.currentFund = currentFund;
        this.availableFund = availableFund;
        this.placeImage = placeImage;
        this.travelStatus = travelStatus;
        this.travelLocation = travelLocation;
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

    public String getTravelStatus() {
        return travelStatus;
    }

    public String getTravelLocation() {
        return travelLocation;
    }
}
