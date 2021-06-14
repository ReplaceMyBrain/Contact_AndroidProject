package com.aoslec.contactproject.Bean;

public class People {

    private String pNo;
    private String pName;
    private String pTel;
    private String pImg;
    private String pGroup;
    private String pFavorite;
    private String uEmail;
    private String count;

    public People(String pNo, String pName, String pTel, String pImg, String pGroup, String pFavorite) {
        this.pNo = pNo;
        this.pName = pName;
        this.pTel = pTel;
        this.pImg = pImg;
        this.pGroup = pGroup;
        this.pFavorite = pFavorite;
    }

    public People(String pName) {
        this.pName = pName;
    }

    public People(String pGroup, String count) {
        this.pGroup = pGroup;
        this.count = count;
    }

    public String getpNo() {
        return pNo;
    }

    public void setpNo(String pNo) {
        this.pNo = pNo;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpTel() {
        return pTel;
    }

    public void setpTel(String pTel) {
        this.pTel = pTel;
    }

    public String getpImg() {
        return pImg;
    }

    public void setpImg(String pImg) {
        this.pImg = pImg;
    }

    public String getpGroup() {
        return pGroup;
    }

    public void setpGroup(String pGroup) {
        this.pGroup = pGroup;
    }

    public String getpFavorite() {
        return pFavorite;
    }

    public void setpFavorite(String pFavorite) {
        this.pFavorite = pFavorite;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }
}



