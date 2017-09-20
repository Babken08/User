package com.example.android.userapplication.Fragments;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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

import com.example.android.userapplication.R;
import com.example.android.userapplication.Service.GPSTracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.example.android.userapplication.Constats.Constant.EVAKUATOR;
import static com.example.android.userapplication.Constats.Constant.MANIPULYATOR;
import static com.example.android.userapplication.Constats.Constant.SHIPPING_AUTO;
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
    private String patvirel;

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
        serviceIntentFilter();
        mFusedLocationClient = new FusedLocationProviderClient(getActivity());

    }

    private void serviceIntentFilter() {
        serviceIntent = new Intent(getActivity(), GPSTracker.class);
        getActivity().startService(serviceIntent);

        mbroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                double lat = intent.getDoubleExtra("lat", 0);
                double lng = intent.getDoubleExtra("lng", 0);


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
        ImageView imgPatvirel = (ImageView) rootView.findViewById(R.id.img_patvirel);

        if (getArguments() != null) {
            patvirel = getArguments().getString("patvirel");
            if (patvirel.equals(TAXI_SIZE_4)) {
                imgPatvirel.setImageResource(R.mipmap.ic_taxi_256);
            }
            if (patvirel.equals(SHIPPING_TRUCK)){
                imgPatvirel.setImageResource(R.mipmap.ic_shipping_256);
            }
            if (patvirel.equals(EVAKUATOR)){
                imgPatvirel.setImageResource(R.mipmap.ic_evacuator_256);
            }
            if (patvirel.equals(TAXI_SIZE_7)){
                imgPatvirel.setImageResource(R.mipmap.ic_taxi_1);
            }
            if (patvirel.equals(SHIPPING_AUTO)){
                imgPatvirel.setImageResource(R.mipmap.ic_shipping_auto);
            }
            if (patvirel.equals(MANIPULYATOR)){
                imgPatvirel.setImageResource(R.mipmap.ic_manipulyator);
            }
        }

        btnSearch.setImageResource(R.mipmap.ic_logo_search);
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
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        buildVersionSDK();
    }

    private void buildVersionSDK() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            ltlng = new LatLng(location.getLatitude(), location.getLongitude());
                            CameraUpdate center = CameraUpdateFactory.newLatLng(ltlng);
                            mMap.moveCamera(center);
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 3000, null);
                            Log.i("ssssssssssssssssssss", location.getLatitude() + "    " + location.getLongitude());
                            Toast.makeText(getActivity(), location.getLatitude() + "    " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

                Toast.makeText(getActivity(), locatio, Toast.LENGTH_SHORT).show();
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng));
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
        getActivity().getApplicationContext().stopService(serviceIntent);
        if (mbroadcastReceiver != null) {
            getActivity().unregisterReceiver(mbroadcastReceiver);
        }
    }
}
