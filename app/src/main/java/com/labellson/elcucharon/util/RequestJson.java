package com.labellson.elcucharon.util;

import android.net.http.AndroidHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RequestJson {

    /**
     * Peticion al servidor web que devuelve un String en JSON
     * @param uri
     * @param headers
     * @return String
     * @throws IOException
     */
    private static String reqGetJSON(String uri, Map<String, String> headers) throws IOException {
        final AndroidHttpClient httpClient = AndroidHttpClient.newInstance("Android");
        try {
            final HttpGet get = new HttpGet(uri);

            //Recorremos el map, y añadimos los headers
            if(!headers.isEmpty()){
                Iterator it = headers.keySet().iterator();
                while(it.hasNext()){
                    String key = (String) it.next();
                    get.setHeader(key, headers.get(key));
                }
            }

            HttpResponse resp = httpClient.execute(get);
            return EntityUtils.toString(resp.getEntity());
        }finally {
            httpClient.close();
        }
    }

    /**
     * Hace una peticion a un servidor, que devolvera un JSONArray. Estar seguro de la uri devuelva un Array
     * Si devuelve un JSONObject, lanza JSONException
     * @param uri uri de la peticion
     * @return Array JSON
     * @throws IOException
     * @throws JSONException
     */
    public static JSONArray reqGetJSONArray(String uri) throws IOException, JSONException {
        return reqGetJSONArray(uri, new HashMap<String, String>());
    }

    public static JSONArray reqGetJSONArray(String uri, Map<String, String> headers) throws IOException, JSONException {
        return new JSONArray(reqGetJSON(uri, headers));
    }

    /**
     * Peticion a servicio Web que devuelve un JSONObject
     * Si devuelve un JSONArray, lanza JSONException
     * @param uri
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public static JSONObject reqGetJSONObject(String uri) throws IOException, JSONException {
        return reqGetJSONObject(uri, new HashMap<String, String>());
    }

    public static JSONObject reqGetJSONObject(String uri, Map<String, String> headers) throws IOException, JSONException {
        return new JSONObject(reqGetJSON(uri, headers));
    }
}
