package com.example.lars.rentafilmapplication.Presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lars.rentafilmapplication.Domain.Film;
import com.example.lars.rentafilmapplication.R;

import java.util.ArrayList;

/**
 * Created by Kayvon Rahimi on 18-6-2017.
 */

public class FilmAdapter extends ArrayAdapter<Film> {
    private final String TAG = this.getClass().getSimpleName();
    private ArrayList filmList;

    public FilmAdapter(Context context, ArrayList<Film> filmList) {
        super(context, R.layout.custom_listitem_film, filmList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater productInflater = LayoutInflater.from(getContext());
        View customView = productInflater.inflate(R.layout.custom_listitem_film, parent, false);

        Film singleFilm = getItem(position);

        TextView filmIdTextView = (TextView) customView.findViewById(R.id.filmIdTextView);
        TextView filmTitleTextView = (TextView) customView.findViewById(R.id.filmTitleTextView);
        TextView filmLengthTextView = (TextView) customView.findViewById(R.id.filmLengthTextView);
        TextView filmRatingTextView = (TextView) customView.findViewById(R.id.filmRatingTextView);

        filmIdTextView.setText(singleFilm.getFilmId());
        filmTitleTextView.setText(singleFilm.getTitle());
        filmLengthTextView.setText(singleFilm.getLength());
        filmRatingTextView.setText(singleFilm.getRating());

        return customView;
    }
}
