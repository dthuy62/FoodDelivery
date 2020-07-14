package com.example.ddth.Model;

public class DanangFood {
    String name;
    String price;
    Integer imgUrl;
    String rating;
    String restaurent_name;
    public DanangFood(String re_name, String name, String rating, String price, Integer imgUrl  ) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.rating = rating;
        this.restaurent_name = re_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(Integer imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRestaurent_name() {
        return restaurent_name;
    }

    public void setRestaurent_name(String restaurent_name) {
        this.restaurent_name = restaurent_name;
    }
}
