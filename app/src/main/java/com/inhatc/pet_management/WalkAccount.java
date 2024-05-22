package com.inhatc.pet_management;

import android.net.Uri;

public class WalkAccount {

    private String emailId;
    private String name;
    private String walkDate;
    private String walkTime;
    private String walkStep;
    private String walkMemo;
    private String walkMeter;
    private String walkImageUri;


    public WalkAccount() {

    }


    public WalkAccount(String emailId, String name, String date, String time, String step, String meter, String memo, String uri) {
        this.emailId = emailId;
        this.name = name;
        this.walkDate = date;
        this.walkTime = time;
        this.walkStep = step;
        this.walkMeter = meter;
        this.walkMemo = memo;
        this.walkImageUri = uri;
    }


    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWalkDate() {
        return walkDate;
    }

    public void setWalkDate(String walkDate) {
        this.walkDate = walkDate;
    }

    public String getWalkTime() {
        return walkTime;
    }

    public void setWalkTime(String walkTime) {
        this.walkTime = walkTime;
    }

    public String getWalkStep() {
        return walkStep;
    }

    public void setWalkStep(String walkStep) {
        this.walkStep = walkStep;
    }

    public String getWalkMemo() {
        return walkMemo;
    }

    public void setWalkMemo(String walkMemo) {
        this.walkMemo = walkMemo;
    }

    public String getWalkMeter() {
        return walkMeter;
    }

    public void setWalkMeter(String walkMeter) {
        this.walkMeter = walkMeter;
    }

    public String getWalkImageUri() {
        return walkImageUri;
    }

    public void setWalkImageUri(String walkImageUri) {
        this.walkImageUri = walkImageUri;
    }
}
