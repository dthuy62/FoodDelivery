package com.example.ddth.Model;

public class Food {
    private String IdCategory, IdRestaurent, Namefood, Img, Desc, Price, Discount, Address;

    public Food() {
    }

    public Food(String idCategory, String idRestaurent, String namefood, String img, String desc, String price, String discount, String address) {
        IdCategory = idCategory;
        IdRestaurent = idRestaurent;
        Namefood = namefood;
        Img = img;
        Desc = desc;
        Price = price;
        Discount = discount;
        Address = address;
    }

    public String getIdCategory() {
        return IdCategory;
    }

    public void setIdCategory(String idCategory) {
        IdCategory = idCategory;
    }

    public String getIdRestaurent() {
        return IdRestaurent;
    }

    public void setIdRestaurent(String idRestaurent) {
        IdRestaurent = idRestaurent;
    }

    public String getNamefood() {
        return Namefood;
    }

    public void setName(String namefood) {
        Namefood = namefood;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
