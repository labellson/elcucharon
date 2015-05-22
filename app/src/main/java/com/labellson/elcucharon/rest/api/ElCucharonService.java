package com.labellson.elcucharon.rest.api;

import com.labellson.elcucharon.model.Restaurante;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by dani on 22/05/15.
 */
public interface ElCucharonService {

    @GET("/restaurante")
    void listRestaurantes(Callback<List<Restaurante>> cb);

}
