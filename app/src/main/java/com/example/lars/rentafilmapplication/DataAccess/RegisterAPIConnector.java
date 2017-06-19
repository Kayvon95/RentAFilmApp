package com.example.lars.rentafilmapplication.DataAccess;

import android.os.AsyncTask;
import android.util.Log;

import com.example.lars.rentafilmapplication.Domain.Customer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;
import static com.example.lars.rentafilmapplication.Service.Config.URL_REGISTER;

/**
 * Created by Lars on 19-6-2017.
 */

public class RegisterAPIConnector extends AsyncTask<Void, Void, Void> {

    private Customer customer;

    public RegisterAPIConnector(Customer customer) {
        this.customer = customer;
    }

    @Override
    protected Void doInBackground(Void... params) {

        StringBuilder sb = new StringBuilder();
        String http = URL_REGISTER;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(http);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();
            //Create JSONObject here
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("email", customer.getEmail());
            jsonParam.put("password", customer.getPassword());
            jsonParam.put("active", customer.isActiveState());
            jsonParam.put("create_date", customer.getCreationDate());
            jsonParam.put("last_update", customer.getLastDate());

            Log.i(TAG, jsonParam.toString());

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(jsonParam.toString());
            out.close();

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                System.out.println("" + sb.toString());

            } else {
                System.out.println(urlConnection.getResponseMessage());
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return null;
    }
}

