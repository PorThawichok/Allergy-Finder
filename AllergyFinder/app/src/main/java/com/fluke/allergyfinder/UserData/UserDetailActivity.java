package com.fluke.allergyfinder.UserData;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fluke.allergyfinder.EditUserActivity;
import com.fluke.allergyfinder.R;
import com.fluke.allergyfinder.SharedPreferences.AppPreferences;
import com.fluke.allergyfinder.Utils;

public class UserDetailActivity extends AppCompatActivity {

    TextView txt_name, txt_password, txt_email, txt_short_name, txt_type, txt_age, txt_gender, txt_height, txt_weight, txt_lose,txt_exercise;
    AppPreferences appPreferences;
    String Weight, Height, User_id, Name, Password, Email, Photo, Type, Gender, Age, Corn, Fluctose, Gluten, Lactose, No_Sugar, Nut, Shellfish, Vegan, Exercise;
    ImageView image_profile, btn_choose_photo, img_visible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        Intent tt = getIntent();
        User_id = tt.getStringExtra("user_id");
        Email = tt.getStringExtra("email");
        Password = tt.getStringExtra("password");
        Name = tt.getStringExtra("name");
        Type = tt.getStringExtra("type");
        Photo = tt.getStringExtra("photo");
        Gender = tt.getStringExtra("gender");
        Age = tt.getStringExtra("age");
        Corn = tt.getStringExtra("corn");
        Fluctose = tt.getStringExtra("fluctose");
        Gluten = tt.getStringExtra("gluten");
        Lactose = tt.getStringExtra("lactose");
        No_Sugar = tt.getStringExtra("no_sugar");
        Nut = tt.getStringExtra("nut");
        Shellfish = tt.getStringExtra("shellfish");
        Vegan = tt.getStringExtra("vegan");
        Height = tt.getStringExtra("height");
        Weight = tt.getStringExtra("weight");
        Exercise = tt.getStringExtra("exercise");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Name);

        String arrayLose[] = {Corn, Fluctose, Gluten, Lactose, No_Sugar, Nut, Shellfish, Vegan};

        String lose = "";
        for (int i = 0; i < arrayLose.length; i++) {
            if (!arrayLose[i].equals("")) {
                lose = lose + "," + arrayLose[i];
            }
        }

        txt_name = findViewById(R.id.txt_name);
        txt_password = findViewById(R.id.txt_password);
        txt_email = findViewById(R.id.txt_email);
        txt_short_name = findViewById(R.id.txt_short_name);
        txt_type = findViewById(R.id.txt_type);
        txt_gender = findViewById(R.id.txt_gender);
        txt_height = findViewById(R.id.txt_height);
        txt_weight = findViewById(R.id.txt_weight);
        txt_age = findViewById(R.id.txt_age);
        txt_lose = findViewById(R.id.txt_lose);
        txt_exercise = findViewById(R.id.txt_exercise);
        image_profile = findViewById(R.id.image_profile);
        btn_choose_photo = findViewById(R.id.btn_choose_photo);

        txt_name.setText(Name);
        txt_email.setText(Email);
        txt_password.setText(Password);
        txt_type.setText(Type);
        txt_gender.setText(Gender);

        txt_age.setText(Age);
        txt_height.setText(Height + " cm.");
        txt_weight.setText(Weight + " kg.");

        if (!lose.isEmpty()) {
            if (lose.substring(0, 1).equals(",")) {
                txt_lose.setText(lose.substring(1));
            }
        } else {
            txt_lose.setText("No Allergy Found");
        }

        if (!Photo.equals("")) {
            txt_short_name.setVisibility(View.GONE);
            Glide.with(UserDetailActivity.this).load(Photo).into(image_profile);
        } else {
            txt_short_name.setText(Name.substring(0, 1));
        }

        if (Exercise.equals("1")) {
            txt_exercise.setText("Little to no exercise");
        } else if (Exercise.equals("2")) {
            txt_exercise.setText("Exercise 1-3 times a week");
        }  else if (Exercise.equals("3")) {
            txt_exercise.setText("Exercise 4-5 times a week");
        }  else if (Exercise.equals("4")) {
            txt_exercise.setText("Daily exercise or intense exercise 4-5 times a week");
        }  else if (Exercise.equals("5")) {
            txt_exercise.setText("Intense exercise 6-7 times a week");
        }

        LinearLayout layout_edit = findViewById(R.id.layout_edit);
        layout_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tt = new Intent(UserDetailActivity.this, UserEditActivity.class);
                tt.putExtra("user_id", User_id);
                tt.putExtra("email", Email);
                tt.putExtra("name", Name);
                tt.putExtra("password", Password);
                tt.putExtra("gender", Gender);
                tt.putExtra("age", Age);
                tt.putExtra("height", String.valueOf(Height));
                tt.putExtra("weight", String.valueOf(Weight));
                tt.putExtra("corn", Corn);
                tt.putExtra("fluctose", Fluctose);
                tt.putExtra("gluten", Gluten);
                tt.putExtra("lactose", Lactose);
                tt.putExtra("no_sugar", No_Sugar);
                tt.putExtra("nut", Nut);
                tt.putExtra("shellfish", Shellfish);
                tt.putExtra("vegan", Vegan);
                tt.putExtra("exercise", Exercise);
                startActivity(tt);
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}