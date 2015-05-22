package com.labellson.elcucharon.rest.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by dani on 22/05/15.
 */

//Clase creada para no tener que crear el servicio en cada activity
public class ElCucharonService {

    private static ElCucharonServiceInterface service = null;
    private static final String server = "http://192.168.2.108:8084/elcucharon/restapi";

    private static ElCucharonServiceInterface initService(){
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(server).setConverter(new GsonConverter(gson)).build();
        return restAdapter.create(ElCucharonServiceInterface.class);
    }

    private static void createInstance(){
        if(service == null){
            synchronized (ElCucharonService.class){
                if(service == null){
                    service = initService();
                }
            }
        }
    }

    public static ElCucharonServiceInterface getService(){
        if(service == null) createInstance();
        return service;
    }
}
