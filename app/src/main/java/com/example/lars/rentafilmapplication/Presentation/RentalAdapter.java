package com.example.lars.rentafilmapplication.Presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lars.rentafilmapplication.Domain.Rental;
import com.example.lars.rentafilmapplication.R;

import java.util.ArrayList;

/**
 * Created by Kayvon Rahimi on 19-6-2017.
 */


public class RentalAdapter extends ArrayAdapter<Rental> {
    private final String TAG = this.getClass().getSimpleName();
    private ArrayList filmList;

    public RentalAdapter(Context context, ArrayList<Rental> rentalList) {
        super(context, R.layout.custom_listitem_rental, rentalList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater productInflater = LayoutInflater.from(getContext());
        View customView = productInflater.inflate(R.layout.custom_listitem_rental, parent, false);

        Rental singleRental = getItem(position);

        TextView rentalIdTextView = (TextView) customView.findViewById(R.id.rentalIdTextView);
        TextView customerIdTextView = (TextView) customView.findViewById(R.id.customerIdTextView);
        TextView inventoryIdTextView = (TextView) customView.findViewById(R.id.inventoryIdTextView);

        rentalIdTextView.setText(singleRental.getRentalId());
        customerIdTextView.setText(singleRental.getCustomerId());
        inventoryIdTextView.setText(singleRental.getInventoryId());

        return customView;
    }
}
