package com.example.daong.activitykotlin;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    List<Address> addresses;
    private Context context;
    Location location;
    String result = "";
    Geocoder geocoder;
    boolean not_first_time_showing_info_window;

    public CustomInfoWindowGoogleMap(Context ctx) {
        context = ctx;
        //option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    public CustomInfoWindowGoogleMap(Context ctx, Location location) {
        context = ctx;
        this.location = location;
    }

    @Override
    public View getInfoWindow(Marker marker) {

        View view = ((MapsActivity) context).getLayoutInflater()
                .inflate(R.layout.custom_info_window, null);
        TextView name = view.findViewById(R.id.camera_name);
        ImageView img = view.findViewById(R.id.camera_image);
        Camera infoWindowData = (Camera) marker.getTag();
        if (not_first_time_showing_info_window) {
            Picasso.with(context).load(infoWindowData.getImageUrl()).into(img);
        } else {
            not_first_time_showing_info_window = true;
            Picasso.with(context).load(infoWindowData.getImageUrl()).into(img, new InfoWindowRefresher(marker));
            //Glide.with(context).load(infoWindowData.getImageUrl()).apply(option).into(img);
        }

        name.setText(infoWindowData.getDescription().toString());
        return view;
    }



    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private class InfoWindowRefresher implements Callback {
        private Marker markerToRefresh;

        private InfoWindowRefresher(Marker markerToRefresh) {
            this.markerToRefresh = markerToRefresh;
        }

        @Override
        public void onSuccess() {
            markerToRefresh.showInfoWindow();
        }

        @Override
        public void onError() {
        }
    }
}

