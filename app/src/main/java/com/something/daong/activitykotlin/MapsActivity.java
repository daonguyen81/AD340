package com.example.daong.activitykotlin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    SupportMapFragment supportMapFragment;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    private Hashtable<String, String> markers = new Hashtable<>();
    GoogleMap mGoogleMap;
    MaterialSearchView materialSearchView;
    String[] list;
    Geocoder geocoder;
    List<Address> addresses;

    //public HashMap<Integer, Marker> mHashMap = new HashMap<Integer, Marker>();
    public static final int PERMISSIONS_REQUEST_LOCATION = 100;
    private String url = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Maps");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.daong.activitykotlin.MainActivity.class));
                finish();
            }
        });

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        list = new String[]{"AD340", "Android", "Mobile App", "Google", "Programming", "Android Developer"};
        materialSearchView = (MaterialSearchView) findViewById(R.id.mysearch);
        materialSearchView.setSuggestions(list);
        materialSearchView.setEllipsize(true);
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Here create your filtering
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //change if typing
                return false;
            }
        });

        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {}

            @Override
            public void onSearchViewClosed() {}
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            }
            else {
                //Request Location Permission to user
                checkPermission();
            }
        }
        else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }

        mGoogleMap.setInfoWindowAdapter(new CustomInfoWindowGoogleMap(MapsActivity.this));
        getCameraData();
    }

    public void getCameraData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("Features");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject camObj = array.getJSONObject(i);
                        Camera camera = new Camera();
                        JSONArray camLocation = camObj.getJSONArray("PointCoordinate");

                        camera.setLatitude(camLocation.getDouble(0));
                        camera.setLongtitude(camLocation.getDouble(1));

                        JSONArray camArray = camObj.getJSONArray("Cameras");
                        for (int j = 0; j < camArray.length(); j++) {
                            JSONObject cameras = camArray.getJSONObject(j);
                            camera.setId(cameras.getString("Id"));
                            camera.setDescription(cameras.getString("Description"));
                            camera.setType(cameras.getString("Type"));
                            camera.setImageUrl(cameras.getString("ImageUrl"));
                        }

                        MarkerOptions markerOptions = new MarkerOptions();
                        LatLng newCamLocation = new LatLng(camera.getLatitude(),
                                camera.getLongtitude());
                        if (camera.getType().equals(("wsdot"))) {
                            markerOptions.position(newCamLocation).title(camera.getId()).snippet(camera.getDescription()).icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        } else {
                            markerOptions.position(newCamLocation).title(camera.getId()).snippet(camera.getDescription());
                        }

                        final Marker marker = mGoogleMap.addMarker(markerOptions);
                        markers.put(marker.getId(), camera.getImageUrl());
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(newCamLocation));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(15000);
        mLocationRequest.setFastestInterval(15000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    @Override
    public void onLocationChanged(final Location location)
    {
        String currentAddress = "";
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        currentAddress = getAddress(location);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.snippet(currentAddress);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        String dao_image = "http://dnguyen.icoolshow.net/hawaii-family.jpg";
        markers.put(mCurrLocationMarker.getId(), dao_image);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));

        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(200);
        circleOptions.fillColor(Color.BLUE);
        circleOptions.strokeWidth(6);
        mGoogleMap.addCircle(circleOptions);
    }

    public String getAddress(Location location) {
        String result = "";
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if(addresses != null){
                Address address = addresses.get(0);
                StringBuffer addressDetails = new StringBuffer();

                addressDetails.append("Current Location Address");
                addressDetails.append("\n");
                if(address.getSubThoroughfare() == null) {
                    addressDetails.append("");
                } else {
                    addressDetails.append(address.getSubThoroughfare());
                    addressDetails.append(" ");
                }

                if(address.getThoroughfare() == null){
                    addressDetails.append("");
                } else {
                    addressDetails.append(address.getThoroughfare());
                    addressDetails.append("\n");
                }

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

            } else{
                result = "No Address returned!";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    Toast.makeText(this, "permission denied by user", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (materialSearchView.isSearchOpen()) {
            materialSearchView.closeSearch();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setting_option, menu);
        MenuItem item = menu.findItem(R.id.search);
        materialSearchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_setting) {
            Toast toast = Toast.makeText(MapsActivity.this, "This is setting option menu!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 10);
            toast.show();
            return true;
        }
        if(id == R.id.search) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

        private Context context;

        public CustomInfoWindowGoogleMap(Context ctx) {
            context = ctx;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View view = ((MapsActivity) context).getLayoutInflater()
                    .inflate(R.layout.custom_info_window, null);

            final String url = markers.get(marker.getId());

            final String snippet = marker.getSnippet();

            TextView name = view.findViewById(R.id.camera_name);
            ImageView img = view.findViewById(R.id.camera_image);
            Log.d("url",url);
            Picasso.with(context).load(url).error(R.mipmap.ic_launcher).resize(200,140)
                    .into(img, new InfoWindowRefresher(marker));

            name.setText(snippet);
            return view;
        }
    }

    static class InfoWindowRefresher implements Callback {
        Marker markerToRefresh;

        InfoWindowRefresher(Marker markerToRefresh) {
            this.markerToRefresh = markerToRefresh;
        }

        @Override
        public void onSuccess() {
            if (markerToRefresh == null) {
                return;
            }

            if (!markerToRefresh.isInfoWindowShown()) {
                return;
            }
            markerToRefresh.hideInfoWindow();
            markerToRefresh.showInfoWindow();
        }

        @Override
        public void onError() { }
    }
}
