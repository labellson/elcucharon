package com.labellson.elcucharon.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.labellson.elcucharon.R;
import com.labellson.elcucharon.model.User;
import com.labellson.elcucharon.util.restapi.CucharonRestApi;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by dani on 27/05/15.
 */
public class LoginTask extends AsyncTask<Void, Void, User> {

    private User user;
    private Context context;
    private ProgressDialog pDialog;

    public LoginTask(User u, Context context){
        user = u;
        this.context = context;
        pDialog = new ProgressDialog(context);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage("Cargando...");
        pDialog.setCancelable(true);
        pDialog.setMax(100);
    }

    @Override
    protected void onPreExecute() {
        pDialog.setProgress(0);
        pDialog.show();
    }

    @Override
    protected User doInBackground(Void... params) {
        User u;
        try {
            u = CucharonRestApi.loginUser(user);
        } catch (IOException e) {
            Log.e("IOException", "onClick loginUser", e);
            return null;
        } catch (JSONException e) {
            Log.e("JSONException", "onClick loginUser", e);
            return null;
        }
        return u;
    }

    @Override
    protected void onPostExecute(User u) {
        pDialog.dismiss();
        if(u != null){
            u.save(context);
            Activity a = (Activity) context;
            a.recreate();
            //a.finish();
            //a.startActivity(a.getIntent());
        }else{
            Toast.makeText(context, "Introduce un usuario valido", Toast.LENGTH_SHORT).show();
        }
    }
}
