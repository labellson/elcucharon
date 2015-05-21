package com.labellson.elcucharon.ui.model;

import android.widget.ImageView;

import java.math.BigDecimal;

/**
 * Created by labellson on 21/05/15.
 */
public class Restaurante {

    private final int id;
    private final String nombre;
    private final BigDecimal lng;
    private final BigDecimal lat;
    private final int nMesas;
    private final String descripcion;
    private final ImageView foto;

    public Restaurante(int id, String nombre, String descripcion, int nMesas, ImageView foto, BigDecimal lng, BigDecimal lat){
        this.id = id;
        this.nombre = nombre;
        this.lng = lng;
        this.lat = lat;
        this.nMesas = nMesas;
        this.descripcion = descripcion;
        this.foto = foto;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public int getnMesas() {
        return nMesas;
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
