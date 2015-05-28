package com.labellson.elcucharon.model;

import android.graphics.Bitmap;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dani on 27/05/15.
 */
public class User {

    private int id;
    private String email;
    private String auth; //Authentication
    private String movil;
    private String dni;
    private String nombre;
    private String foto;

    public User(String email,  String movil, String dni, String nombre, String foto, String pass, int id) {
        this(email, movil, dni, nombre, foto);
        this.id = id;
        auth = Base64.encodeToString((email+":"+pass).getBytes(), Base64.NO_WRAP);
    }

    public User(String email, String movil, String dni, String nombre, String foto, String pass){
        this(email, movil, dni, nombre, foto);
        auth = Base64.encodeToString((email+":"+pass).getBytes(), Base64.NO_WRAP);
    }

    public User(String email, String movil, String dni, String nombre, String foto, int id){
        this(email, movil, dni, nombre, foto);
        this.id = id;
    }

    public User(String email, String movil, String dni, String nombre, String foto){
        this.email = email;
        this.movil = movil;
        this.dni = dni;
        this.nombre = nombre;
        this.foto = foto;
    }

    public User(String email, String pass){
        this.email = email;
        auth = Base64.encodeToString((email+":"+pass).getBytes(), Base64.NO_WRAP);
    }


    private static JSONObject serializeUser(User u) throws JSONException {
        JSONObject jObj = new JSONObject();
        jObj.put("id", u.getId());
        jObj.put("email", u.getEmail());
        if(u.getMovil() != null) jObj.put("movil", u.getMovil());
        if(u.getDni() != null) jObj.put("dni", u.getDni());
        if(u.getNombre() != null) jObj.put("nombre", u.getNombre());
        return jObj;
    }

    public static String serialize(User u) throws JSONException {
        return serializeUser(u).toString();
    }

    public static String serializeRegiter(User u, String pass) throws JSONException {
        JSONObject jObj = serializeUser(u);
        jObj.put("pass", pass);
        return jObj.toString();
    }

    public static User deserialize(JSONObject jObj) throws JSONException {
        return new User(jObj.getString("email"),
                jObj.getString("movil"),
                jObj.getString("dni"),
                jObj.getString("nombre"),
                jObj.getString("foto"),
                jObj.getInt("id"));
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getAuth(){
        return auth;
    }

    public void setAuth(String auth){
        this.auth = auth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
