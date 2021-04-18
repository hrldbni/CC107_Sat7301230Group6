package com.example.myapplication;

public class ModelFriends {
    private int personId;
    private String personImage;
    private String personName;

    public ModelFriends(int personId, String personImage, String personName) {
        this.personId = personId;
        this.personImage = personImage;
        this.personName = personName;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getPersonImage() {
        return personImage;
    }

    public void setPersonImage(String personImage) {
        this.personImage = personImage;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
