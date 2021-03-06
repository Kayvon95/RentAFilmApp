package com.example.lars.rentafilmapplication.DataAccess;

import android.os.AsyncTask;
import android.util.Log;

import com.example.lars.rentafilmapplication.Domain.Rental;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.lars.rentafilmapplication.Service.Config.URL_DELETERENTAL;

/**
 * Created by Kayvon Rahimi on 19-6-2017.
 */

public class RentalDeleteAPIConnector extends AsyncTask<Void, Void, Void> {
    private final String TAG = this.getClass().getSimpleName();
    private Rental rental;

    public RentalDeleteAPIConnector(Rental rental) {
        this.rental = rental;
    }

    @Override
    protected Void doInBackground(Void... params) {
        StringBuilder sb = new StringBuilder();
        String http = URL_DELETERENTAL;
        HttpURLConnection urlConnection=null;
        try {
            URL url = new URL(http);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();
            //Create JSONObject here
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("rental_id", rental.getRentalId());
            jsonParam.put("rental_date", rental.getRentalDate());
            jsonParam.put("inventory_id", rental.getInventoryId());
            jsonParam.put("customer_id", rental.getCustomerId());
            jsonParam.put("return_date", rental.getReturnDate());
            jsonParam.put("staff_id", rental.getStaffId());
            jsonParam.put("last_update", rental.getLastUpdate());

            Log.i(TAG,jsonParam.toString());
            OutputStreamWriter out = new   OutputStreamWriter(urlConnection.getOutputStream());
            out.write(jsonParam.toString());
            out.close();

            int HttpResult =urlConnection.getResponseCode();
            if(HttpResult ==HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream(),"utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                System.out.println(""+sb.toString());

            }else{
                System.out.println(urlConnection.getResponseMessage());
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }finally{
            if(urlConnection!=null)
                urlConnection.disconnect();
        }
        return null;
    }
}
