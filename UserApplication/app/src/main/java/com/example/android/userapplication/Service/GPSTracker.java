package com.example.android.userapplication.Service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class GPSTracker extends Service implements LocationListener {

    private Location mlocation;
    protected LocationManager mlocationManager;

    @Override
    public void onCreate() {
        getLocation();
    }

    public Location getLocation() {
        try {
            mlocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            boolean isGPSEnabled = mlocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled) {
                    if (mlocation == null) {
                        mlocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                        if (mlocationManager != null) {
                            mlocation = mlocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
                    }
                }
            }

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "GPS Enabled", Toast.LENGTH_SHORT).show();
        }
        return mlocation;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        Intent intent = new Intent("SERVICE_GPS");
        intent.putExtra("lat", location.getLatitude());
        intent.putExtra("lng", location.getLongitude());
        sendBroadcast(intent);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}