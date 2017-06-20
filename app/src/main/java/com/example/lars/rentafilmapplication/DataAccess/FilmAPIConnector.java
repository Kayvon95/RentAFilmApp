package com.example.lars.rentafilmapplication.DataAccess;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.example.lars.rentafilmapplication.Domain.Film;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Kayvon Rahimi on 19-6-2017.
 */

public class FilmAPIConnector extends AsyncTask<String, Void, String> {

    private final String TAG = getClass().getSimpleName();
    private OnFilmAvailable listener = null;
    private Context context;
    private ProgressDialog progressBar;

    public FilmAPIConnector(OnFilmAvailable listener, Context context){
        this.listener = listener;
        this.context = context;
    }

    // spinner voor 'laadscherm' tijdens het ophalen van de films
    protected void onPreExecute(){

            progressBar = new ProgressDialog(context);
            progressBar.setCancelable(false);
            progressBar.setMessage("Fetching Films");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setIndeterminate(true);
            progressBar.show();
    }

    protected String doInBackground(String... params) {
        //
        InputStream inputStream = null;
        int responseCode = -1;

        String movieUrl = params[0];
        String response = "";

        Log.i(TAG, "doInBackground - " + movieUrl);

        //Try-block to establish connection
        try {
            // Create productURL object
            URL url = new URL(movieUrl);
            // Open connection
            URLConnection urlConnection = url.openConnection();

            if (!(urlConnection instanceof HttpURLConnection)){
                return null;
            }

            HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
            httpConnection.setAllowUserInteraction(false);
            httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setRequestMethod("GET");

            httpConnection.connect();

            responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                inputStream = httpConnection.getInputStream();
                response = getStringFromInputStream(inputStream);
            } else {
                Log.e(TAG, "Error, invalid response");
            }

            //Exception catching and logging
        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground MalformedURL " + e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
            Log.e(TAG, "doInBackground IOException " + e.getLocalizedMessage());
            return null;
        }catch (Exception e) {
            Log.e(TAG, "doInBackground Exception " + e.getLocalizedMessage());
            return null;
        }
        return response;
    }

    //Handle doInBackground method results, retrieve from json, fit form of Product-class
    protected void onPostExecute(String response) {

        if (response == null || response == ""){
            Log.e(TAG, "onPostExecute got empty response");
        } else {

            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(response);

                JSONArray items = jsonObject.getJSONArray("items");
                for (int idx = 0; idx < items.length(); idx++) {
                    JSONObject film = items.getJSONObject(idx);

                    int filmId = film.getInt("film_id");
                    String title = film.getString("title");
                    String description = film.getString("description");
                    int releaseYear = film.getInt("release_year");
                    int langId = film.getInt("language_id");
                    int rentalDuration = film.getInt("rental_duration");
                    int length = film.getInt("length");
                    String rating = film.getString("rating");
                    String features = film.getString("special_features");
                    String lastUpdate = film.getString("last_update");

                    Film f = new Film(filmId, title, description, releaseYear, langId,
                            rentalDuration, length, rating, features, lastUpdate);


                    listener.onFilmAvailable(f);
                }

                progressBar.dismiss();
            } catch (JSONException ex) {
                Log.e(TAG, "onPostExecute JSONException " + ex.getLocalizedMessage());
            }
        }
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    // Call back interface
    public interface OnFilmAvailable {
        void onFilmAvailable(Film film);
    }
}
