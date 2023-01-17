package com.fluke.allergyfinder;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fluke.allergyfinder.Product.ProductAddActivity;
import com.fluke.allergyfinder.User.MainUserActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.fluke.allergyfinder.SharedPreferences.AppPreferences;

import org.checkerframework.checker.units.qual.A;

import java.util.Calendar;
import java.util.HashMap;

public class EditUserActivity extends AppCompatActivity {

    EditText edt_email, edt_password, edt_password_confirm, edt_name, edt_age, edt_height, edt_weight;
    ImageView img_visible1, img_visible2, img_close1, img_close2, img_close3, img_close4, img_close5;
    Spinner spinner_gender;
    CheckBox chk_gluten, chk_lactose, chk_nut, chk_shellfish, chk_corn, chk_fluctose, chk_vegan, chk_no_sugar;
    RadioButton radio1, radio2, radio3, radio4, radio5;
    String User_id, Name, Password, Email, Age, Gender, Corn, Fluctose, Gluten, Lactose, No_Sugar, Nut, Shellfish, Vegan, Exercise, Height, Weight;

    AppPreferences appPreferences;
    boolean visiblePassword1 = true, visiblePassword2 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit User Page");
        appPreferences = new AppPreferences(EditUserActivity.this);

        Intent tt = getIntent();
        User_id = tt.getStringExtra("user_id");
        Email = tt.getStringExtra("email");
        Name = tt.getStringExtra("name");
        Password = tt.getStringExtra("password");
        Age = tt.getStringExtra("age");
        Gender = tt.getStringExtra("gender");
        Height = tt.getStringExtra("height");
        Weight = tt.getStringExtra("weight");
        Corn = tt.getStringExtra("corn");
        Fluctose = tt.getStringExtra("fluctose");
        Gluten = tt.getStringExtra("gluten");
        Lactose = tt.getStringExtra("lactose");
        No_Sugar = tt.getStringExtra("sugar");
        Nut = tt.getStringExtra("nut");
        Shellfish = tt.getStringExtra("shellfish");
        Vegan = tt.getStringExtra("vegan");
        Exercise = tt.getStringExtra("exercise");

        edt_name = findViewById(R.id.edt_name);
        edt_password = findViewById(R.id.edt_password);
        edt_email = findViewById(R.id.edt_email);
        edt_age = findViewById(R.id.edt_age);
        edt_height = findViewById(R.id.edt_height);
        edt_weight = findViewById(R.id.edt_weight);
        img_visible1 = findViewById(R.id.img_visible1);
        img_visible2 = findViewById(R.id.img_visible2);
        img_close1 = findViewById(R.id.img_close1);
        img_close2 = findViewById(R.id.img_close2);
        img_close3 = findViewById(R.id.img_close3);
        img_close4 = findViewById(R.id.img_close4);
        img_close5 = findViewById(R.id.img_close5);
        spinner_gender = findViewById(R.id.spinner_gender);
        chk_gluten = findViewById(R.id.chk_gluten);
        chk_lactose = findViewById(R.id.chk_lactose);
        chk_nut = findViewById(R.id.chk_nut);
        chk_shellfish = findViewById(R.id.chk_shellfish);
        chk_corn = findViewById(R.id.chk_corn);
        chk_fluctose = findViewById(R.id.chk_fluctose);
        chk_vegan = findViewById(R.id.chk_vegan);
        chk_no_sugar = findViewById(R.id.chk_no_sugar);
        radio1 = findViewById(R.id.radio1);
        radio2 = findViewById(R.id.radio2);
        radio3 = findViewById(R.id.radio3);
        radio4 = findViewById(R.id.radio4);
        radio5 = findViewById(R.id.radio5);

        if (Gender.equals("Male")) {
            spinner_gender.setSelection(0);
        } else {
            spinner_gender.setSelection(1);
        }

        EditText array_edt[] = {edt_email, edt_name, edt_age, edt_height, edt_weight};
        ImageView array_img[] = {img_close1, img_close2, img_close3, img_close4, img_close5};

        for (int i = 0; i < array_edt.length; i++) {
            int finalI = i;
            array_edt[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count != 0) {
                        array_img[0].setVisibility(View.GONE);
                        array_img[finalI].setVisibility(View.VISIBLE);
                    } else {
                        array_img[0].setVisibility(View.GONE);
                        array_img[finalI].setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    // TODO Auto-generated method stub
                }
            });
        }

        if (!Gluten.equals("")) {
            chk_gluten.setChecked(true);
        }

        if (!Lactose.equals("")) {
            chk_lactose.setChecked(true);
        }

        if (!Nut.equals("")) {
            chk_nut.setChecked(true);
        }

        if (!Shellfish.equals("")) {
            chk_shellfish.setChecked(true);
        }

        if (!Corn.equals("")) {
            chk_corn.setChecked(true);
        }

        if (!Fluctose.equals("")) {
            chk_fluctose.setChecked(true);
        }

        if (!Vegan.equals("")) {
            chk_vegan.setChecked(true);
        }

        if (!No_Sugar.equals("")) {
            chk_no_sugar.setChecked(true);
        }

        if (Exercise.equals("1")) {
            radio1.setChecked(true);
        } else if (Exercise.equals("2")) {
            radio2.setChecked(true);
        } else if (Exercise.equals("3")) {
            radio3.setChecked(true);
        } else if (Exercise.equals("4")) {
            radio4.setChecked(true);
        } else if (Exercise.equals("5")) {
            radio5.setChecked(true);
        }

        edt_name.setText(Name);
        edt_email.setText(Email);
        edt_age.setText(Age);
        edt_height.setText(Height);
        edt_weight.setText(Weight);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public static boolean checkEmail(String email){

        return email.isEmpty();
    }
    public static boolean checkPassword(String password){

        return password.isEmpty();
    }
    public static boolean checkName(String name){

        return name.isEmpty();
    }
    public static boolean checkAge(String age){

        return age.isEmpty();
    }
    public static boolean checkAgeNumber(String age){

        return Integer.parseInt(age) >= 100;
    }
    public static boolean checkHeight(String height){

        return height.isEmpty();
    }
    public static boolean checkHeightNumber(String height){

        return Integer.parseInt(height) >= 200;
    }

    public static boolean checkWeight(String weight){

        return weight.isEmpty();
    }
    public static boolean checkWeightNumber(String weight){

        return Integer.parseInt(weight) >= 110;
    }

    public void EditUser(View view) {
        Utils.hideKeyboard(EditUserActivity.this);
        String Name = edt_name.getText().toString().trim();
        String Password = edt_password.getText().toString().trim();
        String Email = edt_email.getText().toString().trim();
        String Age = edt_age.getText().toString().trim();
        String Height = edt_height.getText().toString().trim();
        String Weight = edt_weight.getText().toString().trim();
        String Gender = spinner_gender.getSelectedItem().toString().trim();
        double mbr = 0;

        if (checkEmail(Email)) {
            edt_email.setError("Please enter your email");
            edt_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            edt_email.setError("Please check your email !!!");
            edt_email.requestFocus();
            return;
        }

        if (checkPassword(Password)) {
            edt_password.setError("Please enter your password");
            edt_password.requestFocus();
            return;
        }

        if (checkName(Name)) {
            edt_name.setError("Please enter your name");
            edt_name.requestFocus();
            return;
        }

        if (checkAge(Age)) {
            edt_age.setError("Please enter your age");
            edt_age.requestFocus();
            return;
        }

        if (checkAgeNumber(Age)) {
            edt_age.setError("Please check your age");
            edt_age.requestFocus();
            return;
        }
        if (checkHeight(Height)) {
            edt_height.setError("Please enter your height");
            edt_height.requestFocus();
            return;
        }

        if (checkHeightNumber(Height)) {
            edt_height.setError("Please check your height");
            edt_height.requestFocus();
            return;
        }
        if (checkWeight(Weight)) {
            edt_weight.setError("Please enter your weight");
            edt_weight.requestFocus();
            return;
        }
        if (checkWeightNumber(Weight)) {
            edt_weight.setError("Please check your weight");
            edt_weight.requestFocus();
            return;
        }

        String gluten, lactose, nut, shellfish, corn, fluctose, vegan, no_sugar;

        if (!radio1.isChecked() && !radio2.isChecked() && !radio3.isChecked() && !radio4.isChecked() && !radio5.isChecked()) {
            Toast.makeText(this, "Please select exercise behavior", Toast.LENGTH_SHORT).show();
            return;
        }

        if (chk_gluten.isChecked()) {
            gluten = "gluten";
        } else {
            gluten = "";
        }

        if (chk_lactose.isChecked()) {
            lactose = "lactose";
        } else {
            lactose = "";
        }

        if (chk_nut.isChecked()) {
            nut = "nut";
        } else {
            nut = "";
        }

        if (chk_shellfish.isChecked()) {
            shellfish = "shellfish";
        } else {
            shellfish = "";
        }

        if (chk_corn.isChecked()) {
            corn = "corn";
        } else {
            corn = "";
        }

        if (chk_fluctose.isChecked()) {
            fluctose = "flutose";
        } else {
            fluctose = "";
        }

        if (chk_vegan.isChecked()) {
            vegan = "vegan";
        } else {
            vegan = "";
        }

        if (chk_no_sugar.isChecked()) {
            no_sugar = "no sugar";
        } else {
            no_sugar = "";
        }
        String exercise = null;
        if (Gender.equals("Male")) {
            if (radio1.isChecked()) {
                exercise = "1";
                mbr = (10 * Double.parseDouble(Weight) + (6.25 * Double.parseDouble(Height)) - (5 * Double.parseDouble(Age)) + 5) * 1.2;
            } else if (radio2.isChecked()) {
                exercise = "2";
                mbr = (10 * Double.parseDouble(Weight) + (6.25 * Double.parseDouble(Height)) - (5 * Double.parseDouble(Age)) + 5) * 1.375;
            } else if (radio3.isChecked()) {
                exercise = "3";
                mbr = (10 * Double.parseDouble(Weight) + (6.25 * Double.parseDouble(Height)) - (5 * Double.parseDouble(Age)) + 5) * 1.55;
            } else if (radio4.isChecked()) {
                exercise = "4";
                mbr = (10 * Double.parseDouble(Weight) + (6.25 * Double.parseDouble(Height)) - (5 * Double.parseDouble(Age)) + 5) * 1.725;
            } else if (radio5.isChecked()) {
                exercise = "5";
                mbr = (10 * Double.parseDouble(Weight) + (6.25 * Double.parseDouble(Height)) - (5 * Double.parseDouble(Age)) + 5) * 1.9;
            }
        } else {
            if (radio1.isChecked()) {
                exercise = "1";
                mbr = (10 * Double.parseDouble(Weight) + (6.25 * Double.parseDouble(Height)) - (5 * Double.parseDouble(Age)) - 161) * 1.2;
            } else if (radio2.isChecked()) {
                exercise = "2";
                mbr = (10 * Double.parseDouble(Weight) + (6.25 * Double.parseDouble(Height)) - (5 * Double.parseDouble(Age)) - 161) * 1.375;
            } else if (radio3.isChecked()) {
                exercise = "3";
                mbr = (10 * Double.parseDouble(Weight) + (6.25 * Double.parseDouble(Height)) - (5 * Double.parseDouble(Age)) - 161) * 1.55;
            } else if (radio4.isChecked()) {
                exercise = "4";
                mbr = (10 * Double.parseDouble(Weight) + (6.25 * Double.parseDouble(Height)) - (5 * Double.parseDouble(Age)) - 161) * 1.725;
            } else if (radio5.isChecked()) {
                exercise = "5";
                mbr = (10 * Double.parseDouble(Weight) + (6.25 * Double.parseDouble(Height)) - (5 * Double.parseDouble(Age)) - 161) * 1.9;
            }
        }

        double carbohydrate = (mbr * 0.6) / 4;
        double protein = (mbr * 0.1) / 4;
        double fat = (mbr * 0.3) / 4;
        double sugar = (mbr * 0.1) / 4;
        double calories = mbr;

        if (Utils.isNetworkConnected(EditUserActivity.this)) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("email", Email);
            hashMap.put("name", Name);
            hashMap.put("password", Password);
            hashMap.put("age", Age);
            hashMap.put("height", Integer.parseInt(Height));
            hashMap.put("weight", Integer.parseInt(Weight));
            hashMap.put("gender", Gender);
            hashMap.put("daily_calories", Integer.parseInt(String.format("%.0f", mbr)));
            hashMap.put("daily_protein", Integer.parseInt(String.format("%.0f", protein)));
            hashMap.put("daily_carbohydrate", Integer.parseInt(String.format("%.0f", carbohydrate)));
            hashMap.put("daily_fat", Integer.parseInt(String.format("%.0f", fat)));
            hashMap.put("daily_sugar", Integer.parseInt(String.format("%.0f", sugar)));
            hashMap.put("daily_sodium", 2300);
            hashMap.put("weight", Integer.parseInt(Weight));
            hashMap.put("height", Integer.parseInt(Height));
            hashMap.put("gender", Gender);
            hashMap.put("photo", "");
            hashMap.put("gluten", gluten);
            hashMap.put("lactose", lactose);
            hashMap.put("nut", nut);
            hashMap.put("shellfish", shellfish);
            hashMap.put("corn", corn);
            hashMap.put("fluctose", fluctose);
            hashMap.put("vegan", vegan);
            hashMap.put("no_sugar", no_sugar);
            hashMap.put("exercise", exercise);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("user").document(User_id).update(hashMap);

            appPreferences.setBooleanPrefs(AppPreferences.KEY_SAVE_USER, true);
            appPreferences.setStringPrefs(AppPreferences.KEY_USER_ID, User_id);
            appPreferences.setStringPrefs(AppPreferences.KEY_EMAIL, Email);
            appPreferences.setStringPrefs(AppPreferences.KEY_NAME, Name);
            appPreferences.setStringPrefs(AppPreferences.KEY_PASSWORD, Password);
            appPreferences.setStringPrefs(AppPreferences.KEY_GENDER, Gender);
            appPreferences.setStringPrefs(AppPreferences.KEY_AGE, Age);
            appPreferences.setStringPrefs(AppPreferences.KEY_CORN, corn);
            appPreferences.setStringPrefs(AppPreferences.KEY_FLUCTOSE, fluctose);
            appPreferences.setStringPrefs(AppPreferences.KEY_GLUTEN, gluten);
            appPreferences.setIntPrefs(AppPreferences.KEY_HEIGHT, Integer.parseInt(Height));
            appPreferences.setStringPrefs(AppPreferences.KEY_LACTOSE, lactose);
            appPreferences.setStringPrefs(AppPreferences.KEY_NO_SUGAR, no_sugar);
            appPreferences.setStringPrefs(AppPreferences.KEY_NUT, nut);
            appPreferences.setStringPrefs(AppPreferences.KEY_SHELLFISH, shellfish);
            appPreferences.setStringPrefs(AppPreferences.KEY_VEGAN, vegan);
            appPreferences.setStringPrefs(AppPreferences.KEY_EXERCISE, exercise);
            appPreferences.setIntPrefs(AppPreferences.KEY_WEIGHT, Integer.parseInt(Weight));

            appPreferences.setIntPrefs(AppPreferences.KEY_DAILY_CALORIES, Integer.parseInt(String.format("%.0f", calories)));
            appPreferences.setIntPrefs(AppPreferences.KEY_DAILY_PROTEIN, Integer.parseInt(String.format("%.0f", protein)));
            appPreferences.setIntPrefs(AppPreferences.KEY_DAILY_CARBOHYDRATE, Integer.parseInt(String.format("%.0f", carbohydrate)));
            appPreferences.setIntPrefs(AppPreferences.KEY_DAILY_FAT, Integer.parseInt(String.format("%.0f", fat)));
            appPreferences.setIntPrefs(AppPreferences.KEY_DAILY_SUGAR, Integer.parseInt(String.format("%.0f", sugar)));

            Toast.makeText(EditUserActivity.this, "Information updated", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditUserActivity.this, MainUserActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("from", "edituser");
            startActivity(intent);
            finish();
        }
    }


    public void Visible_Register1(View view) {
        if (visiblePassword1) {
            visiblePassword1 = false;
            edt_password.setTransformationMethod(null);
            img_visible1.setImageResource(R.drawable.ic_visibility_off);
        } else {
            visiblePassword1 = true;
            edt_password.setTransformationMethod(new PasswordTransformationMethod());
            img_visible1.setImageResource(R.drawable.ic_visibility);
        }
    }

    public void Visible_Register2(View view) {
        if (visiblePassword2) {
            visiblePassword2 = false;
            edt_password_confirm.setTransformationMethod(null);
            img_visible2.setImageResource(R.drawable.ic_visibility_off);
        } else {
            visiblePassword2 = true;
            edt_password_confirm.setTransformationMethod(new PasswordTransformationMethod());
            img_visible2.setImageResource(R.drawable.ic_visibility);
        }
    }


    public void Close2(View view) {
        edt_name.setText("");
    }

    public void Close3(View view) {
        edt_age.setText("");
    }

    public void Close4(View view) {
        edt_height.setText("");
    }

    public void Close5(View view) {
        edt_weight.setText("");
    }

}