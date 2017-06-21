package com.example.lars.rentafilmapplication.Presentation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.lars.rentafilmapplication.DataAccess.FilmAPIConnector;
import com.example.lars.rentafilmapplication.Domain.Film;
import com.example.lars.rentafilmapplication.R;

import java.util.ArrayList;

import static com.example.lars.rentafilmapplication.Service.Config.URL_GETMOVIES;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, FilmAPIConnector.OnFilmAvailable {

    private final String TAG = this.getClass().getSimpleName();
    private ArrayList<Film> films = new ArrayList<>();
    private Toolbar toolbar;
    private ListView filmListView;
    private FilmAdapter filmAdapter;
    private EditText searchFld;
    private FilmAPIConnector getFilms;
    private Button searchBtn;


    //String to retrieve from field
    private String entry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        toolbar.setTitle(R.string.home_toolbar);
        setSupportActionBar(toolbar);

        getFilms = new FilmAPIConnector(this, this);
        getFilms.execute(URL_GETMOVIES);

        filmListView = (ListView) findViewById(R.id.filmListView);
        filmListView.setOnItemClickListener(this);

        filmAdapter = new FilmAdapter(getApplicationContext(), getLayoutInflater(), films);
        filmListView.setAdapter(filmAdapter);

        //Search textfield + button
        searchFld = (EditText) findViewById(R.id.searchInput);
        searchBtn = (Button) findViewById(R.id.searchButton);

        //ActionListener for button, get text from field
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                films.clear();
                entry = searchFld.getText().toString();
                getFilm();
            }
        });
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

            default:
                return super.onOptionsItemSelected(item);
        }
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
        FilmAPIConnector task = new FilmAPIConnector(this, this);
        String[] urls = new String[]{ URL_GETMOVIES + entry};
        task.execute(urls);
    }


    @Override
    public void onBackPressed() {
        // zorg dat je back niet kan gebruiken
    }

    @Override
    public void onFilmAvailable(Film film) {

        films.add(film);
        Log.i(TAG, "Film fetched (" + film.toString() + ")");
        filmAdapter.notifyDataSetChanged();
    }
}
