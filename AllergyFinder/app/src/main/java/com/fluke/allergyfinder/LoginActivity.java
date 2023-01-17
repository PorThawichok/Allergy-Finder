package com.fluke.allergyfinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fluke.allergyfinder.Admin.MainAdminActivity;
import com.fluke.allergyfinder.Model.User;
import com.fluke.allergyfinder.SharedPreferences.AppPreferences;
import com.fluke.allergyfinder.User.MainUserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class LoginActivity extends AppCompatActivity {

    EditText edt_email, edt_password;
    ImageView img_visible1;
    AppPreferences appPreferences;
    boolean visiblePassword = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        appPreferences = new AppPreferences(LoginActivity.this);

        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        img_visible1 = findViewById(R.id.img_visible1);
    }

    public static boolean checkEmail(String email){

        return email.isEmpty();
    }

    public static boolean checkPassword(String password){

        return password.isEmpty();
    }


    public void Login(View view) {
        String Email = edt_email.getText().toString().trim();
        String Password = edt_password.getText().toString().trim();

        if (checkEmail(Email)) {
            edt_email.setError("Please enter your email");
            edt_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            edt_email.setError("Please check your email!!!");
            edt_email.requestFocus();
            return;
        }

        if (checkPassword(Password)) {
            edt_password.setError("Please enter your password");
            edt_password.requestFocus();
            return;
        }

        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage("A moment...");
        pd.show();

        if (Utils.isNetworkConnected(LoginActivity.this)) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("user")
                    .whereEqualTo("email", Email)
                    .whereEqualTo("password", Password)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User user = document.toObject(User.class);

                                    appPreferences.setBooleanPrefs(AppPreferences.KEY_SAVE_USER, true);
                                    appPreferences.setStringPrefs(AppPreferences.KEY_USER_ID, user.getUser_id());
                                    appPreferences.setStringPrefs(AppPreferences.KEY_EMAIL, user.getEmail());
                                    appPreferences.setStringPrefs(AppPreferences.KEY_PASSWORD, user.getPassword());
                                    appPreferences.setStringPrefs(AppPreferences.KEY_NAME, user.getName());
                                    appPreferences.setStringPrefs(AppPreferences.KEY_PHOTO, user.getPhoto());
                                    appPreferences.setStringPrefs(AppPreferences.KEY_TYPE, user.getType());
                                    appPreferences.setStringPrefs(AppPreferences.KEY_GENDER, user.getGender());
                                    appPreferences.setStringPrefs(AppPreferences.KEY_AGE, user.getAge());
                                    appPreferences.setStringPrefs(AppPreferences.KEY_CORN, user.getCorn());
                                    appPreferences.setStringPrefs(AppPreferences.KEY_FLUCTOSE, user.getFluctose());
                                    appPreferences.setStringPrefs(AppPreferences.KEY_GLUTEN, user.getGluten());
                                    appPreferences.setIntPrefs(AppPreferences.KEY_HEIGHT, user.getHeight());
                                    appPreferences.setStringPrefs(AppPreferences.KEY_LACTOSE, user.getLactose());
                                    appPreferences.setStringPrefs(AppPreferences.KEY_NO_SUGAR, user.getNo_sugar());
                                    appPreferences.setStringPrefs(AppPreferences.KEY_NUT, user.getNut());
                                    appPreferences.setStringPrefs(AppPreferences.KEY_SHELLFISH, user.getShellfish());
                                    appPreferences.setStringPrefs(AppPreferences.KEY_VEGAN, user.getVegan());
                                    appPreferences.setIntPrefs(AppPreferences.KEY_WEIGHT, user.getWeight());
                                    appPreferences.setStringPrefs(AppPreferences.KEY_EXERCISE, user.getExercise());

                                    appPreferences.setIntPrefs(AppPreferences.KEY_DAILY_CALORIES, user.getDaily_calories());
                                    appPreferences.setIntPrefs(AppPreferences.KEY_DAILY_PROTEIN, user.getDaily_protein());
                                    appPreferences.setIntPrefs(AppPreferences.KEY_DAILY_CARBOHYDRATE, user.getDaily_carbohydrate());
                                    appPreferences.setIntPrefs(AppPreferences.KEY_DAILY_FAT, user.getDaily_fat());
                                    appPreferences.setIntPrefs(AppPreferences.KEY_DAILY_SUGAR, user.getDaily_sugar());
                                    appPreferences.setIntPrefs(AppPreferences.KEY_DAILY_SODIUM, user.getDaily_sodium());
                                    pd.dismiss();

                                    if (user.getType().equals("admin")) {
                                        Intent tt = new Intent(LoginActivity.this, MainAdminActivity.class);
                                        tt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        tt.putExtra("from", "login");
                                        startActivity(tt);
                                        finish();
                                    } else {
                                        Intent tt = new Intent(LoginActivity.this, MainUserActivity.class);
                                        tt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        tt.putExtra("from", "login");
                                        startActivity(tt);
                                        finish();
                                    }
                                }
                            } else {
                                pd.dismiss();
                                Toast.makeText(LoginActivity.this, "Something wrong, Please try again", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (pd.isShowing()) {
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                        Toast.makeText(LoginActivity.this, "Something wrong, Please try again", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }, 4000);
                            }
                        }
                    });
        }
    }

    public void Register(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity1.class));
    }

    public void Visible_Login1(View view) {
        if (visiblePassword) {
            visiblePassword = false;
            edt_password.setTransformationMethod(null);
            img_visible1.setImageResource(R.drawable.ic_visibility_off);
        } else {
            visiblePassword = true;
            edt_password.setTransformationMethod(new PasswordTransformationMethod());
            img_visible1.setImageResource(R.drawable.ic_visibility);
        }
    }

    public void Forget_Password(View view) {
        startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
    }
}