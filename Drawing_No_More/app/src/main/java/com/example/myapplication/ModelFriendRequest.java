package com.example.myapplication;

public class ModelFriendRequest {

    private int requestorUserId, requestId;
    private String requestorImage;
    private String requestorName;

    public ModelFriendRequest(int requestorUserId, int requestId, String requestorImage, String requestorName) {
        this.requestorUserId = requestorUserId;
        this.requestId = requestId;
        this.requestorImage = requestorImage;
        this.requestorName = requestorName;
    }

    public int getRequestorUserId() {
        return requestorUserId;
    }

    public void setRequestorUserId(int requestorUserId) {
        this.requestorUserId = requestorUserId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getRequestorImage() {
        return requestorImage;
    }

    public void setRequestorImage(String requestorImage) {
        this.requestorImage = requestorImage;
    }

    public String getRequestorName() {
        return requestorName;
    }

    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }
}
