package com.example.android.userapplication.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.userapplication.R;

public class ManipulyatorAmragrumFragment extends Fragment {
    public ManipulyatorAmragrumFragment() {
        // Required empty public constructor
    }
    public static ManipulyatorAmragrumFragment newInstance() {
        return new ManipulyatorAmragrumFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manipulyator_amragrum, container, false);
    }

}
