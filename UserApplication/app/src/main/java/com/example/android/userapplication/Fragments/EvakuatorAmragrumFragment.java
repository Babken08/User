package com.example.android.userapplication.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.userapplication.R;

public class EvakuatorAmragrumFragment extends Fragment {
    public EvakuatorAmragrumFragment() {
        // Required empty public constructor
    }
    public static EvakuatorAmragrumFragment newInstance() {
        return new EvakuatorAmragrumFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_evakuator_amragrum, container, false);
    }
}
