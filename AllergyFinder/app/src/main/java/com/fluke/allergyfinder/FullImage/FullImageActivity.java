package com.fluke.allergyfinder.FullImage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.fluke.allergyfinder.R;
import com.github.chrisbanes.photoview.PhotoView;

public class FullImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);

        getSupportActionBar().hide();
        Intent tt = getIntent();
        String Photo = tt.getStringExtra("photo");

        PhotoView imageView = findViewById(R.id.imageView1);

        Glide.with(FullImageActivity.this)
                .load(Photo)
                .into(imageView);
    }

    public void Close(View view) {
        finish();
    }
}
