package com.labellson.elcucharon.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.labellson.elcucharon.util.restapi.CucharonRestApi;
import com.roomorama.caldroid.CaldroidFragment;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dani on 30/05/15.
 */
public class FetchReservasRestauranteTask extends AsyncTask<Void, Void, List<Date>> {

    private Context context;
    private CaldroidFragment caldroidFragment;
    private ProgressDialog pDialog;
    private int idRestaurante;

    public FetchReservasRestauranteTask(Context context, CaldroidFragment caldroidFragment, int idRestaurante){
        this.context = context;
        this.caldroidFragment = caldroidFragment;
        this.idRestaurante = idRestaurante;
        pDialog = new ProgressDialog(context);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage("Cargando...");
        pDialog.setCancelable(true);
        pDialog.setMax(100);
    }

    @Override
    protected List<Date> doInBackground(Void... params) {
        try {
            return CucharonRestApi.fetchReservasRestaurante(idRestaurante);
        } catch (IOException e) {
            return null;
        } catch (JSONException e) {
            Log.e("JSONException", "FetchReservasRestauranteTask", e);
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        pDialog.setProgress(0);
        pDialog.show();
    }

    @Override
    protected void onPostExecute(List<Date> dates) {
        if(dates == null) return;

        caldroidFragment.setDisableDates((ArrayList<Date>) dates);
        caldroidFragment.refreshView();
        pDialog.dismiss();
    }
}
