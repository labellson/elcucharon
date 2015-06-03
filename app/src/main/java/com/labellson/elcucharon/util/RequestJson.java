package com.labellson.elcucharon.util;

import android.net.http.AndroidHttpClient;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

            get.setHeaders(getHeaders(headers));

            HttpResponse resp = httpClient.execute(get);
            return EntityUtils.toString(resp.getEntity());
        }finally {
            httpClient.close();
        }
    }

    private static String reqPutJSON(String uri, String json, Map<String, String> headers) throws IOException {
        final AndroidHttpClient httpClient = AndroidHttpClient.newInstance("Android");
        try {
            final HttpPut put = new HttpPut(uri);

            //Añadir la entidad JSON
            StringEntity se = new StringEntity(json);
            put.setEntity(se);

            //Añadimos los headers
            put.setHeaders(getHeaders(headers));

            HttpResponse resp = httpClient.execute(put);
            return EntityUtils.toString(resp.getEntity());
        }finally {
            httpClient.close();
        }
    }

    /**
     * Post al servidor web, que devolvera otro objeto o array json
     * @param uri
     * @param json
     * @param headers
     * @return
     * @throws IOException
     */
    private static String reqPostJSON(String uri, String json, Map<String, String> headers) throws IOException {
        final AndroidHttpClient httpClient = AndroidHttpClient.newInstance("Android");
        try {
            final HttpPost post = new HttpPost(uri);

            //Añadir la entidad JSON
            StringEntity se = new StringEntity(json);
            post.setEntity(se);

            //Añadimos los headers
            post.setHeaders(getHeaders(headers));

            HttpResponse resp = httpClient.execute(post);
            return EntityUtils.toString(resp.getEntity());
        }finally {
            httpClient.close();
        }
    }

    public static void reqDelete(String uri, Map<String, String> headers) throws IOException {
        final AndroidHttpClient httpClient = AndroidHttpClient.newInstance("Android");
        final HttpDelete delete = new HttpDelete(uri);
        delete.setHeaders(getHeaders(headers));
        httpClient.execute(delete);
    }

    /**
     * Transforma el Map en Headers[]
     * @param headers
     * @return
     */
    private static Header[] getHeaders(Map<String, String> headers){
        //Recorremos el map, y añadimos los headers
        if(!headers.isEmpty()){
            Iterator it = headers.keySet().iterator();
            Header[] h = new Header[headers.size()];
            int i=0;
            while(it.hasNext()){
                String key = (String) it.next();
                h[i] = new BasicHeader(key, headers.get(key));
                i++;
            }
            return h;
        }
        return null;
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

    public static JSONObject reqPutJSONObject(String uri, JSONObject jObj, Map<String, String> headers) throws IOException, JSONException {
        return new JSONObject(reqPutJSON(uri, jObj.toString(), headers));
    }

    /**
     * Peticion al servicio web que hace POST de jObj y devuelve un JsonObject
     * @param uri
     * @param jObj
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public static JSONObject reqPostJSONObject(String uri, JSONObject jObj) throws IOException, JSONException {
        return reqPostJSONObject(uri, jObj, new HashMap<String, String>());
    }

    public static JSONObject reqPostJSONObject(String uri, JSONObject jObj, Map<String, String> headers) throws IOException, JSONException {
        return new JSONObject(reqPostJSON(uri, jObj.toString(), headers));
    }
}
