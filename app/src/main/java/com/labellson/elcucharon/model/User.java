package com.labellson.elcucharon.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Base64;

import com.labellson.elcucharon.R;

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

    public void save(Context context){
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.sp_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor sp_editor = sp.edit();

        sp_editor.putBoolean(context.getString(R.string.sp_learned_drawer), true);
        sp_editor.putInt(context.getString(R.string.sp_user_id), id);
        sp_editor.putString(context.getString(R.string.sp_user_email), email);
        sp_editor.putString(context.getString(R.string.sp_user_auth), auth);
        sp_editor.putString(context.getString(R.string.sp_user_movil), movil);
        sp_editor.putString(context.getString(R.string.sp_user_dni), dni);
        sp_editor.putString(context.getString(R.string.sp_user_nombre), nombre);

        sp_editor.commit();
    }

    public static User load(Context context){
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.sp_name), Context.MODE_PRIVATE);
        User u = new User(sp.getString(context.getString(R.string.sp_user_email), null),
                sp.getString(context.getString(R.string.sp_user_movil), null),
                sp.getString(context.getString(R.string.sp_user_dni), null),
                sp.getString(context.getString(R.string.sp_user_nombre), null),
                null, sp.getInt(context.getString(R.string.sp_user_id), -1));
        u.setAuth(sp.getString(context.getString(R.string.sp_user_auth), null));
        return u;
    }


    public JSONObject serializeJSON() throws JSONException {
        JSONObject jObj = new JSONObject();
        jObj.put("id", id);
        jObj.put("email", email);
        if(movil != null) jObj.put("movil", movil);
        if(dni != null) jObj.put("dni", dni);
        if(nombre != null) jObj.put("nombre", nombre);
        return jObj;
    }

    public JSONObject serializeRegisterJSON(String pass) throws JSONException {
        JSONObject jObj = serializeJSON();
        jObj.put("pass", pass);
        return jObj;
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
