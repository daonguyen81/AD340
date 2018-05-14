package com.example.daong.activitykotlin;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Camera {
    public String Id;
    public String Description;
    public String imageUrl;
    public String Type;


    public Camera() {
    }


    public Camera(String id, String description, String imageUrl, String type) {
        Id = id;
        Description = description;
        this.imageUrl = imageUrl;
        Type = type;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        if(getType().equals("sdot")) {
            this.imageUrl = "http://www.seattle.gov/trafficcams/images/" + imageUrl;
        } else {
            this.imageUrl = "http://images.wsdot.wa.gov/nw/" + imageUrl;
        }
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

}
