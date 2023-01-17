package com.fluke.allergyfinder.Admin;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.fluke.allergyfinder.R;
import com.fluke.allergyfinder.User.MainUserActivity;
import com.fluke.allergyfinder.Utils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainAdminActivity extends AppCompatActivity {

    private BottomBar bottomBar;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.nav_home) { // กด Home ไป HomeActivity
                    selectedFragment = HomeAdminFragment.newInstance();
                    getSupportActionBar().setTitle("Home");

                } else if (tabId == R.id.nav_product) {
                    selectedFragment = ProductFragment.newInstance();
                    getSupportActionBar().setTitle("Product");

                } else if (tabId == R.id.nav_user) {
                    selectedFragment = UserFragment.newInstance();
                    getSupportActionBar().setTitle("User");

                }else if (tabId == R.id.nav_account) {
                    selectedFragment = AccountFragment.newInstance();
                    getSupportActionBar().setTitle("Account");

                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentContainer, selectedFragment);
                transaction.commit();
            }
        });

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
                Utils.Logout(MainAdminActivity.this);
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }
}