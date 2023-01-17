package com.fluke.allergyfinder.Model;

public class Eating {
    public String eating_id;
    public String barcode;
    public String day;
    public String time;
    public String user_id;

    public Eating() {

    }

    public Eating(String eating_id, String barcode, String day, String time, String user_id) {
        this.eating_id = eating_id;
        this.barcode = barcode;
        this.day = day;
        this.time = time;
        this.user_id = user_id;
    }

    public String getEating_id() {
        return eating_id;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getUser_id() {
        return user_id;
    }
}
