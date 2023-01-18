package com.fluke.allergyfinder.UserData;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.fluke.allergyfinder.Admin.MainAdminActivity;
import com.fluke.allergyfinder.LoginActivity;
import com.fluke.allergyfinder.R;
import com.fluke.allergyfinder.RegisterActivity2;
import com.fluke.allergyfinder.SharedPreferences.AppPreferences;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class UserAddActivity2 extends AppCompatActivity {

    CheckBox chk_gluten, chk_lactose, chk_nut, chk_shellfish, chk_corn, chk_fluctose, chk_vegan, chk_no_sugar;
    RadioButton radio1, radio2, radio3, radio4, radio5;
    String email, name, password, age, weight, height, gender;
    double mbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Add Page");

        Intent tt = getIntent();
        email = tt.getStringExtra("email");
        name = tt.getStringExtra("name");
        password = tt.getStringExtra("password");
        age = tt.getStringExtra("age");
        height = tt.getStringExtra("height");
        weight = tt.getStringExtra("weight");
        gender = tt.getStringExtra("gender");

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
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void Submit(View view) {
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
            fluctose = "fluctose";
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
        if (gender.equals("Male")) {
            if (radio1.isChecked()) {
                exercise = "1";
                mbr = (10 * Double.parseDouble(weight) + (6.25 * Double.parseDouble(height)) - (5 * Double.parseDouble(age)) + 5) * 1.2;
            } else if (radio2.isChecked()) {
                exercise = "2";
                mbr = (10 * Double.parseDouble(weight) + (6.25 * Double.parseDouble(height)) - (5 * Double.parseDouble(age)) + 5) * 1.375;
            } else if (radio3.isChecked()) {
                exercise = "3";
                mbr = (10 * Double.parseDouble(weight) + (6.25 * Double.parseDouble(height)) - (5 * Double.parseDouble(age)) + 5) * 1.55;
            } else if (radio4.isChecked()) {
                exercise = "4";
                mbr = (10 * Double.parseDouble(weight) + (6.25 * Double.parseDouble(height)) - (5 * Double.parseDouble(age)) + 5) * 1.725;
            } else if (radio5.isChecked()) {
                exercise = "5";
                mbr = (10 * Double.parseDouble(weight) + (6.25 * Double.parseDouble(height)) - (5 * Double.parseDouble(age)) + 5) * 1.9;
            }
        } else {
            if (radio1.isChecked()) {
                exercise = "1";
                mbr = (10 * Double.parseDouble(weight) + (6.25 * Double.parseDouble(height)) - (5 * Double.parseDouble(age)) - 161) * 1.2;
            } else if (radio2.isChecked()) {
                exercise = "2";
                mbr = (10 * Double.parseDouble(weight) + (6.25 * Double.parseDouble(height)) - (5 * Double.parseDouble(age)) - 161) * 1.375;
            } else if (radio3.isChecked()) {
                exercise = "3";
                mbr = (10 * Double.parseDouble(weight) + (6.25 * Double.parseDouble(height)) - (5 * Double.parseDouble(age)) - 161) * 1.55;
            } else if (radio4.isChecked()) {
                exercise = "4";
                mbr = (10 * Double.parseDouble(weight) + (6.25 * Double.parseDouble(height)) - (5 * Double.parseDouble(age)) - 161) * 1.725;
            } else if (radio5.isChecked()) {
                exercise = "5";
                mbr = (10 * Double.parseDouble(weight) + (6.25 * Double.parseDouble(height)) - (5 * Double.parseDouble(age)) - 161) * 1.9;
            }
        }

        double carbohydrate = (mbr * 0.6) / 4;
        double protein = (mbr * 0.1) / 4;
        double fat = (mbr * 0.3) / 4;
        double sugar = (mbr * 0.1) / 4;
        double calories = mbr;

        //Toast.makeText(this, String.format("%.0f", carbohydrate) + " " + String.format("%.0f", protein) + " " + String.format("%.0f", fat) + " " + String.format("%.0f", sugar) + " " + String.format("%.0f", calories), Toast.LENGTH_SHORT).show();

        final ProgressDialog pd = new ProgressDialog(UserAddActivity2.this);
        pd.setMessage("A Moment...");
        pd.show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection("user").document();
        String userId = ref.getId();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("user_id", userId);
        hashMap.put("email", email);
        hashMap.put("name", name);
        hashMap.put("password", password);
        hashMap.put("age", age);
        hashMap.put("type", "user");
        hashMap.put("daily_calories", Integer.parseInt(String.format("%.0f", mbr)));
        hashMap.put("daily_protein", Integer.parseInt(String.format("%.0f", protein)));
        hashMap.put("daily_carbohydrate", Integer.parseInt(String.format("%.0f", carbohydrate)));
        hashMap.put("daily_fat", Integer.parseInt(String.format("%.0f", fat)));
        hashMap.put("daily_sugar", Integer.parseInt(String.format("%.0f", sugar)));
        hashMap.put("daily_sodium", 2300);
        hashMap.put("height", Integer.parseInt(height));
        hashMap.put("weight", Integer.parseInt(weight));
        hashMap.put("gender", gender);
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
        hashMap.put("dateTime", FieldValue.serverTimestamp());

        db.collection("user").document(userId).set(hashMap);

//        AppPreferences appPreferences = new AppPreferences(UserAddActivity2.this);
//        appPreferences.setBooleanPrefs(AppPreferences.KEY_SAVE_USER, true);
//        appPreferences.setStringPrefs(AppPreferences.KEY_USER_ID, userId);
//        appPreferences.setStringPrefs(AppPreferences.KEY_EMAIL, email);
//        appPreferences.setStringPrefs(AppPreferences.KEY_PASSWORD, password);
//        appPreferences.setStringPrefs(AppPreferences.KEY_NAME, name);
//        appPreferences.setStringPrefs(AppPreferences.KEY_PHOTO, "");
//        appPreferences.setStringPrefs(AppPreferences.KEY_TYPE, "user");
//        appPreferences.setStringPrefs(AppPreferences.KEY_GENDER, gender);
//        appPreferences.setStringPrefs(AppPreferences.KEY_AGE, age);
//        appPreferences.setStringPrefs(AppPreferences.KEY_CORN, corn);
//        appPreferences.setStringPrefs(AppPreferences.KEY_FLUCTOSE, fluctose);
//        appPreferences.setStringPrefs(AppPreferences.KEY_GLUTEN, gluten);
//        appPreferences.setIntPrefs(AppPreferences.KEY_HEIGHT, Integer.parseInt(height));
//        appPreferences.setStringPrefs(AppPreferences.KEY_LACTOSE, lactose);
//        appPreferences.setStringPrefs(AppPreferences.KEY_NO_SUGAR, no_sugar);
//        appPreferences.setStringPrefs(AppPreferences.KEY_NUT, nut);
//        appPreferences.setStringPrefs(AppPreferences.KEY_SHELLFISH, shellfish);
//        appPreferences.setStringPrefs(AppPreferences.KEY_VEGAN, vegan);
//        appPreferences.setStringPrefs(AppPreferences.KEY_EXERCISE, exercise);
//        appPreferences.setIntPrefs(AppPreferences.KEY_WEIGHT, Integer.parseInt(weight));
//
//        appPreferences.setIntPrefs(AppPreferences.KEY_DAILY_CALORIES, Integer.parseInt(String.format("%.0f", calories)));
//        appPreferences.setIntPrefs(AppPreferences.KEY_DAILY_PROTEIN, Integer.parseInt(String.format("%.0f", protein)));
//        appPreferences.setIntPrefs(AppPreferences.KEY_DAILY_CARBOHYDRATE, Integer.parseInt(String.format("%.0f", carbohydrate)));
//        appPreferences.setIntPrefs(AppPreferences.KEY_DAILY_FAT, Integer.parseInt(String.format("%.0f", fat)));
//        appPreferences.setIntPrefs(AppPreferences.KEY_DAILY_SUGAR, Integer.parseInt(String.format("%.0f", sugar)));
//        appPreferences.setIntPrefs(AppPreferences.KEY_DAILY_SODIUM, 2300);

        Toast.makeText(UserAddActivity2.this, "Register completed", Toast.LENGTH_SHORT).show();
        pd.dismiss();
        Intent intent = new Intent(UserAddActivity2.this, MainAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("from", "register");
        startActivity(intent);
    }
}