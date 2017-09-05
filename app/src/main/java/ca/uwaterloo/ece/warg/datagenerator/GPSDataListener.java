package ca.uwaterloo.ece.warg.datagenerator;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by cphajduk on 28/08/17.
 */


public class GPSDataListener implements LocationListener {
    private double lon, lat;
    private double alt;
    private float heading, speed;
    private long time;

    private static final String TAG = "GPSDataListener";

    protected LocationManager locationManager;
    Location location;
    Context mContext;

    GPSDataListener(Context context) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        mContext = context;
    }

    @Override
    public void onLocationChanged(Location loc) {;
        String longitude = "Longitude: " + loc.getLongitude();
        Log.v(TAG, longitude);
        String latitude = "Latitude: " + loc.getLatitude();
        Log.v(TAG, latitude);
        lat = loc.getLatitude();
        lon = loc.getLongitude();
        alt = loc.getAltitude();
        heading = loc.getBearing();
        speed = loc.getSpeed();
        time = loc.getTime();
    }

    void getGPS(){
        if ( ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (location != null) {
            String longitude = "Longitude: " + location.getLongitude();
            Log.v(TAG, longitude);
            String latitude = "Latitude: " + location.getLatitude();
            Log.v(TAG, latitude);
            lat = location.getLatitude();
            lon = location.getLongitude();
            alt = location.getAltitude();
            heading = location.getBearing();
            speed = location.getSpeed();
            time = location.getTime();
        }
    }

    public double getLongitude(){
        return lon;
    }
    public double getLatitude(){
        return lat;
    }
    public double getAltitude(){
        return alt;
    }
    public float getHeading(){
        return heading;
    }
    public float getSpeed(){
        return speed;
    }
    public long getTime(){
        return time;
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}


}
