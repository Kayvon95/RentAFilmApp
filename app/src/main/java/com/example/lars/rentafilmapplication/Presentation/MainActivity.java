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
    private Button login;
    private String myEmail, myPassword;

    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = (EditText) findViewById(R.id.email_login);
        editTextPassword = (EditText) findViewById(R.id.password_login);

        login = (Button) findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                myEmail = editTextEmail.getText().toString();
                myPassword = editTextPassword.getText().toString();
                handleLogin(myEmail, myPassword);
            }
        });
    }

    private void handleLogin(String email, String password){
        // Maakt JSON object met username en password voor de request body
        String body = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
        Log.i(TAG, "handleLogin body: " +body);

        try {
            JSONObject jsonBody = new JSONObject(body);
            JsonObjectRequest jsObRequest = new JsonObjectRequest
                    (Request.Method.POST, Config.URL_LOGIN, jsonBody, new Response.Listener<JSONObject>() {

                        public void onResponse(JSONObject response){
                            // succesvol response geeft token.
                            Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();

                            try {
                                String token = response.getString("token");

                                Context context = getApplicationContext();
                                SharedPreferences sharedPref = context.getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("TOKEN", token);
                                editor.commit();

                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                                // sluit login activity
                                finish();

                            } catch (JSONException e){
                                Log.e(TAG, e.getMessage());
                            }
                        }

                        // Error response van Volley
                    }, new Response.ErrorListener(){
                        public void onErrorResponse(VolleyError error){
                            handleErrorResponse(error);
                        }
                    });

            jsObRequest.setRetryPolicy(new DefaultRetryPolicy(
                    1500, // socket timeout
                    2, // defaultretrypolicy
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // add een request (jsObRequest) aan de requestqueue door gebruik te maken van de singleton klasse 'VolleyRequestQueue'.
            VolleyRequestQueue.getInstance(this).addToRequestQueue(jsObRequest);
        } catch (JSONException e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
