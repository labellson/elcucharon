package com.labellson.elcucharon.tasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.labellson.elcucharon.model.Restaurante;
import com.labellson.elcucharon.util.restapi.CucharonRestApi;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by dani on 29/05/15.
 */
public class FetchRestauranteTask extends AsyncTask<Void, Void, Restaurante> {

    private Context context;
    private ImageView imageView;
    private TextView textView;
    private Restaurante restaurante;
    private int size;
    private ProgressBar pBar;

    public FetchRestauranteTask(Context context, ImageView imageView, TextView textView, Restaurante restaurante, int size, ProgressBar pBar) {
        this.context = context;
        this.imageView = imageView;
        this.textView = textView;
        this.restaurante = restaurante;
        this.size = size;
        this.pBar = pBar;
    }

    @Override
    protected void onPreExecute() {
        if(pBar != null) pBar.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Restaurante restaurante) {
        if(restaurante == null) return;

        ((Activity) context).setTitle(restaurante.getNombre());
        imageView.setImageBitmap(restaurante.getFoto());
        textView.setText(restaurante.getDescripcion());
        if(pBar != null) pBar.setVisibility(View.GONE);
    }

    @Override
    protected Restaurante doInBackground(Void... params) {
        try {
            return CucharonRestApi.fetchRestaurante(restaurante.getId(), size);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
