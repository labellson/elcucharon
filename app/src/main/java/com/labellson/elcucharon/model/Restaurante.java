package com.labellson.elcucharon.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.labellson.elcucharon.util.DecodeBitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by labellson on 21/05/15.
 */
public class Restaurante implements Serializable {

    private final int id;
    private final String nombre;
    private final BigDecimal lng;
    private final BigDecimal lat;
    private final int mesas;
    private final String descripcion;
    private transient Bitmap foto;

    public Restaurante(int id, String nombre, String descripcion, int nMesas, Bitmap foto, BigDecimal lng, BigDecimal lat){
        this.id = id;
        this.nombre = nombre;
        this.lng = lng;
        this.lat = lat;
        this.mesas = nMesas;
        this.descripcion = descripcion;
        this.foto = foto;
    }

    public static Restaurante deserialize(JSONObject jObj, int imageSize) throws JSONException {
        return new Restaurante(jObj.getInt("id"), jObj.getString("nombre"),
                jObj.getString("descripcion"), jObj.getInt("mesas"),
                jObj.getString("foto") != null ? DecodeBitmap.decodeSampledBitmapFromBase64(jObj.getString("foto"), imageSize, imageSize) : null,
                BigDecimal.valueOf(jObj.getDouble("lng")),
                BigDecimal.valueOf(jObj.getDouble("lat")));
    }

    public static List<Restaurante> deserialize(JSONArray jArray, int imageSize) throws JSONException {
        List<Restaurante> restaurantes = new ArrayList<Restaurante>();
        for(int i=0; i < jArray.length(); i++){
            JSONObject restaurante = jArray.getJSONObject(i);
            restaurantes.add(deserialize(restaurante, imageSize));
        }
        return restaurantes;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public int getMesas() {
        return mesas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
