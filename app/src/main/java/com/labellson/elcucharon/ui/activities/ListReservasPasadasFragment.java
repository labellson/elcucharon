package com.labellson.elcucharon.ui.activities;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.labellson.elcucharon.R;
import com.labellson.elcucharon.model.Reserva;
import com.labellson.elcucharon.model.User;
import com.labellson.elcucharon.ui.adapter.ListReservaAdapter;
import com.labellson.elcucharon.util.restapi.CucharonRestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListReservasPasadasFragment extends Fragment {

    private RecyclerView recView;
    private List<Reserva> mReservas;
    private ListReservaAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_reservas, container, false);

        recView = (RecyclerView) view.findViewById(R.id.rec_view_list_reserva);
        recView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new ListReservaAdapter(mReservas);
        recView.setAdapter(mAdapter);

        return view;
    }

    public void setmReservas(List<Reserva> mReservas) {
        this.mReservas = mReservas;
    }
}
