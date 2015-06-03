package com.labellson.elcucharon.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.labellson.elcucharon.R;
import com.labellson.elcucharon.util.DecodeBitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

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
    private Bitmap foto;

    public User(String email,  String movil, String dni, String nombre, Bitmap foto, String pass, int id) {
        this(email, movil, dni, nombre, foto);
        this.id = id;
        auth = Base64.encodeToString((email+":"+pass).getBytes(), Base64.NO_WRAP);
    }

    public User(String email, String movil, String dni, String nombre, Bitmap foto, String pass){
        this(email, movil, dni, nombre, foto);
        auth = Base64.encodeToString((email+":"+pass).getBytes(), Base64.NO_WRAP);
    }

    public User(String email, String movil, String dni, String nombre, Bitmap foto, int id){
        this(email, movil, dni, nombre, foto);
        this.id = id;
    }

    public User(String email, String movil, String dni, String nombre, Bitmap foto){
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
        if(foto != null) sp_editor.putString(context.getString(R.string.sp_user_foto), encodeFotoBase64());

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
        if(sp.contains(context.getString(R.string.sp_user_foto))){
            byte[] b = Base64.decode(sp.getString(context.getString(R.string.sp_user_foto), ""), Base64.DEFAULT);
            u.setFoto(BitmapFactory.decodeByteArray(b, 0, b.length));
        }
        return u;
    }

    private String encodeFotoBase64(){
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        foto.compress(Bitmap.CompressFormat.PNG, 100, ba);
        byte [] b = ba.toByteArray();
        return Base64.encodeToString(b, Base64.NO_WRAP);
    }

    public JSONObject serializeJSON() throws JSONException {
        JSONObject jObj = new JSONObject();
        jObj.put("id", id);
        if(email != null) jObj.put("email", email);
        if(movil != null) jObj.put("movil", movil);
        if(dni != null) jObj.put("dni", dni);
        if(nombre != null) jObj.put("nombre", nombre);
        if(foto != null) jObj.put("foto", encodeFotoBase64());
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
                jObj.optString("foto").isEmpty() ? null : DecodeBitmap.decodeSampledBitmapFromBase64(jObj.getString("foto"), 256, 256),
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

    public void setNewAuth(String pass){
        auth = Base64.encodeToString((email+":"+pass).getBytes(), Base64.NO_WRAP);
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

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
