package com.example.myapplication;

public class ModelExploreResults {

    private String exploreResultsId;
    private String exploreResultsImage;
    private String exploreResultsTitle;
    private String exploreResultsLocation;
    private String exploreResultsDescription;

    public ModelExploreResults(String exploreResultsId, String exploreResultsImage, String exploreResultsTitle, String exploreResultsLocation, String exploreResultsDescription) {
        this.exploreResultsId = exploreResultsId;
        this.exploreResultsImage = exploreResultsImage;
        this.exploreResultsTitle = exploreResultsTitle;
        this.exploreResultsLocation = exploreResultsLocation;
        this.exploreResultsDescription = exploreResultsDescription;
    }

    public String getExploreResultsId() {
        return exploreResultsId;
    }

    public void setExploreResultsId(String exploreResultsId) {
        this.exploreResultsId = exploreResultsId;
    }

    public String getExploreResultsImage() {
        return exploreResultsImage;
    }

    public void setExploreResultsImage(String exploreResultsImage) {
        this.exploreResultsImage = exploreResultsImage;
    }

    public String getExploreResultsTitle() {
        return exploreResultsTitle;
    }

    public void setExploreResultsTitle(String exploreResultsTitle) {
        this.exploreResultsTitle = exploreResultsTitle;
    }

    public String getExploreResultsLocation() {
        return exploreResultsLocation;
    }

    public void setExploreResultsLocation(String exploreResultsLocation) {
        this.exploreResultsLocation = exploreResultsLocation;
    }

    public String getExploreResultsDescription() {
        return exploreResultsDescription;
    }

    public void setExploreResultsDescription(String exploreResultsDescription) {
        this.exploreResultsDescription = exploreResultsDescription;
    }
}



