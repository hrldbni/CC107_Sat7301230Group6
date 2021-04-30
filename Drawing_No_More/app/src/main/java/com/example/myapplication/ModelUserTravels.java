package com.example.myapplication;

public class ModelUserTravels {

    public int travel_id;
    public String travel_date, travel_destination, travelStatus, travel_img;

    public ModelUserTravels(int travel_id, String travel_date, String travel_destination, String travelStatus, String travel_img) {
        this.travel_id = travel_id;
        this.travel_date = travel_date;
        this.travel_destination = travel_destination;
        this.travelStatus = travelStatus;
        this.travel_img = travel_img;
    }

    public int getTravel_id() {
        return travel_id;
    }

    public void setTravel_id(int travel_id) {
        this.travel_id = travel_id;
    }

    public String getTravel_date() {
        return travel_date;
    }

    public void setTravel_date(String travel_date) {
        this.travel_date = travel_date;
    }

    public String getTravel_destination() {
        return travel_destination;
    }

    public void setTravel_destination(String travel_destination) {
        this.travel_destination = travel_destination;
    }

    public String getTravelStatus() {
        return travelStatus;
    }

    public void setTravelStatus(String travelStatus) {
        this.travelStatus = travelStatus;
    }

    public String getTravel_img() {
        return travel_img;
    }

    public void setTravel_img(String travel_img) {
        this.travel_img = travel_img;
    }
}
