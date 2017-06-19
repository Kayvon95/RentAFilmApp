package com.example.lars.rentafilmapplication.Presentation;

import android.content.Context;
import android.graphics.Movie;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lars.rentafilmapplication.Domain.Film;
import com.example.lars.rentafilmapplication.R;

import java.util.ArrayList;

/**
 * Created by Kayvon Rahimi on 18-6-2017.
 */

public class FilmAdapter extends BaseAdapter {

    private final String TAG = this.getClass().getSimpleName();
    private Context context;
    private LayoutInflater inflater;
    private ArrayList filmArrayList;

    public FilmAdapter(Context context, LayoutInflater inflater, ArrayList<Film> filmArrayList){

        this.context = context;
        this.inflater = inflater;
        this.filmArrayList = filmArrayList;
    }

    public int getCount() {
        int size = filmArrayList.size();
        return size;
    }

    public Object getItem(int position) {
        Log.i(TAG, "getItem: " + position);
        return filmArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(TAG, "getView: " + position);

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_listitem_film, null);

            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.filmTitleTextView);
            viewHolder.rating = (TextView) convertView.findViewById(R.id.filmRatingTextView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Film film = (Film) filmArrayList.get(position);

        viewHolder.title.setText(film.getTitle());
        viewHolder.rating.setText(film.getRating());

        return convertView;
    }

    private static class ViewHolder {
        public TextView filmId;
        public TextView title;
        public TextView description;
        public TextView releaseYear;
        public TextView langId;
        public TextView originalLangId;

        public TextView rentalDuration;
        public TextView length;
        public TextView replacementCost;
        public TextView rating;
        public TextView features;
        public TextView lastUpdate;
    }

}
