package com.fluke.allergyfinder.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.fluke.allergyfinder.EditUserActivity;
import com.fluke.allergyfinder.R;
import com.fluke.allergyfinder.SharedPreferences.AppPreferences;
import com.fluke.allergyfinder.User.MainUserActivity;
import com.fluke.allergyfinder.Utils;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class EditAdminActivity extends AppCompatActivity {

    EditText edt_email, edt_password, edt_password_confirm, edt_name, edt_age;
    ImageView img_visible1, img_visible2, img_close1, img_close2, img_close3;
    Spinner spinner_gender;
    String User_id, Name, Password, Email, Age, Gender;

    AppPreferences appPreferences;
    boolean visiblePassword1 = true, visiblePassword2 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Admin Page");
        appPreferences = new AppPreferences(EditAdminActivity.this);

        Intent tt = getIntent();
        User_id = tt.getStringExtra("user_id");
        Email = tt.getStringExtra("email");
        Name = tt.getStringExtra("name");
        Password = tt.getStringExtra("password");
        Age = tt.getStringExtra("age");
        Gender = tt.getStringExtra("gender");

        edt_name = findViewById(R.id.edt_name);
        edt_password = findViewById(R.id.edt_password);
        edt_email = findViewById(R.id.edt_email);
        edt_age = findViewById(R.id.edt_age);
        img_visible1 = findViewById(R.id.img_visible1);
        img_visible2 = findViewById(R.id.img_visible2);
        img_close1 = findViewById(R.id.img_close1);
        img_close2 = findViewById(R.id.img_close2);
        img_close3 = findViewById(R.id.img_close3);
        spinner_gender = findViewById(R.id.spinner_gender);

        if (Gender.equals("Male")) {
            spinner_gender.setSelection(0);
        } else {
            spinner_gender.setSelection(1);
        }

        EditText array_edt[] = {edt_email, edt_name, edt_age};
        ImageView array_img[] = {img_close1, img_close2, img_close3};

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

        edt_name.setText(Name);
        edt_email.setText(Email);
        edt_age.setText(Age);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void EditUser(View view) {
        Utils.hideKeyboard(EditAdminActivity.this);
        String Name = edt_name.getText().toString().trim();
        String Password = edt_password.getText().toString().trim();
        String Email = edt_email.getText().toString().trim();
        String Age = edt_age.getText().toString().trim();
        String Gender = spinner_gender.getSelectedItem().toString().trim();

        if (Email.isEmpty()) {
            edt_email.setError("Please input your email first");
            edt_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            edt_email.setError("Please check your email !!!");
            edt_email.requestFocus();
            return;
        }

        if (Password.isEmpty()) {
            edt_password.setError("Please input your password first");
            edt_password.requestFocus();
            return;
        }

        if (Name.isEmpty()) {
            edt_name.setError("Please input your name.");
            edt_name.requestFocus();
            return;
        }

        if (Age.isEmpty()) {
            edt_age.setError("Please input your age.");
            edt_age.requestFocus();
            return;
        }

        if (Integer.parseInt(Age) >= 100) {
            edt_age.setError("Please check your age.");
            edt_age.requestFocus();
            return;
        }

        if (Utils.isNetworkConnected(EditAdminActivity.this)) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("email", Email);
            hashMap.put("name", Name);
            hashMap.put("password", Password);
            hashMap.put("age", Age);
            hashMap.put("gender", Gender);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("user").document(User_id).update(hashMap);

            appPreferences.setBooleanPrefs(AppPreferences.KEY_SAVE_USER, true);
            appPreferences.setStringPrefs(AppPreferences.KEY_USER_ID, User_id);
            appPreferences.setStringPrefs(AppPreferences.KEY_EMAIL, Email);
            appPreferences.setStringPrefs(AppPreferences.KEY_NAME, Name);
            appPreferences.setStringPrefs(AppPreferences.KEY_PASSWORD, Password);
            appPreferences.setStringPrefs(AppPreferences.KEY_GENDER, Gender);
            appPreferences.setStringPrefs(AppPreferences.KEY_AGE, Age);

            Intent intent = new Intent(EditAdminActivity.this, MainAdminActivity.class);
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

}