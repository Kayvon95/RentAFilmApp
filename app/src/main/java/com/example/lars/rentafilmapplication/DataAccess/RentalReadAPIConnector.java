package com.example.lars.rentafilmapplication.DataAccess;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.lars.rentafilmapplication.Domain.Film;
import com.example.lars.rentafilmapplication.Domain.Rental;

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

public class RentalReadAPIConnector extends AsyncTask<String, Void, String> {
    private final String TAG = getClass().getSimpleName();
    private RentalReadAPIConnector.OnRentalAvailable listener = null;
    private Context context;
    private ProgressDialog progressBar;

    public RentalReadAPIConnector(OnRentalAvailable listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    protected void onPreExecute(){

        progressBar = new ProgressDialog(context);
        progressBar.setCancelable(false);
        progressBar.setMessage("Fetching rentals");
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

    //Handle doInBackground method results, retrieve from json, fit form of rental class
    protected void onPostExecute(String response) {

        if (response == null || response == ""){
            Log.e(TAG, "onPostExecute got empty response");
        } else {

            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(response);

                JSONArray items = jsonObject.getJSONArray("items");
                for (int idx = 0; idx < items.length(); idx++) {
                    JSONObject rental = items.getJSONObject(idx);

                    int rentalId = rental.getInt("rental_id");
                    String rentalDate = rental.getString("rental_date");
                    int inventoryId = rental.getInt("inventory_id");
                    int customerId = rental.getInt("customer_id");
                    String returnDate = rental.getString("return_date");
                    int staffId = rental.getInt("staff_id");
                    String lastUpdate = rental.getString("last_update");

                    Rental r = new Rental(rentalId, rentalDate, inventoryId, customerId, returnDate, staffId, lastUpdate);

                    listener.onRentalAvailable(r);
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
    public interface OnRentalAvailable {
        void onRentalAvailable(Rental rental);
    }
}
