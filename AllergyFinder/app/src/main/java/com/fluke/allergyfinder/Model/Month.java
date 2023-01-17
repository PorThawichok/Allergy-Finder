package com.fluke.allergyfinder.Model;

public class Month {
    public String month_id;
    public int calories;
    public int protein;
    public int carbohydrate;
    public int fat;
    public int sugar;
    public int sodium;
    public String month;
    public String user_id;

    public Month() {

    }

    public Month(String month_id, int calories, int protein, int carbohydrate, int fat, int sugar, int sodium, String month, String user_id) {
        this.month_id = month_id;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
        this.sugar = sugar;
        this.sodium = sodium;
        this.month = month;
        this.user_id = user_id;
    }

    public String getMonth_id() {
        return month_id;
    }

    public int getCalories() {
        return calories;
    }

    public int getProtein() {
        return protein;
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }

    public int getFat() {
        return fat;
    }

    public int getSugar() {
        return sugar;
    }

    public int getSodium() {
        return sodium;
    }

    public String getMonth() {
        return month;
    }

    public String getUser_id() {
        return user_id;
    }
}
