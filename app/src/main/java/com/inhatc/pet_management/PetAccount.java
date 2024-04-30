package com.inhatc.pet_management;

public class PetAccount {

    private String name;       // 반려동물 이름
    private String birth;      // 반려동물 생년월일
    private String species;    // 반려동물 종
    private String imgUrl;     // 반려동물 이미지
    private String emailId;    // 유저 아이디
    private String key;        // Firebase 데이터베이스 키

    public PetAccount() {
        // 기본 생성자
    }

    public PetAccount(String name, String birth, String species, String imgUrl) {
        this.name = name;
        this.birth = birth;
        this.species = species;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }
}
