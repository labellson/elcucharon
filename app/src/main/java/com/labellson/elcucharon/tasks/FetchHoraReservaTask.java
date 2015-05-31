package com.labellson.elcucharon.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.labellson.elcucharon.R;
import com.labellson.elcucharon.ui.activities.ReservarActivity;
import com.labellson.elcucharon.util.restapi.CucharonRestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by dani on 31/05/15.
 */
public class FetchHoraReservaTask extends AsyncTask<Void, Void, String[]> {

    private Context context;
    private int idRestaurante;
    private Date date;
    private ProgressDialog pDialog;
    private ReservarActivity.SelectHourDialogFragment dFragment;

    public FetchHoraReservaTask(Context context, int idRestaurante, Date date, ReservarActivity.SelectHourDialogFragment dFragment) {
        this.context = context;
        this.idRestaurante = idRestaurante;
        this.date = date;
        this.dFragment = dFragment;
        pDialog = new ProgressDialog(context);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage("Cargando...");
        pDialog.setCancelable(true);
        pDialog.setMax(100);
    }

    @Override
    protected String[] doInBackground(Void... params) {
        try {
            return CucharonRestApi.fetchHoraReservas(idRestaurante, date);
        } catch (IOException e) {
            Log.e("IOException", "fetchHoraReservas",e);
            return null;
        } catch (JSONException e) {
            Log.e("JSONException", "fetchHoraReservas", e);
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        pDialog.setProgress(0);
        pDialog.show();
    }

    @Override
    protected void onPostExecute(String[] strings) {
        if(strings == null) return;
        dFragment.setData(strings);
        ((Activity)context).findViewById(R.id.btn_elegir_hora).setVisibility(View.VISIBLE);
        pDialog.dismiss();
    }
}
