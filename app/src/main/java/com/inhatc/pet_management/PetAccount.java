package com.inhatc.pet_management;

public class PetAccount {

    private String idToken;
    private String pname;
    private String pbirth;
    private String pspecies;
    private String petimage;

    public PetAccount() {
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
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

    public String getPetimage() {
        return petimage;
    }

    public void setPetimage(String petimage) {
        this.petimage = petimage;
    }
}
