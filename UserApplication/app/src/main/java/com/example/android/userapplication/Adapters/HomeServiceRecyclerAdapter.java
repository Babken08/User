package com.example.android.userapplication.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.userapplication.Activityes.HomeActivity;
import com.example.android.userapplication.Fragments.AmragrumFragment;
import com.example.android.userapplication.Fragments.MapFragment;
import com.example.android.userapplication.Model.LogoModel;
import com.example.android.userapplication.R;

import java.util.List;

import static com.example.android.userapplication.Constats.Constant.AMRAGREL;
import static com.example.android.userapplication.Constats.Constant.EVAKUATOR;
import static com.example.android.userapplication.Constats.Constant.PATVIREL;
import static com.example.android.userapplication.Constats.Constant.SHIPPING;
import static com.example.android.userapplication.Constats.Constant.SHIPPING_TRUCK;
import static com.example.android.userapplication.Constats.Constant.TAXI;
import static com.example.android.userapplication.Constats.Constant.TAXI_SIZE_4;
import static com.example.android.userapplication.Constats.Constant.TAXI_SIZE_7;

public class HomeServiceRecyclerAdapter extends RecyclerView.Adapter<HomeServiceRecyclerAdapter.MyViewHolder> {

    private List<LogoModel> list;
    private Context context;

    public HomeServiceRecyclerAdapter(List<LogoModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public HomeServiceRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_service_recycler_row, parent, false);
        return new HomeServiceRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HomeServiceRecyclerAdapter.MyViewHolder holder, final int position) {
        mipmapIcon(holder, position);
        holderOnClickListener(holder, position);
    }

    private void holderOnClickListener(MyViewHolder holder, final int position) {
        holder.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0)
                    alertDialog(TAXI_SIZE_4);
                if (position == 1)
                    alertDialog(SHIPPING_TRUCK);
            }
        });

        holder.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0)
                    alertDialog(TAXI_SIZE_7);
                if (position == 1)
                    alertDialog(EVAKUATOR);
            }
        });
    }

    private void mipmapIcon(MyViewHolder holder, int position) {
        if (position == 0) {
            holder.button1.setImageResource(R.drawable.ic_taxi_256);
            holder.button3.setImageResource(R.drawable.ic_taxi_1);
            holder.name1.setText(TAXI_SIZE_4);
            holder.name3.setText(TAXI_SIZE_7);
        }
        if (position == 1) {
            holder.button1.setImageResource(R.drawable.ic_shipping_256);
            holder.name1.setText(SHIPPING);
            holder.button3.setImageResource(R.drawable.ic_evacuator_256);
            holder.name3.setText(EVAKUATOR);
        }
    }

    private void alertDialog(final String c) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setCancelable(false).setPositiveButton(PATVIREL, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (c.equals(TAXI_SIZE_4))
                    replaceFragment(R.id.container_home_activity, MapFragment.newInstance(TAXI_SIZE_4));
                if (c.equals(SHIPPING_TRUCK))
                    replaceFragment(R.id.container_home_activity, MapFragment.newInstance(SHIPPING_TRUCK));
                if (c.equals(EVAKUATOR))
                    replaceFragment(R.id.container_home_activity, MapFragment.newInstance(EVAKUATOR));
                if (c.equals(TAXI_SIZE_7))
                    replaceFragment(R.id.container_home_activity, MapFragment.newInstance(TAXI_SIZE_7));
            }
        }).setNegativeButton(AMRAGREL, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (c.equals(TAXI_SIZE_4)) {

                    replaceFragment(R.id.container_home_activity, R.id.container_home, AmragrumFragment.newInstance(TAXI + " " + TAXI_SIZE_4), MapFragment.newInstance(TAXI_SIZE_4));
                }
                if (c.equals(SHIPPING_TRUCK)) {
                    replaceFragment(R.id.container_home_activity, R.id.container_home, AmragrumFragment.newInstance(SHIPPING_TRUCK), MapFragment.newInstance(SHIPPING_TRUCK));
                }
                if (c.equals(EVAKUATOR)) {
                    replaceFragment(R.id.container_home_activity, R.id.container_home, AmragrumFragment.newInstance(EVAKUATOR), MapFragment.newInstance(EVAKUATOR));
                }
                if (c.equals(TAXI_SIZE_7)) {
                    replaceFragment(R.id.container_home_activity, R.id.container_home, AmragrumFragment.newInstance(TAXI + " " + TAXI_SIZE_7), MapFragment.newInstance(TAXI_SIZE_7));
                }
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void replaceFragment(int id, Fragment fragment) {
        ((HomeActivity) context).getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(id, fragment)
                .commit();
    }

    private void replaceFragment(int id1, int id2, Fragment fragment1, Fragment fragment2) {
        ((HomeActivity) context).getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(id1, fragment1)
                .replace(id2, fragment2)
                .commit();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView button1;
        private final ImageView button3;
        private final TextView name1;
        private final TextView name3;

        MyViewHolder(View view) {
            super(view);
            button1 = (ImageView) view.findViewById(R.id.home_recycler_button_1);
            button3 = (ImageView) view.findViewById(R.id.home_recycler_button_3);
            name1 = (TextView) view.findViewById(R.id.home_recycler_text_1);
            name3 = (TextView) view.findViewById(R.id.home_recycler_text_3);
        }
    }
}