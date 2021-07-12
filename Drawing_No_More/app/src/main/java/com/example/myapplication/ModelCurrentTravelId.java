package com.example.myapplication;

import android.app.Application;

public class ModelCurrentTravelId extends Application {

    private static String travelId;
    private static String groupCode;

    public static String getGroupCode() {
        return groupCode;
    }

    public static void setGroupCode(String groupCode) {
        ModelCurrentTravelId.groupCode = groupCode;
    }

    public static String getTravelId() {
        return travelId;
    }

    public void setTravelId(String travelId) {
        this.travelId = travelId;
    }
}
