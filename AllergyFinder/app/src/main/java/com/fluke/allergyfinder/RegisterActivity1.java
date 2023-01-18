package com.fluke.allergyfinder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.fluke.allergyfinder.SharedPreferences.AppPreferences;

import java.util.HashMap;

public class RegisterActivity1 extends AppCompatActivity {

    EditText edt_email, edt_password, edt_password_confirm, edt_name, edt_age, edt_height, edt_weight;
    ImageView img_visible1, img_visible2, img_close1, img_close2, img_close3, img_close4, img_close5;
    Spinner spinner_gender;
    AppPreferences appPreferences;
    boolean visiblePassword1 = true, visiblePassword2 = true;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register Page");
        appPreferences = new AppPreferences(RegisterActivity1.this);

        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        edt_password_confirm = findViewById(R.id.edt_password_confirm);
        edt_name = findViewById(R.id.edt_name);
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

        EditText array_edt[] = {edt_email, edt_name, edt_age, edt_height, edt_weight};
        ImageView array_img[] = {img_close1, img_close2, img_close3, img_close4, img_close5};

        for (int i = 0; i < array_edt.length; i++) {
            int finalI = i;
            array_edt[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count != 0) {
                        array_img[finalI].setVisibility(View.VISIBLE);
                    } else {
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

    public static boolean checkLength(String pass){

        return pass.length() < 6;
    }

    public static boolean checkAge(String age){

        return age.isEmpty();
    }
    public static boolean checkHeight(String height){

        return height.isEmpty();
    }

    public static boolean checkWeight(String weight){

        return weight.isEmpty();
    }

    public void Register(View view) {
        Utils.hideSoftKeyboard(RegisterActivity1.this);
        String Email = edt_email.getText().toString().trim();
        String Password = edt_password.getText().toString().trim();
        String Name = edt_name.getText().toString().trim();
        String Age = edt_age.getText().toString().trim();
        String Height = edt_height.getText().toString().trim();
        String Weight = edt_weight.getText().toString().trim();
        String Gender = spinner_gender.getSelectedItem().toString().trim();

        if (checkEmail(Email)) {
            edt_email.setError("Please enter your email");
            edt_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            edt_email.setError("Please check your email");
            edt_email.requestFocus();
            return;
        }

        if (checkName(Name)) {
            edt_name.setError("Please enter your name");
            edt_name.requestFocus();
            return;
        }
        if (checkPassword(Password)) {
            edt_password.setError("Please enter your password");
            edt_password.requestFocus();
            return;
        }
        if (checkLength(Password)) {
            edt_password.setError("Password must be at least 6 characters");
            edt_password.requestFocus();
            return;
        }
        if (checkAge(Age)) {
            edt_age.setError("Please enter your age");
            edt_age.requestFocus();
            return;
        }

        if (checkHeight(Height)) {
            edt_height.setError("Please enter your height");
            edt_height.requestFocus();
            return;
        }

        if (checkWeight(Weight)) {
            edt_weight.setError("Please enter your weight");
            edt_weight.requestFocus();
            return;
        }

        if (Utils.isNetworkConnected(RegisterActivity1.this)) {
            Register(Email, Password, Name, Age, Height, Weight, Gender, RegisterActivity1.this);
        }
    }

    public void Register(String email, String password, String name, String age, String weight, String height, String gender, Context context) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("A moment...");
        pd.show();

        pd.dismiss();
        Intent intent = new Intent(context, RegisterActivity2.class);
        intent.putExtra("email", email);
        intent.putExtra("name", name);
        intent.putExtra("password", password);
        intent.putExtra("age", age);
        intent.putExtra("weight", weight);
        intent.putExtra("height", height);
        intent.putExtra("gender", gender);
        context.startActivity(intent);

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

    public void Close1(View view) {
        edt_email.setText("");
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