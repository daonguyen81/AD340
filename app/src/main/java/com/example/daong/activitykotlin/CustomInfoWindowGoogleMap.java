package com.example.daong.activitykotlin;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import javax.security.auth.callback.Callback;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;
    //RequestOptions option;
    //boolean not_first_time_showing_info_window;

    public CustomInfoWindowGoogleMap(Context ctx){
        context = ctx;
        //option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = ((MapsActivity)context).getLayoutInflater()
                .inflate(R.layout.custom_info_window, null);

        TextView name =  view.findViewById(R.id.camera_name);

        ImageView img =  view.findViewById(R.id.camera_image);


        Camera infoWindowData = (Camera) marker.getTag();


        Picasso.with(context).load(infoWindowData.getImageUrl()).into(img);

        //Glide.with(context).load(infoWindowData.getImageUrl()).apply(option).into(img);

        name.setText(infoWindowData.getDescription().toString());


        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

}

