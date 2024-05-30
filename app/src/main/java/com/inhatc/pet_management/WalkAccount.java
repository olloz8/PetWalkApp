package com.inhatc.pet_management;

import android.net.Uri;

public class WalkAccount {
    private String emailId;
    private String name;
    private String date;
    private String time;
    private String stepNumber;
    private String memo;
    private String meter;
    private String imgUrl;
    private String key;

    public WalkAccount() {
    }

    public WalkAccount(String date, String time, String stepNumber, String meter, String memo, String name, String imgUrl) {
        this.date = date;
        this.time = time;
        this.stepNumber = stepNumber;
        this.meter = meter;
        this.memo = memo;
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(String stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMeter() {
        return meter;
    }

    public void setMeter(String meter) {
        this.meter = meter;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
