package com.labellson.elcucharon.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.labellson.elcucharon.R;
import com.labellson.elcucharon.model.Reserva;
import com.labellson.elcucharon.ui.activities.MainActivity;
import com.labellson.elcucharon.util.restapi.CucharonRestApi;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by dani on 31/05/15.
 */
public class RegisterReservaTask extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private Reserva r;

    public RegisterReservaTask(Context context, Reserva r) {
        this.context = context;
        this.r = r;
        pDialog = new ProgressDialog(context);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage("Cargando...");
        pDialog.setCancelable(true);
        pDialog.setMax(100);
    }

    private ProgressDialog pDialog;

    @Override
    protected void onPreExecute() {
        pDialog.setProgress(0);
        pDialog.show();
    }

    @Override
    protected void onPostExecute(Boolean b) {
        pDialog.dismiss();
        if(b){
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
            Toast.makeText(context, "Reservado con exito :)", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Intentalo mas tarde", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.sp_name), Context.MODE_PRIVATE);
        try {
            return CucharonRestApi.registerReserva(r, sp.getString(context.getString(R.string.sp_user_auth), null));
        } catch (JSONException e) {
            Log.e("JSONException", "RegisterReservaTask", e);
            return false;
        } catch (IOException e) {
            Log.e("IOException", "RegisterReservaTask", e);
            return false;
        }
    }
}
