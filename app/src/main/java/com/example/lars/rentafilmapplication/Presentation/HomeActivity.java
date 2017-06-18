package com.example.lars.rentafilmapplication.Presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lars.rentafilmapplication.Domain.Film;
import com.example.lars.rentafilmapplication.R;


import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private final String TAG = this.getClass().getSimpleName();
    private ArrayList<Film> films = new ArrayList<>();
    private Toolbar toolbar;
    private ListView filmListView;
    private ArrayAdapter myFilmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.home_toolbar);


        films = new ArrayList<>();
        filmListView = (ListView) findViewById(R.id.filmListView);

        //Create adapter
        myFilmAdapter = new FilmAdapter(this, films);
        //bind adapter to listView
        filmListView.setAdapter(myFilmAdapter);
        //ActionListener
        filmListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Select film
        Film selectedFilm = (Film) films.get(position);

        //Verstuur de informatie van de geklikte film mee om de filmdetailactivity mee in te vullen
        Intent i = new Intent(getApplicationContext(), FilmDetailActivity.class);
        i.putExtra("Film", selectedFilm);
        startActivity(i);
    }

    public void getFilm() {
        // onzeApiConnector task = new onzeApiConnector(this); <-- Nog aanmaken
        String[] urls = new String[]{"https://localhost:9001/api/v1/films/"};
        //task.execute(urls);
    }


    @Override
    public void onBackPressed() {
        // zorg dat je back niet kan gebruiken
    }

    //@Override <--- Uncommenten wanneer de apiconnector klaar is
    public void filmSetAvailable(Film film) {

        films.add(film);
        Log.i(TAG, "Film fetched (" + film.toString() + ")");
        myFilmAdapter.notifyDataSetChanged();
    }
}
