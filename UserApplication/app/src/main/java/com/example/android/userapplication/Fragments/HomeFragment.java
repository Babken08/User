package com.example.android.userapplication.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.userapplication.Adapters.HomeServiceRecyclerAdapter;
import com.example.android.userapplication.Model.LogoModel;
import com.example.android.userapplication.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<LogoModel> imageList;
    private LogoModel model;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        callService(rootView);
        imageList();
        homeServiceRecycler(rootView);
        return rootView;
    }

    private void callService(View rootView) {
        ImageView imageCall = (ImageView) rootView.findViewById(R.id.phone_call);
        imageCall.setImageResource(R.drawable.phone_call_128x128);
        imageCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:123456789"));
                startActivity(i);
            }
        });
    }

    private void homeServiceRecycler(View rootView) {
        RecyclerView homeServiceRecyclerView = (RecyclerView) rootView.findViewById(R.id.home_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        homeServiceRecyclerView.setLayoutManager(linearLayoutManager);
        homeServiceRecyclerView.setAdapter(new HomeServiceRecyclerAdapter(imageList, getContext()));
    }

    private void imageList() {
        imageList = new ArrayList<>();
        imageList.add(model);
        imageList.add(model);
    }
}
