package com.labellson.elcucharon.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.labellson.elcucharon.model.User;
import com.labellson.elcucharon.util.restapi.CucharonRestApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by dani on 28/05/15.
 */
//Tambien se usa para editar un usuario
public class RegisterTask extends AsyncTask<Void, Void, JSONObject> {

    private Context context;
    private User user;
    private ProgressDialog pDialog;
    private String pass;
    private boolean edit;

    public RegisterTask(User user, String pass, Context context, boolean edit){
        this.user = user;
        this.context = context;
        this.pass = pass;
        this.edit = edit;
        pDialog = new ProgressDialog(context);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage("Cargando...");
        pDialog.setCancelable(true);
        pDialog.setMax(100);
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        try {
            if(edit){
                return CucharonRestApi.editUser(user, pass);
            }else {
                return CucharonRestApi.registerUser(user, pass);
            }
        } catch (JSONException e) {
            Log.e("JSONException", "RegisterTask", e);
            return null;
        } catch (IOException e) {
            Log.e("IOException", "RegisterTask", e);
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        pDialog.setProgress(0);
        pDialog.show();
    }

    @Override
    protected void onPostExecute(JSONObject jObj) {
        pDialog.dismiss();
        String toast = null;
        try {
            if(jObj == null){
                toast = "Estamos realizando mantenimiento, prueba mas tarde";
            }else{
                if (jObj.has("problem")){
                    String problem = jObj.getString("problem");
                    switch (problem){
                        case "email": toast = "El email esta en uso"; break;
                        case "movil": toast = "El movil ya esta registrado"; break;
                        case "dni": toast = "El dni ya esta registrado"; break;
                    }
                }else{
                    User u = User.deserialize(jObj);
                    u.setAuth(user.getAuth());
                    u.save(context);
                    toast = "Usuario creado :)";
                    Intent intent = ((Activity)context).getParentActivityIntent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    //context.startActivity(((Activity) context).getParentActivityIntent());
                    //((Activity) context).finish();
                }
            }
        } catch (JSONException e) {
            Log.e("RegisterTask", "JSONException: Creo que es imposible que pase, pero bueno", e);
        }
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }
}
