package com.example.lars.rentafilmapplication.Presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.lars.rentafilmapplication.DataAccess.RentalReadAPIConnector;
import com.example.lars.rentafilmapplication.Domain.Rental;
import com.example.lars.rentafilmapplication.R;

import java.util.ArrayList;

import static com.example.lars.rentafilmapplication.Service.Config.URL_GETRENTALS;

public class RentalActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, RentalReadAPIConnector.OnRentalAvailable{
    private final String TAG = this.getClass().getSimpleName();
    private ArrayList<Rental> rentals = new ArrayList<>();
    private ListView rentalListView;
    private RentalAdapter rentalAdapter;
    private EditText searchFld;
    private RentalReadAPIConnector getRentals;
    private Button searchBtn;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental);
        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        toolbar.setTitle(R.string.home_toolbar);
        setSupportActionBar(toolbar);

        getRentals = new RentalReadAPIConnector(this, this);
        getRentals.execute(URL_GETRENTALS);

        rentalListView = (ListView) findViewById(R.id.rentalListView);
        rentalListView.setOnItemClickListener(this);

        rentalAdapter = new RentalAdapter(getApplicationContext(), getLayoutInflater(), rentals);
        rentalListView.setAdapter(rentalAdapter);

        //Search textfield + button
        searchFld = (EditText) findViewById(R.id.searchInput);
        searchBtn = (Button) findViewById(R.id.searchButton);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rent_a_film_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.log_out:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            case R.id.home:

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Select film
        Rental selectedRental = (Rental) rentals.get(position);

        //Verstuur de informatie van de geklikte rental listitem mee om de rentaldetailactivity mee in te vullen
        Intent i = new Intent(getApplicationContext(), RentalDetailActivity.class);
        i.putExtra("Rental", selectedRental);
        startActivity(i);
    }

    public void getRental() {
        RentalReadAPIConnector task = new RentalReadAPIConnector(this, this);
        String[] urls = new String[]{ URL_GETRENTALS};
        task.execute(urls);
    }

    @Override
    public void onRentalAvailable(Rental rental) {
        rentals.add(rental);
        Log.i(TAG, "Rental fetched (" + rental.toString() + ")");
        rentalAdapter.notifyDataSetChanged();
    }

}
