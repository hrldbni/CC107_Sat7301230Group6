package com.example.myapplication;

public class ModelDestinationImages {

    public int destinationId;
    public String destinationLocation;
    public String destinationDescription;
    public String destinationImage;

    public ModelDestinationImages(int destinationId, String destinationLocation, String destinationDescription, String destinationImage) {
        this.destinationId = destinationId;
        this.destinationLocation = destinationLocation;
        this.destinationDescription = destinationDescription;
        this.destinationImage = destinationImage;
    }

    public String getDestinationImage() {
        return destinationImage;
    }

    public void setDestinationImage(String destinationImage) {
        this.destinationImage = destinationImage;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public String getDestinationDescription() {
        return destinationDescription;
    }

    public void setDestinationDescription(String destinationDescription) {
        this.destinationDescription = destinationDescription;
    }
}
