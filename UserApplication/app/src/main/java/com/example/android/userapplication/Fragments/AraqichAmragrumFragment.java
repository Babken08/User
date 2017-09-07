package com.example.android.userapplication.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.userapplication.R;

import static com.example.android.userapplication.Constats.Constant.SHIPPING;


public class AraqichAmragrumFragment extends Fragment {

    private String shipping;

    public AraqichAmragrumFragment() {
        // Required empty public constructor
    }

    public static AraqichAmragrumFragment newInstance(String shipping) {
        AraqichAmragrumFragment fragment = new AraqichAmragrumFragment();
        Bundle args = new Bundle();
        args.putString(SHIPPING, shipping);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            shipping = getArguments().getString(SHIPPING);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_araqich_amragrum, container, false);
    }

}
