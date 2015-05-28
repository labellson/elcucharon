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

    public LoginTask(User u, Context context, ProgressDialog pDialog){
        user = u;
        this.pDialog = pDialog;
        this.context = context;
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
            SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.sp_name), Context.MODE_PRIVATE);
            SharedPreferences.Editor sp_editor = sp.edit();

            sp_editor.putBoolean(context.getString(R.string.sp_learned_drawer), true);
            sp_editor.putInt(context.getString(R.string.sp_user_id), u.getId());
            sp_editor.putString(context.getString(R.string.sp_user_email), u.getEmail());
            sp_editor.putString(context.getString(R.string.sp_user_auth), u.getAuth());
            sp_editor.putString(context.getString(R.string.sp_user_movil), u.getMovil());
            sp_editor.putString(context.getString(R.string.sp_user_dni), u.getDni());
            sp_editor.putString(context.getString(R.string.sp_user_nombre), u.getNombre());

            sp_editor.commit();

            Activity a = (Activity) context;
            a.recreate();
            //a.finish();
            //a.startActivity(a.getIntent());
        }else{
            Toast.makeText(context, "Introduce un usuario valido", Toast.LENGTH_SHORT).show();
        }
    }
}
