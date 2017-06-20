package com.example.lars.rentafilmapplication.Presentation;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lars.rentafilmapplication.Domain.Rental;
import com.example.lars.rentafilmapplication.R;

import java.util.ArrayList;

/**
 * Created by Kayvon Rahimi on 19-6-2017.
 */


public class RentalAdapter extends BaseAdapter {
    private final String TAG = this.getClass().getSimpleName();
    private Context context;
    private LayoutInflater inflater;
    private ArrayList rentalList;

    public RentalAdapter(Context context, LayoutInflater inflater, ArrayList<Rental> rentalList) {
        this.context = context;
        this.inflater = inflater;
        this.rentalList = rentalList;
    }

    public int getCount() {
        int size = rentalList.size();
        return size;
    }

    public Object getItem(int position) {
        Log.i(TAG, "getItem: " + position);
        return rentalList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(TAG, "getView: " + position);

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_listitem_rental, null);

            viewHolder = new ViewHolder();

            viewHolder.rentalId = (TextView) convertView.findViewById(R.id.rentalIdTextView);
        TextView customerIdTextView = (TextView) convertView.findViewById(R.id.customerIdTextView);
        TextView inventoryIdTextView = (TextView) convertView.findViewById(R.id.inventoryIdTextView);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Rental singleRental = (Rental) rentalList.get(position);
        
        viewHolder.rentalId.setText(singleRental.getRentalId());
        viewHolder.customerId.setText(singleRental.getCustomerId());
        viewHolder.inventoryId.setText(singleRental.getInventoryId());

        return convertView;
    }

    private static class ViewHolder {
        public TextView rentalId;
        public TextView customerId;
        public TextView inventoryId;

    }

}
