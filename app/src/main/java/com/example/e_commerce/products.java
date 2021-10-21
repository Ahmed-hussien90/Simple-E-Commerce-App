package com.example.e_commerce;

import com.google.firebase.storage.StorageReference;

public class products {
    String title;
    String oldPrice;
    String price;
    String details;
    int quantity;
    StorageReference ImagePath;
    String check;


    public products(String title,String oldPrice, String price ,StorageReference imagePath , int quantity ,String details,String check) {
        this.title =title;
        this.price = price;
        this.ImagePath = imagePath;
        this.quantity = quantity;
        this.oldPrice =oldPrice;
        this.details = details;
        this.check = check;
    }


    public String getTitle() {
        return  title;
    }

    public String getPrice() {
        return price;
    }

    public StorageReference getImagePath() {
        return ImagePath;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public String getDetails() {
        return details;
    }

    public String getCheck() {
        return check;
    }
}

