package com.example.lars.rentafilmapplication.Presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.lars.rentafilmapplication.Service.Config;
import com.example.lars.rentafilmapplication.R;
import com.example.lars.rentafilmapplication.Service.VolleyRequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button login, register;
    private String myEmail, myPassword;

    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = (EditText) findViewById(R.id.email_login);
        editTextPassword = (EditText) findViewById(R.id.password_login);

        // Clear de sharedPrefs , hierdoor wordt de token verwijdert en moet je opnieuw inloggen.

        register = (Button) findViewById(R.id.link_register);
        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        login = (Button) findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                myEmail = editTextEmail.getText().toString();
                myPassword = editTextPassword.getText().toString();

                if (myEmail.equals("") || myPassword.equals("")){
                    Toast.makeText(getApplicationContext(), "Empty fields", Toast.LENGTH_SHORT).show();
                } else {
                    handleLogin(myEmail, myPassword);
                }
            }
        });
    }

    private void handleLogin(String email, String password){

        try {
            JSONObject jsonBody  = new JSONObject();
            jsonBody.put("email",email);
            jsonBody.put("password", password);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, Config.URL_LOGIN, jsonBody, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // Succesvol response - dat betekent dat we een geldig token hebben.
                            // txtLoginErrorMsg.setText("Response: " + response.toString());
                            Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();

                            // We hebben nu het token. We kiezen er hier voor om
                            // het token in SharedPreferences op te slaan. Op die manier
                            // is het token tussen app-stop en -herstart beschikbaar -
                            // totdat het token expired.
                            try {
                                String token = response.getString("token");

                                Context context = getApplicationContext();
                                SharedPreferences sharedPref = context.getSharedPreferences("token", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("token", token);
                                editor.commit();

                                // Start the main activity, and close the login activity
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                                // Close the current activity
                                finish();

                            } catch (JSONException e) {
                                // e.printStackTrace();
                                Log.e(TAG, e.getMessage());
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            handleErrorResponse(error);
                        }
                    });

            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                    1500, // SOCKET_TIMEOUT_MS,
                    2, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Access the RequestQueue through your singleton class.
            VolleyRequestQueue.getInstance(this).addToRequestQueue(jsObjRequest);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            // e.printStackTrace();
        }
        return;
    }

    // Handel Volley errors op de juiste manier af
    public void handleErrorResponse(VolleyError error) {
        Log.e(TAG, "handleErrorResponse");

        if(error instanceof com.android.volley.AuthFailureError) {
            String json = null;
            NetworkResponse response = error.networkResponse;
            if (response != null && response.data != null) {
                json = new String(response.data);
                json = trimMessage(json, "error");
                if (json != null) {
                    json = "Error " + response.statusCode + ": " + json;
                    Toast.makeText(getApplicationContext(), json, Toast.LENGTH_LONG).show();
                }
            } else {
                Log.e(TAG, "handleErrorResponse: kon geen networkResponse vinden.");
            }
        } else if(error instanceof com.android.volley.NoConnectionError) {
            Log.e(TAG, "handleErrorResponse: server was niet bereikbaar");
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_server_offline), Toast.LENGTH_LONG).show();
        } else {
            Log.e(TAG, "handleErrorResponse: error = " + error);
        }
    }

    public String trimMessage(String json, String key){
        Log.i(TAG, "trimMessage: json = " + json);
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }
        return trimmedString;
    }
}
