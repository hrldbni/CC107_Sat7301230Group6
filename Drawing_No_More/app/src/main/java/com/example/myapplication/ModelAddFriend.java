package com.example.myapplication;

public class ModelAddFriend {

    private int addFriend_userId;
    private String addFriendName;
    private String addFriendImage;
    private String addFriendCheck;

    public ModelAddFriend(int addFriend_userId, String addFriendName, String addFriendImage, String addFriendCheck) {
        this.addFriend_userId = addFriend_userId;
        this.addFriendName = addFriendName;
        this.addFriendImage = addFriendImage;
        this.addFriendCheck = addFriendCheck;
    }

    public int getAddFriend_userId() {
        return addFriend_userId;
    }

    public void setAddFriend_userId(int addFriend_userId) {
        this.addFriend_userId = addFriend_userId;
    }

    public String getAddFriendName() {
        return addFriendName;
    }

    public void setAddFriendName(String addFriendName) {
        this.addFriendName = addFriendName;
    }

    public String getAddFriendImage() {
        return addFriendImage;
    }

    public void setAddFriendImage(String addFriendImage) {
        this.addFriendImage = addFriendImage;
    }

    public String getAddFriendCheck() {
        return addFriendCheck;
    }

    public void setAddFriendCheck(String addFriendCheck) {
        this.addFriendCheck = addFriendCheck;
    }
}
