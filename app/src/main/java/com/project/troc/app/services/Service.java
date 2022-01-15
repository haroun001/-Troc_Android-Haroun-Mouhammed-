package com.project.troc.app.services;

import com.project.troc.app.models.Annonce;


import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface Service {

    @GET("annonces/")
    Call<List<Annonce>> getAll();

    @GET("/{id}")
    Call<Annonce> getByCode(@Path("id") String id);

    @POST("/")
    Call<Annonce> insert(@Body Annonce annonce);

    @PUT("/{id}")
    Call<Annonce> update(@Path("id") String id, @Body Annonce annonce);

    @DELETE("/{id}")
    Call<Annonce> delete(@Path("id") String id);

    @DELETE("/")
    Call<Annonce> deleteAll();
}
