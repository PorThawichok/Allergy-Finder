package com.fluke.allergyfinder.Model;

import java.util.List;

public class Product {
    public String barcode;
    public int calories;
    public int carbohydrate;
    public int eat;
    public int fat;
    public List<String> ingredients;
    public String name;
    public String photo;
    public int protein;
    public int sodium;
    public int sugar;

    public Product() {

    }

    public Product(String barcode, int calories, int carbohydrate, int eat, int fat, List<String> ingredients, String name, String photo, int protein, int sodium, int sugar) {
        this.barcode = barcode;
        this.calories = calories;
        this.carbohydrate = carbohydrate;
        this.eat = eat;
        this.fat = fat;
        this.ingredients = ingredients;
        this.name = name;
        this.photo = photo;
        this.protein = protein;
        this.sodium = sodium;
        this.sugar = sugar;
    }

    public String getBarcode() {
        return barcode;
    }

    public int getCalories() {
        return calories;
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }

    public int getEat() {
        return eat;
    }

    public int getFat() {
        return fat;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public int getProtein() {
        return protein;
    }

    public int getSodium() {
        return sodium;
    }

    public int getSugar() {
        return sugar;
    }
}
