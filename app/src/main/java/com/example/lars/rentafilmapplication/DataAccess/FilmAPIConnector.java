package com.example.lars.rentafilmapplication.DataAccess;

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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Kayvon Rahimi on 19-6-2017.
 */

public class FilmAPIConnector extends AsyncTask<String, Void, String> {

    private final String TAG = getClass().getSimpleName();
    private FilmListener listener;
    private Handler handler = new Handler();

    protected String doInBackground(String... params) {
        //
        InputStream inputStream = null;
        // url fetched from entry
        String productUrl = "";
        // Resulting response
        String response = "";
        BufferedReader reader = null;

        //Try-block to establish connection
        try {
            // Create productURL object
            URL url = new URL(params[0]);
            // Open connection
            URLConnection urlConnection = url.openConnection();
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while  ((line = reader.readLine()) != null) {
                response += line;
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
        }finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("doInBackGround IOExc", e.getLocalizedMessage());
                    return null;
                }
            }
        }
        // Het resultaat gaat naar de onPostExecute methode.
        return response;
    }

    //Handle doInBackground method results, retrieve from json, fit form of Product-class
    protected void onPostExecute(String response) {
        //Logging info
//        Log.i("onPostExecute ", response);

        //Parse JSON-results for requested results
        try {
            // Top level json object
            JSONObject jsonObject = new JSONObject(response);

            // Get all products, loop through them
            JSONArray films = jsonObject.getJSONArray("films");
            //The loop
            for(int i = 0; i < films.length(); i++) {

                // Get title, specsTag, summary, longDescription, smallImgUrl, largeImgUrl products:get json via index, convert index to String as variable
                int filmId = films.getJSONObject(i).optInt("film_id");
                String title = films.getJSONObject(i).optString("title");
                String description = films.getJSONObject(i).optString("description");
                int releaseYear = films.getJSONObject(i).optInt("release_year");
                int langId = films.getJSONObject(i).optInt("language_id");
                int originalLangId = films.getJSONObject(i).optInt("original_language_id");
                int rentalDuration = films.getJSONObject(i).optInt("rental_duration");
                int length = films.getJSONObject(i).optInt("length");
                Double replacementCost = films.getJSONObject(i).optDouble("replacement");
                String rating = films.getJSONObject(i).optString("rating");
                String features = films.getJSONObject(i).optString("special_features");
                String lastUpdate = films.getJSONObject(i).optString("last_update");


                Film film = new Film(filmId, title, description, releaseYear,
                        langId, originalLangId, rentalDuration, length, replacementCost,
                        rating, features, lastUpdate );


                // call back with new person data
                listener.onFilmAvailable(film);

            }
        } catch( JSONException ex) {
            Log.e(TAG, "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
    }



    // Call back interface
    public interface FilmListener {
        void onFilmAvailable(Film film);
    }
}
