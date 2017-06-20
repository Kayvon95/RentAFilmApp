package com.example.lars.rentafilmapplication.Presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.lars.rentafilmapplication.Domain.Film;
import com.example.lars.rentafilmapplication.R;

public class FilmDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView idTextView, titleTextView, descriptionTextView, releaseYearTextView, lengthTextView, ratingTextView, featuresTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        toolbar.setTitle(R.string.filmdetail_toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();

        Film selectedFilm = (Film) i.getSerializableExtra("Film");

        idTextView = (TextView) findViewById(R.id.idTextView);
        titleTextView = (TextView) findViewById(R.id.filmTitleTextView);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextview);
        releaseYearTextView = (TextView) findViewById(R.id.releaseYearTextView);
        lengthTextView = (TextView) findViewById(R.id.lengthTextView);
        ratingTextView = (TextView) findViewById(R.id.ratingTextView);
        featuresTextView = (TextView) findViewById(R.id.featuresTextView);
//
//        idTextView.setText(selectedFilm.getFilmId());
//        titleTextView.setText(selectedFilm.getTitle());
//        descriptionTextView.setText(selectedFilm.getDescription());
//        releaseYearTextView.setText(selectedFilm.getReleaseYear()+ "");
//        lengthTextView.setText(selectedFilm.getLength()+ "");
//        ratingTextView.setText(selectedFilm.getRating());
//        featuresTextView.setText(selectedFilm.getFeatures());

    }
}
