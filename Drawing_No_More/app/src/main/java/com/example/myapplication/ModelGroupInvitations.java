package com.example.myapplication;

public class ModelGroupInvitations {

    public int requestId;
    public String groupTravelCode, groupTravelAdmin, groupTravelToInvite, travelId;

    public ModelGroupInvitations(int requestId, String groupTravelCode, String groupTravelAdmin, String groupTravelToInvite, String travelId) {
        this.requestId = requestId;
        this.groupTravelCode = groupTravelCode;
        this.groupTravelAdmin = groupTravelAdmin;
        this.groupTravelToInvite = groupTravelToInvite;
        this.travelId = travelId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getGroupTravelCode() {
        return groupTravelCode;
    }

    public void setGroupTravelCode(String groupTravelCode) {
        this.groupTravelCode = groupTravelCode;
    }

    public String getGroupTravelAdmin() {
        return groupTravelAdmin;
    }

    public void setGroupTravelAdmin(String groupTravelAdmin) {
        this.groupTravelAdmin = groupTravelAdmin;
    }

    public String getGroupTravelToInvite() {
        return groupTravelToInvite;
    }

    public void setGroupTravelToInvite(String groupTravelToInvite) {
        this.groupTravelToInvite = groupTravelToInvite;
    }

    public String getTravelId() {
        return travelId;
    }

    public void setTravelId(String travelId) {
        this.travelId = travelId;
    }
}




