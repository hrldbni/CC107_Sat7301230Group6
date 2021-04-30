package com.example.myapplication;

public class ModelInviteFriend {

    int inviteFriendId;
    String inviteFriendName, inviteFriendImage;

    public ModelInviteFriend(int inviteFriendId, String inviteFriendName, String inviteFriendImage) {
        this.inviteFriendId = inviteFriendId;
        this.inviteFriendName = inviteFriendName;
        this.inviteFriendImage = inviteFriendImage;
    }

    public int getInviteFriendId() {
        return inviteFriendId;
    }

    public void setInviteFriendId(int inviteFriendId) {
        this.inviteFriendId = inviteFriendId;
    }

    public String getInviteFriendName() {
        return inviteFriendName;
    }

    public void setInviteFriendName(String inviteFriendName) {
        this.inviteFriendName = inviteFriendName;
    }

    public String getInviteFriendImage() {
        return inviteFriendImage;
    }

    public void setInviteFriendImage(String inviteFriendImage) {
        this.inviteFriendImage = inviteFriendImage;
    }
}
