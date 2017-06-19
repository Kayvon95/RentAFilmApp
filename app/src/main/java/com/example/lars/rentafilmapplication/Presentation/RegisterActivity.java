package com.example.lars.rentafilmapplication.Presentation;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lars.rentafilmapplication.DataAccess.RegisterAPIConnector;
import com.example.lars.rentafilmapplication.Domain.Customer;
import com.example.lars.rentafilmapplication.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Customer customer;
    private EditText email, password;
    private Button button;
    private String formattedDate;
    private RegisterAPIConnector connector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText) findViewById(R.id.register_email);
        password = (EditText) findViewById(R.id.register_password);

        button = (Button) findViewById(R.id.register_button);
        button.setOnClickListener(this);

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());
    }

    public void onClick(View v){

        if (!email.getText().toString().equals("") && !password.getText().toString().equals("")) {
            customer = new Customer(true, formattedDate, formattedDate, password.getText().toString(), email.getText().toString());
            new RegisterAPIConnector(customer).execute();

            Toast.makeText(getApplicationContext(), "Registration succesfull", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Please enter a valid email and password", Toast.LENGTH_LONG).show();
        }

    }
}
