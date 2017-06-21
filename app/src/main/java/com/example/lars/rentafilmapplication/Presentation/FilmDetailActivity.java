package com.example.lars.rentafilmapplication.Presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lars.rentafilmapplication.Domain.Film;
import com.example.lars.rentafilmapplication.R;

public class FilmDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView titleTextView, descriptionTextView, releaseYearTextView, lengthTextView, ratingTextView, featuresTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        toolbar.setTitle(R.string.filmdetail_toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();

        Film selectedFilm = (Film) i.getSerializableExtra("Film");

        titleTextView = (TextView) findViewById(R.id.filmTitleTextView);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextview);
        releaseYearTextView = (TextView) findViewById(R.id.releaseYearTextView);
        lengthTextView = (TextView) findViewById(R.id.lengthTextView);
        ratingTextView = (TextView) findViewById(R.id.ratingTextView);
        featuresTextView = (TextView) findViewById(R.id.featuresTextView);

        titleTextView.setText(selectedFilm.getTitle());
        descriptionTextView.setText(selectedFilm.getDescription());
        releaseYearTextView.setText(selectedFilm.getReleaseYear()+ "");
        lengthTextView.setText(selectedFilm.getLength()+ "");
        ratingTextView.setText(selectedFilm.getRating());
        featuresTextView.setText(selectedFilm.getFeatures());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rent_a_film_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.log_out:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            case R.id.home:
                Intent intent1 = new Intent(this, HomeActivity.class);
                startActivity(intent1);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
