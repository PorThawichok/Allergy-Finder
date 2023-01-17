package com.fluke.allergyfinder.Model;

public class FavoriteModel {
    public String favorite_id;
    public String barcode;
    public String user_id;

    public FavoriteModel() {

    }

    public FavoriteModel(String favorite_id, String barcode, String user_id) {
        this.favorite_id = favorite_id;
        this.barcode = barcode;
        this.user_id = user_id;
    }

    public String getFavorite_id() {
        return favorite_id;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getUser_id() {
        return user_id;
    }
}
