package com.example.ddth.Model;



public class Users {
    public String email;
    public String image;
    public String name;
    public String phone;
    public String uid;
    public String rule;





    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Users(String email, String image, String name, String phone, String uid, String rule) {
        this.email = email;
        this.image = image;
        this.name = name;
        this.phone = phone;
        this.uid = uid;
        this.rule = rule;
    }

    public Users() {
    }


}
