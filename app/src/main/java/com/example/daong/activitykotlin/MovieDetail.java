package com.example.daong.activitykotlin;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.daong.activitykotlin.R;

import org.w3c.dom.Text;

public class MovieDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Recievie data
        String title = getIntent().getExtras().getString("zombie_title");
        int year = getIntent().getExtras().getInt("zombie_year");
        String director = getIntent().getExtras().getString("zombie_director");
        String image_url = getIntent().getExtras().getString("zombie_image");
        String description = getIntent().getExtras().getString("zombie_description");

        //view
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);

        TextView zombie_title = findViewById(R.id.zombie_title);
        TextView zombie_year = findViewById(R.id.zombie_year);
        TextView zombie_director = findViewById(R.id.zombie_director);
        TextView zombie_description = findViewById(R.id.zombie_description);
        ImageView zombie_image = findViewById(R.id.zombie_thumbnail);

        //setting values to each view
        zombie_title.setText(title);
        zombie_year.setText(String.valueOf(year));
        zombie_director.setText(director);
        zombie_description.setText(description);

        collapsingToolbarLayout.setTitle(title);

        RequestOptions requestOption = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

        //setting image using Glide
        Glide.with(this).load(image_url).apply(requestOption).into(zombie_image);

    }
}
