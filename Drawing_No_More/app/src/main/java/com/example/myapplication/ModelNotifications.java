package com.example.myapplication;

public class ModelNotifications {

    int notification_id;
    String notification_title;
    String notification_for;
    String notification_about;
    String notification_date;
    String notification_img;

    public ModelNotifications(int notification_id, String notification_title, String notification_for, String notification_about, String notification_date, String notification_img) {
        this.notification_id = notification_id;
        this.notification_title = notification_title;
        this.notification_for = notification_for;
        this.notification_about = notification_about;
        this.notification_date = notification_date;
        this.notification_img = notification_img;
    }

    public int getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(int notification_id) {
        this.notification_id = notification_id;
    }

    public String getNotification_title() {
        return notification_title;
    }

    public void setNotification_title(String notification_title) {
        this.notification_title = notification_title;
    }

    public String getNotification_for() {
        return notification_for;
    }

    public void setNotification_for(String notification_for) {
        this.notification_for = notification_for;
    }

    public String getNotification_about() {
        return notification_about;
    }

    public void setNotification_about(String notification_about) {
        this.notification_about = notification_about;
    }

    public String getNotification_date() {
        return notification_date;
    }

    public void setNotification_date(String notification_date) {
        this.notification_date = notification_date;
    }

    public String getNotification_img() {
        return notification_img;
    }

    public void setNotification_img(String notification_img) {
        this.notification_img = notification_img;
    }
}


