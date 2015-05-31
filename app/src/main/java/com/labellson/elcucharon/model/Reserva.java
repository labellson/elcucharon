package com.labellson.elcucharon.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by dani on 30/05/15.
 */
public class Reserva {

    private int id;
    private Date fecha;
    private int idUser;
    private Restaurante restaurante;

    public Reserva(int id, Date fecha, int idUser, Restaurante restaurante) {
        this.id = id;
        this.fecha = fecha;
        this.idUser = idUser;
        this.restaurante = restaurante;
    }

    public Reserva(Date fecha, int idUser, Restaurante restaurante) {
        this.id = -1;
        this.fecha = fecha;
        this.idUser = idUser;
        this.restaurante = restaurante;
    }

    public JSONObject serializeJSON() throws JSONException {
        JSONObject jObj = new JSONObject();
        jObj.put("id", id);
        jObj.put("fecha", fecha.getTime());
        jObj.put("idUser", idUser);
        jObj.put("idRestaurante", restaurante.getId());
        return jObj;
    }

    public static Reserva deserializeJSON(JSONObject jObj) throws JSONException {
        return new Reserva(jObj.getInt("id"),
                new Date(jObj.getLong("fecha")),
                jObj.getInt("idUser"),
                Restaurante.deserialize(jObj.getJSONObject("idRestaurante"), 200));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }
}
