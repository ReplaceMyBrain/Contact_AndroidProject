package com.aoslec.contactproject.Bean;

public class People {
    private String name;
    private String tel;
    private String group;
    private String favorite;
    private String memo;
    private String img;

    public People(String name, String img) {
        this.name = name;
        this.img = img;
    }

    public People(String name, String tel, String group, String favorite, String memo, String img) {
        this.name = name;
        this.tel = tel;
        this.group = group;
        this.favorite = favorite;
        this.memo = memo;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}


