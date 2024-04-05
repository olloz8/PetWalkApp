package com.inhatc.pet_management;

public class PetAccount {

    private String petId;       //번호
    private String pname;       //반려동물 이름
    private String pbirth;      //반려동물 생년월일
    private String pspecies;    //반려동물 종
    private String imgUrl;     //반려동물 이미지

    public PetAccount() {
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPbirth() {
        return pbirth;
    }

    public void setPbirth(String pbirth) {
        this.pbirth = pbirth;
    }

    public String getPspecies() {
        return pspecies;
    }

    public void setPspecies(String pspecies) {
        this.pspecies = pspecies;
    }


}
