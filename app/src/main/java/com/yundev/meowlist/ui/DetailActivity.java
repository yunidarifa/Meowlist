package com.yundev.meowlist.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yundev.meowlist.R;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.yundev.meowlist.R.layout.activity_detail);

        String imgDetail = getIntent().getStringExtra("intent_img");
        getSupportActionBar().setTitle("Detail Kucing");
        Picasso.get()
                .load(imgDetail)
                .into((ImageView) findViewById(R.id.img_detail));
    }
}