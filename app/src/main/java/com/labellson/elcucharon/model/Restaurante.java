package com.labellson.elcucharon.model;

import android.widget.ImageView;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

/**
 * Created by labellson on 21/05/15.
 */
public class Restaurante {

    @Expose
    private final int id;
    @Expose
    private final String nombre;
    @Expose
    private final BigDecimal lng;
    @Expose
    private final BigDecimal lat;
    @Expose
    private final int mesas;
    @Expose
    private final String descripcion;
    @Expose
    private final ImageView foto;

    public Restaurante(int id, String nombre, String descripcion, int nMesas, ImageView foto, BigDecimal lng, BigDecimal lat){
        this.id = id;
        this.nombre = nombre;
        this.lng = lng;
        this.lat = lat;
        this.mesas = nMesas;
        this.descripcion = descripcion;
        this.foto = foto;
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

    public ImageView getFoto() {
        return foto;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
