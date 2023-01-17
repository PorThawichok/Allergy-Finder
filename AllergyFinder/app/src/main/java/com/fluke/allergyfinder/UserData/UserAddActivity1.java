package com.fluke.allergyfinder.UserData;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.fluke.allergyfinder.R;
import com.fluke.allergyfinder.RegisterActivity1;
import com.fluke.allergyfinder.RegisterActivity2;
import com.fluke.allergyfinder.SharedPreferences.AppPreferences;
import com.fluke.allergyfinder.Utils;

public class UserAddActivity1 extends AppCompatActivity {

    EditText edt_email, edt_password, edt_password_confirm, edt_name, edt_age, edt_height, edt_weight;
    ImageView img_visible1, img_visible2, img_close1, img_close2, img_close3, img_close4, img_close5;
    Spinner spinner_gender;
    AppPreferences appPreferences;
    boolean visiblePassword1 = true, visiblePassword2 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Add Page");
        appPreferences = new AppPreferences(UserAddActivity1.this);

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

    public void Register(View view) {
        Utils.hideSoftKeyboard(UserAddActivity1.this);
        String Email = edt_email.getText().toString().trim();
        String Password = edt_password.getText().toString().trim();
        String Name = edt_name.getText().toString().trim();
        String Age = edt_age.getText().toString().trim();
        String Height = edt_height.getText().toString().trim();
        String Weight = edt_weight.getText().toString().trim();
        String Gender = spinner_gender.getSelectedItem().toString().trim();

        if (Email.isEmpty()) {
            edt_email.setError("Please enter your email");
            edt_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            edt_email.setError("Please check your email");
            edt_email.requestFocus();
            return;
        }

        if (Name.isEmpty()) {
            edt_name.setError("Please enter your name");
            edt_name.requestFocus();
            return;
        }
        if (Password.isEmpty()) {
            edt_password.setError("Please enter your password");
            edt_password.requestFocus();
            return;
        }
        if (Password.length() < 6) {
            edt_password.setError("Password must be at least 6 characters");
            edt_password.requestFocus();
            return;
        }
        if (Age.isEmpty()) {
            edt_age.setError("Please enter your age");
            edt_age.requestFocus();
            return;
        }

        if (Height.isEmpty()) {
            edt_height.setError("Please check your height");
            edt_height.requestFocus();
            return;
        }

        if (Weight.isEmpty()) {
            edt_weight.setError("Please check your weight");
            edt_weight.requestFocus();
            return;
        }

        if (Utils.isNetworkConnected(UserAddActivity1.this)) {
            Register(Email, Password, Name, Age, Height, Weight, Gender, UserAddActivity1.this);
        }
    }

    public void Register(String email, String password, String name, String age, String height, String weight, String gender, Context context) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("One Moment...");
        pd.show();

        pd.dismiss();
        Intent intent = new Intent(context, UserAddActivity2.class);
        intent.putExtra("email", email);
        intent.putExtra("name", name);
        intent.putExtra("password", password);
        intent.putExtra("age", age);
        intent.putExtra("height", height);
        intent.putExtra("weight", weight);
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