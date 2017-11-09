package com.example.android.userapplication.Fragments;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.userapplication.Activityes.HomeActivity;
import com.example.android.userapplication.R;
import com.example.android.userapplication.Service.GPSTracker;
import com.example.android.userapplication.utilityes.GPSUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.example.android.userapplication.Constats.Constant.EVAKUATOR;
import static com.example.android.userapplication.Constats.Constant.SHIPPING_TRUCK;
import static com.example.android.userapplication.Constats.Constant.TAXI_SIZE_4;
import static com.example.android.userapplication.Constats.Constant.TAXI_SIZE_7;

public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 100;
    private GoogleMap mMap;
    private Intent serviceIntent;
    private GoogleApiClient mGoogleApiClient;
    private BroadcastReceiver mbroadcastReceiver;
    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng ltlng;
    private EditText edSearchLocation;
    private LocationManager locationManager;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private GPSUtil util;

    private Marker marker;

    public MapFragment() {
    }

    public static MapFragment newInstance(String s) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString("patvirel", s);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




//        if (mGoogleApiClient == null) {
//            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
//                    .addApi(LocationServices.API)
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this).build();
//            mGoogleApiClient.connect();
//
//            LocationRequest locationRequest = LocationRequest.create();
//            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//            locationRequest.setInterval(30 * 1000);
//            locationRequest.setFastestInterval(5 * 1000);
//            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                    .addLocationRequest(locationRequest);
//
//            //**************************
//            builder.setAlwaysShow(true); //this is the key ingredient
//            //**************************
//
//            PendingResult<LocationSettingsResult> result =
//                    LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
//            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
//                @Override
//                public void onResult(LocationSettingsResult result) {
//                    final Status status = result.getStatus();
//                    final LocationSettingsStates state = result.getLocationSettingsStates();
//                    switch (status.getStatusCode()) {
//                        case LocationSettingsStatusCodes.SUCCESS: {
//                            // All location settings are satisfied. The client can initialize location
//                            // requests here.
//                            Log.i("sssssssssssssss", "success");
//                            break;
//                        }
//                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED: {
//                            // Location settings are not satisfied. But could be fixed by showing the user
//                            // a dialog.
//                            Log.i("sssssssssssssss", "RESOLUTION_REQUIRED");
//                            try {
//                                // Show the dialog by calling startResolutionForResult(),
//                                // and check the result in onActivityResult().
//                                status.startResolutionForResult(
//                                        getActivity(), 1000);
//                            } catch (IntentSender.SendIntentException e) {
//                                // Ignore the error.
//                            }
//                            break;
//                        }
//                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE: {
//                            // Location settings are not satisfied. However, we have no way to fix the
//                            // settings so we won't show the dialog.
//                            Log.i("sssssssssssssss", "SETTINGS_CHANGE_UNAVAILABLE");
//                            break;
//                        }
//                        case LocationSettingsStatusCodes.CANCELED: {
//                            Log.i("sssssssssssssss", "CANCELED");
//                            break;
//                        }
//                    }
//                }
//            });
//        }

        ((HomeActivity) getActivity()).getSupportActionBar().hide();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i("ssssssssssssssss", "Refreshed token: " + refreshedToken);

        serviceIntentFilter();
        mFusedLocationClient = new FusedLocationProviderClient(getActivity());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);

                Log.i("sssssssssss", "Place: " + place.getName());
                edSearchLocation.setText(place.getName());
                String locatio = edSearchLocation.getText().toString();
                if (!locatio.isEmpty()) {

                    List<Address> addressList = null;
                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

                    try {
                        addressList = geocoder.getFromLocationName(locatio, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (addressList != null && addressList.size() > 0) {

                        String addressLine = addressList.get(0).getAddressLine(0);

                        Log.i("ssssssssssssssss", addressLine);
                        if (marker != null) {
                            marker.remove();
                        }
                        Toast.makeText(getActivity(), locatio, Toast.LENGTH_SHORT).show();
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                        marker = mMap.addMarker(new MarkerOptions().position(latLng));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(), address.getLongitude()), 18.0f));

                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                Toast.makeText(getActivity(), "Marker", Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        });

                    } else {
                        Toast.makeText(getActivity(), "Entry currently name ", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.i("ssssssssss", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    private void serviceIntentFilter() {
        serviceIntent = new Intent(getActivity(), GPSTracker.class);
        getActivity().startService(serviceIntent);

        mbroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                double lat = intent.getDoubleExtra("lat", 0);
                double lng = intent.getDoubleExtra("lng", 0);

                Log.i("sssssssssssssss", lat + " asdasdadasd " + lng);
                buildVersionSDK();

                cameraCenter(lat, lng);
            }
        };
        IntentFilter intFilt = new IntentFilter("SERVICE_GPS");
        getActivity().registerReceiver(mbroadcastReceiver, intFilt);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        rootViewFindById(rootView);
        return rootView;
    }

    private void rootViewFindById(View rootView) {
        edSearchLocation = (EditText) rootView.findViewById(R.id.et_search_location);
        ImageView btnSearch = (ImageView) rootView.findViewById(R.id.btn_search);
        ImageView markerMap = (ImageView) rootView.findViewById(R.id.marker_map);

        markerMap.setImageDrawable(getResources().getDrawable(R.drawable.map_evakuator));

        edSearchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                        .setTypeFilter(AutocompleteFilter.TYPE_FILTER_GEOCODE)
                        .build();
                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .setFilter(typeFilter)
                            .build(getActivity());
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });

        if (getArguments() != null) {
            String patvirel = getArguments().getString("patvirel");
            if (patvirel.equals(TAXI_SIZE_4)) {
                markerMap.setImageResource(R.drawable.map_taxi4);
            }
            if (patvirel.equals(SHIPPING_TRUCK)) {
                markerMap.setImageResource(R.drawable.map_shipping);
            }
            if (patvirel.equals(EVAKUATOR)) {
                markerMap.setImageResource(R.drawable.map_evakuator);
            }
            if (patvirel.equals(TAXI_SIZE_7)) {
                markerMap.setImageResource(R.drawable.map_taxi7);
            }
        }

        btnSearch.setImageResource(R.drawable.ic_logo_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearch();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
    }

    private void buildGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
            util = new GPSUtil(getActivity(), mGoogleApiClient);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        buildVersionSDK();
        mMap.getFocusedBuilding();
        mMap.getUiSettings().setRotateGesturesEnabled(false);

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                return true;
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                CameraPosition cameraPosition = mMap.getCameraPosition();
                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                Log.i("ssssssssssssss cameraPosition", String.valueOf(cameraPosition.target.latitude));
                Log.i("ssssssssssssss cameraPosition", String.valueOf(cameraPosition.target.longitude));
                updateAddress(cameraPosition, geocoder);
            }
        });
    }

    private void updateAddress(CameraPosition cameraPosition, Geocoder geocoder) {
        List<Address> addressList = null;

        try {
            addressList = geocoder.getFromLocation(cameraPosition.target.latitude, cameraPosition.target.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addressList != null && addressList.size() > 0) {

            String addressLine = addressList.get(0).getAddressLine(0);

            Log.i("ssssssssssssssss addressLine", addressLine);
            if (marker != null) {
                marker.remove();
            }
            String s = addressLine;
            String a = s;
            char[] r = s.toCharArray();
            for (int i = 0; i < r.length; i++) {
                if (r[i] == ',') {
                    a = s.substring(0, i);
                    Log.i("ssssssssssssssssss +        aaaaaaaaaaaaaaaaaaaaaaa", a);
                    edSearchLocation.setText(a);
                    break;
                }
            }


            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Toast.makeText(getActivity(), "Marker", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

        } else {
            Toast.makeText(getActivity(), "Entry currently name ", Toast.LENGTH_SHORT).show();
        }
    }

    private void buildVersionSDK() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }
        buildGoogleApiClient();
//        mFusedLocationClient.getLastLocation()
//                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        if (GPSStatus() && isOnline()) {
//                            if (location != null) {
//                                cameraCenter(location.getLatitude(), location.getLongitude());
//                                Log.i("ssssssssssssssssssss", "FusedLocation " + location.getLatitude() + "    " + location.getLongitude());
//                            }
//                        } else {
//                            util.settingsrequest();
////                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//                            WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//                            wifiManager.setWifiEnabled(true);
//                        }
//                    }
//                });
    }

    private void cameraCenter(double lat, double lng) {
        ltlng = new LatLng(lat, lng);
        CameraUpdate center = CameraUpdateFactory.newLatLng(ltlng);
        mMap.moveCamera(center);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 3000, null);
    }

    private boolean GPSStatus() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null;
    }

    public void onSearch() {
        if (mMap != null) {
            mMap.clear();
        }
        String locatio = edSearchLocation.getText().toString();
        if (!locatio.isEmpty()) {

            List<Address> addressList = null;
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

            try {
                addressList = geocoder.getFromLocationName(locatio, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addressList != null && addressList.size() > 0) {

                String addressLine = addressList.get(0).getAddressLine(0);

                Log.i("ssssssssssssssss", addressLine);
                if (marker != null) {
                    marker.remove();
                }
                Toast.makeText(getActivity(), locatio, Toast.LENGTH_SHORT).show();
                Address address = addressList.get(0);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(), address.getLongitude()), 18.0f));

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Toast.makeText(getActivity(), "Marker", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

            } else {
                Toast.makeText(getActivity(), "Entry currently name ", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((HomeActivity) getActivity()).getSupportActionBar().show();
        getActivity().getApplicationContext().stopService(serviceIntent);
        if (mbroadcastReceiver != null) {
            getActivity().unregisterReceiver(mbroadcastReceiver);
        }
    }

    private void replaceFragment(int id, Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(id, fragment)
                .commit();
    }


}
