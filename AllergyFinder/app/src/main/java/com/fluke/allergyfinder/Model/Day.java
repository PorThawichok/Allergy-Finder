package com.fluke.allergyfinder.Model;

public class Day {
    public String day_id;
    public int calories;
    public int protein;
    public int carbohydrate;
    public int fat;
    public int sugar;
    public int sodium;
    public String day;
    public String user_id;

    public Day() {

    }

    public Day(String day_id, int calories, int protein, int carbohydrate, int fat, int sugar, int sodium, String day, String user_id) {
        this.day_id = day_id;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
        this.sugar = sugar;
        this.sodium = sodium;
        this.day = day;
        this.user_id = user_id;
    }

    public String getDay_id() {
        return day_id;
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

    public String getDay() {
        return day;
    }

    public String getUser_id() {
        return user_id;
    }
}
