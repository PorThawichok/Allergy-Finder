package com.fluke.allergyfinder.Model;

public class User {
    public String user_id;
    public String email;
    public String password;
    public String name;
    public String photo;
    public String type;
    public String gender;
    public String age;
    public String corn;
    public int daily_calories;
    public int daily_protein;
    public int daily_carbohydrate;
    public int daily_fat;
    public int daily_sugar;
    public int daily_sodium;
    public String fluctose;
    public String gluten;
    public int height;
    public String lactose;
    public String no_sugar;
    public String nut;
    public String shellfish;
    public String vegan;
    public String exercise;
    public int weight;

    public User() {

    }

    public User(String user_id, String email, String password, String name, String photo, String type, String gender, String age, String corn, int daily_calories, int daily_protein, int daily_carbohydrate, int daily_fat, int daily_sugar, int daily_sodium, String fluctose, String gluten, int height, String lactose, String no_sugar, String nut, String shellfish, String vegan, String exercise, int weight) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.photo = photo;
        this.type = type;
        this.gender = gender;
        this.age = age;
        this.corn = corn;
        this.daily_calories = daily_calories;
        this.daily_protein = daily_protein;
        this.daily_carbohydrate = daily_carbohydrate;
        this.daily_fat = daily_fat;
        this.daily_sugar = daily_sugar;
        this.daily_sodium = daily_sodium;
        this.fluctose = fluctose;
        this.gluten = gluten;
        this.height = height;
        this.lactose = lactose;
        this.no_sugar = no_sugar;
        this.nut = nut;
        this.shellfish = shellfish;
        this.vegan = vegan;
        this.exercise = exercise;
        this.weight = weight;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getType() {
        return type;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getCorn() {
        return corn;
    }

    public int getDaily_calories() {
        return daily_calories;
    }

    public int getDaily_protein() {
        return daily_protein;
    }

    public int getDaily_carbohydrate() {
        return daily_carbohydrate;
    }

    public int getDaily_fat() {
        return daily_fat;
    }

    public int getDaily_sugar() {
        return daily_sugar;
    }

    public int getDaily_sodium() {
        return daily_sodium;
    }

    public String getFluctose() {
        return fluctose;
    }

    public String getGluten() {
        return gluten;
    }

    public int getHeight() {
        return height;
    }

    public String getLactose() {
        return lactose;
    }

    public String getNo_sugar() {
        return no_sugar;
    }

    public String getNut() {
        return nut;
    }

    public String getShellfish() {
        return shellfish;
    }

    public String getVegan() {
        return vegan;
    }

    public String getExercise() {
        return exercise;
    }

    public int getWeight() {
        return weight;
    }
}