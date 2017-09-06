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

import com.example.android.userapplication.Activityes.HomeActivity;
import com.example.android.userapplication.Fragments.AraqichAmragrumFragment;
import com.example.android.userapplication.Fragments.AraqichFragment;
import com.example.android.userapplication.Fragments.TaxiAmragrumFragment;
import com.example.android.userapplication.Fragments.TaxiFragment;
import com.example.android.userapplication.Model.LogoModel;
import com.example.android.userapplication.R;

import java.util.List;

public class HomeServiceRecyclerAdapter extends RecyclerView.Adapter<HomeServiceRecyclerAdapter.MyViewHolder> {

    private static final String TAXI = "TAXI";
    private static final String ARAQICH = "ARAQICH";
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
        if (position == 0) holder.button.setImageResource(R.mipmap.ic_taxi_256);
        if (position == 1) holder.button.setImageResource(R.mipmap.ic_shipping_256);
        if (position == 2) holder.button.setImageResource(R.mipmap.ic_evacuator_256);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    alertDialog("Patvirel", "Amragrel", TAXI);
                }
                if (position == 1) {
                    alertDialog("Patvirel", "Amragrel", ARAQICH);
                }
                if (position == 2) {

                }
            }
        });

    }

    private void alertDialog(String a, String b, final String c) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setCancelable(false).setPositiveButton(a, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (c.equals(TAXI)) {
                    replaceFragment(TaxiFragment.newInstance());
                }
                if (c.equals(ARAQICH)) {
                    replaceFragment(AraqichFragment.newInstance());
                }
            }
        }).setNegativeButton(b, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (c.equals(TAXI)) {
                    replaceFragment(TaxiAmragrumFragment.newInstance());
                }
                if (c.equals(ARAQICH)) {
                    replaceFragment(AraqichAmragrumFragment.newInstance());
                }
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void replaceFragment(Fragment fragment) {
        ((HomeActivity) context).getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container_home_activity, fragment)
                .commit();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView button;

        MyViewHolder(View view) {
            super(view);
            button = (ImageView) view.findViewById(R.id.home_recycler_button);
        }
    }


}