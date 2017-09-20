package com.example.android.userapplication.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.userapplication.R;

import static com.example.android.userapplication.Constats.Constant.AMRAGREL_FRAGMENT;

public class AmragrumFragment extends Fragment {

    private String amragrum;
    private TextView tvAmragrel;
    private EditText amAddress;
    private Button mOk;


    public AmragrumFragment() {
        // Required empty public constructor
    }

    public static AmragrumFragment newInstance(String amragrum) {
        AmragrumFragment fragment = new AmragrumFragment();
        Bundle args = new Bundle();
        args.putString(AMRAGREL_FRAGMENT, amragrum);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_amragrum, container, false);


        rootViewFindViewById(rootView);
        if (getArguments() != null) {
            amragrum = getArguments().getString(AMRAGREL_FRAGMENT);
            tvAmragrel.setText(amragrum);
        }
        return rootView;
    }

    private void rootViewFindViewById(View rootView) {
        tvAmragrel = (TextView) rootView.findViewById(R.id.tv_amragrel);
        amAddress = (EditText) rootView.findViewById(R.id.amragrum_address_amragrel);
        mOk = (Button) rootView.findViewById(R.id.amragrum_ok);

        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = amAddress.getText().toString();
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });

    }


}
