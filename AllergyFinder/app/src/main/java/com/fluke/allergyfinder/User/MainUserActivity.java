package com.fluke.allergyfinder.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.fluke.allergyfinder.CaptureAct;
import com.fluke.allergyfinder.LoginActivity;
import com.fluke.allergyfinder.Product.ProductDetailActivity;
import com.fluke.allergyfinder.R;
import com.fluke.allergyfinder.SharedPreferences.AppPreferences;
import com.fluke.allergyfinder.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;


public class MainUserActivity extends AppCompatActivity {

    private BottomBar bottomBar;
    Fragment selectedFragment = null;
    String User_id;
    AppPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        appPreferences = new AppPreferences(MainUserActivity.this);
        User_id = appPreferences.getStringPrefs(AppPreferences.KEY_USER_ID);
        String Name = appPreferences.getStringPrefs(AppPreferences.KEY_NAME);

        //Toast.makeText(this, Latitude + " " + Longitude, Toast.LENGTH_SHORT).show();

        Intent tt = getIntent();
        String From = tt.getStringExtra("from");
        if (From.equals("edituser")) {
            selectedFragment = AccountFragment.newInstance();
            getSupportActionBar().setTitle("Account");
        } else {
            selectedFragment = HomeFragment.newInstance();
            getSupportActionBar().setTitle("Main");
        }

        bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.nav_profile) { // กด Home ไป HomeActivity
                    selectedFragment = AccountFragment.newInstance();
                    getSupportActionBar().setTitle("Profile");
                } else if (tabId == R.id.nav_search) {
                    selectedFragment = SearchMainFragment.newInstance();
                    getSupportActionBar().setTitle("Search");
                } else if (tabId == R.id.nav_bookmark) {
                    selectedFragment = BookmarkFragment.newInstance();
                    getSupportActionBar().setTitle("Bookmark");
                } else if (tabId == R.id.nav_track) {
                    selectedFragment = TrackFragment.newInstance();
                    getSupportActionBar().setTitle("Track");
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentContainer, selectedFragment);
                transaction.commit();
            }
        });
    }

    public void onBackPressed() {
        Logout();
    }

    private void Logout() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainUserActivity.this);
        dialog.setTitle("Alert");
        dialog.setIcon(android.R.drawable.btn_star_big_on);
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to exit?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent logout = new Intent(MainUserActivity.this, LoginActivity.class);
                logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logout);
                appPreferences.clearPrefs(); // clear session login
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void Scan(View view) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("Scan a barcode or QR Code");
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setCaptureActivity(CaptureAct.class);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("favorite")
                        .whereEqualTo("barcode", intentResult.getContents().trim())
                        .whereEqualTo("user_id", User_id).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().size() == 0) {
                                        Intent tt = new Intent(MainUserActivity.this, ProductDetailActivity.class);
                                        tt.putExtra("barcode", intentResult.getContents());
                                        tt.putExtra("favorite", "false");
                                        startActivity(tt);
                                    } else {
                                        Intent tt = new Intent(MainUserActivity.this, ProductDetailActivity.class);
                                        tt.putExtra("barcode", intentResult.getContents());
                                        tt.putExtra("favorite", "true");
                                        startActivity(tt);
                                    }
                                }
                            }
                        });
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Utils.Logout(MainUserActivity.this);
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }
}