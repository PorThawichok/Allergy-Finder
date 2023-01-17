package com.fluke.allergyfinder.Model;

public class Year {
    public String year_id;
    public int calories;
    public int protein;
    public int carbohydrate;
    public int fat;
    public int sugar;
    public int sodium;
    public String year;
    public String user_id;

    public Year() {

    }

    public Year(String year_id, int calories, int protein, int carbohydrate, int fat, int sugar, int sodium, String year, String user_id) {
        this.year_id = year_id;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
        this.sugar = sugar;
        this.sodium = sodium;
        this.year = year;
        this.user_id = user_id;
    }

    public String getYear_id() {
        return year_id;
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

    public String getYear() {
        return year;
    }

    public String getUser_id() {
        return user_id;
    }
}
