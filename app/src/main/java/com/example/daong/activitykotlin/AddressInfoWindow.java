package com.example.daong.activitykotlin;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddressInfoWindow implements GoogleMap.InfoWindowAdapter {

    List<Address> addresses;
    private Context context;
    Location location;
    String result = "";
    Geocoder geocoder;

    public AddressInfoWindow(Context context, Location location) {
        this.context = context;
        this.location = location;
    }

    @Override
    public View getInfoWindow(Marker marker) {

        View view = ((MapsActivity)context).getLayoutInflater()
                .inflate(R.layout.address_infor_window, null);

        TextView addressView =  view.findViewById(R.id.address);

        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null) {
                Address address = addresses.get(0);
                StringBuffer addressDetails = new StringBuffer();

                addressDetails.append("Current Location Address");
                addressDetails.append("\n");

                addressDetails.append(address.getThoroughfare());
                addressDetails.append("\n");

                addressDetails.append("City: ");
                addressDetails.append(address.getLocality());
                addressDetails.append("\n");

                addressDetails.append("County: ");
                addressDetails.append(address.getSubAdminArea());
                addressDetails.append("\n");

                addressDetails.append("State: ");
                addressDetails.append(address.getAdminArea());
                addressDetails.append("\n");

                addressDetails.append("Postal Code: ");
                addressDetails.append(address.getPostalCode());
                addressDetails.append("\n");

                addressDetails.append("Country: ");
                addressDetails.append(address.getCountryName());
                addressDetails.append("\n");

                result = result + addressDetails;

            } else {
                result = "No Address returned!";
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        addressView.setText(result.toString());

        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
