package com.labellson.elcucharon.util.restapi;

import com.labellson.elcucharon.model.Restaurante;
import com.labellson.elcucharon.model.User;
import com.labellson.elcucharon.util.RequestJson;

import org.json.JSONException;

import java.io.IOException;
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

    //Logueara al usuario, mediante Basic Authorization. Basta con que u tenga email y pass
    public static User loginUser(User u) throws IOException, JSONException {
        String relativeUri = "/user/email/";

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(AUTH, "Basic "+u.getAuth());

        User loggedUser = User.deserialize(RequestJson.reqGetJSONObject(server + relativeUri + u.getEmail(), headers));
        loggedUser.setAuth(u.getAuth());

        return loggedUser;
    }

}
