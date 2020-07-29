package com.example.ddth.Model;

public class Ratting {
    private String userphone, foodId, ratevalue, comment;

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getRatevalue() {
        return ratevalue;
    }

    public void setRatevalue(String ratevalue) {
        this.ratevalue = ratevalue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Ratting() {
    }

    public Ratting(String userphone, String foodId, String ratevalue, String comment) {
        this.userphone = userphone;
        this.foodId = foodId;
        this.ratevalue = ratevalue;
        this.comment = comment;
    }
}
