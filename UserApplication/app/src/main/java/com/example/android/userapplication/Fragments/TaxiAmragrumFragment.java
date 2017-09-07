package com.example.android.userapplication.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.userapplication.R;

import static com.example.android.userapplication.Constats.Constant.TAXI;

public class TaxiAmragrumFragment extends Fragment {

    private String taxi;

    public TaxiAmragrumFragment() {
        // Required empty public constructor
    }

    public static TaxiAmragrumFragment newInstance(String taxi) {
        TaxiAmragrumFragment fragment = new TaxiAmragrumFragment();
        Bundle args = new Bundle();
        args.putString(TAXI, taxi);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            taxi = getArguments().getString(TAXI);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_taxi_amragrum, container, false);
    }


}
