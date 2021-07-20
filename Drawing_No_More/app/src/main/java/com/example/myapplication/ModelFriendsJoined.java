package com.example.myapplication;

public class ModelFriendsJoined {

    public int travelId, userTravelId;
    public String groupTravelCode, friendProfileImage, friendName;

    public ModelFriendsJoined(int travelId, int userTravelId, String groupTravelCode, String friendProfileImage, String friendName) {
        this.travelId = travelId;
        this.userTravelId = userTravelId;
        this.groupTravelCode = groupTravelCode;
        this.friendProfileImage = friendProfileImage;
        this.friendName = friendName;
    }

    public int getTravelId() {
        return travelId;
    }

    public void setTravelId(int travelId) {
        this.travelId = travelId;
    }

    public int getUserTravelId() {
        return userTravelId;
    }

    public void setUserTravelId(int userTravelId) {
        this.userTravelId = userTravelId;
    }

    public String getGroupTravelCode() {
        return groupTravelCode;
    }

    public void setGroupTravelCode(String groupTravelCode) {
        this.groupTravelCode = groupTravelCode;
    }

    public String getFriendProfileImage() {
        return friendProfileImage;
    }

    public void setFriendProfileImage(String friendProfileImage) {
        this.friendProfileImage = friendProfileImage;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
}
