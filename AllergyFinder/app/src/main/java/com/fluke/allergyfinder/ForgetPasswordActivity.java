package com.fluke.allergyfinder;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fluke.allergyfinder.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText send_email;
    Button btn_reset;
    TextView txt_password;
    ImageView img_close1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Forget Password page");

        send_email = findViewById(R.id.send_email);
        btn_reset = findViewById(R.id.btn_reset);
        img_close1 = findViewById(R.id.img_close1);
        txt_password = findViewById(R.id.txt_password);


        send_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != 0) {
                    img_close1.setVisibility(View.VISIBLE);
                } else {
                    img_close1.setVisibility(View.INVISIBLE);
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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public static boolean checkReset(String reset){

        return reset.equals("");
    }

    public void Reset(View view) {
        String email = send_email.getText().toString();

        if (checkReset(email)) {
            Toast.makeText(ForgetPasswordActivity.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);

                                txt_password.setVisibility(View.VISIBLE);
                                txt_password.setText("You password is: " + user.password);
                            }
                        } else {
                            Toast.makeText(ForgetPasswordActivity.this, "Something wrong, Please try again", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });

//        else {
//            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(ForgetPasswordActivity.this, "กรุณาตรวจสอบอีเมลล์", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
//                    } else {
//                        String error = task.getException().getMessage();
//                        Toast.makeText(ForgetPasswordActivity.this, error, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
    }

    public void Back_Intent(View view) {
        finish();
    }

    public void Close1(View view) {
        send_email.setText("");
    }
}
