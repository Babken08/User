package com.example.android.userapplication.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.userapplication.R;


public class TaxiAmragrumFragment extends Fragment {

    public TaxiAmragrumFragment() {
        // Required empty public constructor
    }

    public static TaxiAmragrumFragment newInstance() {
        return new TaxiAmragrumFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_taxi_amragrum, container, false);
    }


}
