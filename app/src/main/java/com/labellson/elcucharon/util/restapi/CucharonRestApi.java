package com.labellson.elcucharon.util.restapi;

import com.labellson.elcucharon.model.Reserva;
import com.labellson.elcucharon.model.Restaurante;
import com.labellson.elcucharon.model.User;
import com.labellson.elcucharon.util.RequestJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dani on 22/05/15.
 */
public class CucharonRestApi {

    private static final String server = "http://192.168.2.108:8084/elcucharon/restapi";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String AUTH = "Authorization";
    private static final String MEDIATYPE_JSON = "application/json";

    //Traera la lista de restaurantes del servidor
    public static List<Restaurante> listRestaurantes(int imageSize) throws IOException, JSONException {
        String  relativeUri = "/restaurante";
        return Restaurante.deserialize(RequestJson.reqGetJSONArray(server + relativeUri), imageSize);
    }

    public static Restaurante fetchRestaurante(int id, int size) throws IOException, JSONException {
        String relativeUri = "/restaurante/";
        return Restaurante.deserialize(RequestJson.reqGetJSONObject(server + relativeUri + id), size);
    }

    //Logueara al usuario, mediante Basic Authorization. Basta con que u tenga email y pass
    public static User loginUser(User u) throws IOException, JSONException {
        String relativeUri = "/user/email/";

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(AUTH, "Basic "+u.getAuth());

        User loggedUser = User.deserialize(RequestJson.reqGetJSONObject(server + relativeUri + u.getEmail(), headers));
        loggedUser.setAuth(u.getAuth());

        return loggedUser;
    }

    public static JSONObject registerUser(User u, String pass) throws JSONException, IOException {
        String relativeUri = "/user";

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(CONTENT_TYPE, MEDIATYPE_JSON);

        return RequestJson.reqPostJSONObject(server + relativeUri, u.serializeRegisterJSON(pass), headers);
    }

    //Devuelve una lista de fechas para las que el restaurante tiene las reservas llenas
    public static List<Date> fetchReservasRestaurante(int idRestaurante) throws IOException, JSONException {
        String relativeUri= "/reserva/restaurante/";

        JSONArray jArray = RequestJson.reqGetJSONArray(server + relativeUri + idRestaurante);
        List<Date> d = new ArrayList<Date>();

        for(int i=0; i < jArray.length(); i++){
            d.add(new Date(jArray.getJSONObject(i).getLong("fecha")));
        }

        return d;
    }

    //Devuelve una lista de horas en las que el restaurante no tiene reserva
    public static String[] fetchHoraReservas(int idRestaurante, Date d) throws IOException, JSONException {
        String relativeUri= "/reserva/restaurante/";

        JSONArray jArray = RequestJson.reqGetJSONArray(server + relativeUri + idRestaurante +"/"+ d.getTime());
        String[] h = new String[jArray.length()];

        for(int i=0; i < jArray.length(); i++)
            h[i] = jArray.getJSONObject(i).getString("hora");

        return h;
    }

    //POST, para añadir una reserva
    public static boolean registerReserva(Reserva r, String auth) throws JSONException, IOException {
        String relativeUri = "/reserva";

        Map<String, String > headers = new HashMap<String, String>();
        headers.put(CONTENT_TYPE, MEDIATYPE_JSON);
        headers.put(AUTH, "Basic "+ auth);

        return RequestJson.reqPostJSONObject(server + relativeUri, r.serializeJSON(), headers).has("id");
    }

    //Fetch de las reservas de un usuario
    public static List<Reserva> fetchReservas(User u) throws IOException, JSONException {
        String relativeUri = "/reserva/user/";

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(AUTH, "Basic "+ u.getAuth());

        return Reserva.deserializeJSON(RequestJson.reqGetJSONArray(server + relativeUri + u.getId(), headers));
    }

    //Elimina una reserva
    public static void deleteReserva(int idReserva, String auth) throws IOException {
        String relativeUri = "/reserva/";

        Map<String, String> headers = new HashMap<String, String >();
        headers.put(AUTH, "Basic "+auth);

        RequestJson.reqDelete(server + relativeUri + idReserva, headers);
    }
}
