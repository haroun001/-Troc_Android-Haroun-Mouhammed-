package com.project.troc.app.controllers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.troc.app.activities.MainActivity;
import com.project.troc.app.config.RetrofitConfig;
import com.project.troc.app.config.Server;
import com.project.troc.app.models.Annonce;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

public class AnnonceController {

    private static ArrayList<Annonce> annonceList = new ArrayList<>();
    private static Annonce annonce;

    public static ArrayList<Annonce> getAnnonceList() {
        return annonceList;
    }
    public static ArrayList<Annonce> getAnnonce() {
        ArrayList<Annonce> arrayList = new ArrayList<>();
        arrayList.add(annonce);
        return arrayList;
    }

    public static void getAllStudentsVolley(final Context mainContext, final MainActivity current, final Method methodAfterFinished) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Server.BASE_URL + "students", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Annonce[] array = gson.fromJson(response,Annonce[].class);

                annonceList.clear();
                Collections.addAll(annonceList, array);
                try {
                    methodAfterFinished.invoke(current);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    Toast.makeText(mainContext, "Unexpected error. (status: 0013)", Toast.LENGTH_SHORT).show();
                    System.exit(0013);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mainContext, "Error communicating with server. Try again later.", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(mainContext);
        requestQueue.add(stringRequest);
    }

    public static void getStudent(String code, final Context mainContext, final MainActivity current, final Method methodAfterFinished) {

        Call<Annonce> call = new RetrofitConfig().getService().getByCode(code);

        call.enqueue(new Callback<Annonce>() {
            @Override
            public void onResponse(retrofit.Response<Annonce> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    annonce = response.body();
                    try {
                        methodAfterFinished.invoke(current);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        Toast.makeText(mainContext, "Unexpected error. (status: 0013)", Toast.LENGTH_SHORT).show();
                        System.exit(0013);
                    }
                }
                else{
                    Toast.makeText(mainContext, "Error communicating with server. Try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(mainContext, "Error communicating with server. Try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getAllStudentsRetrofit(final Context mainContext, final MainActivity current, final Method methodAfterFinished) {

        Call<List<Annonce>> call = new RetrofitConfig().getService().getAll();

        call.enqueue(new Callback<List<Annonce>>() {
            @Override
            public void onResponse(retrofit.Response<List<Annonce>> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    annonceList.clear();
                    for(int i = 0; i < response.body().size(); i++) {
                        annonceList.add((Annonce) response.body().get(i));
                    }
                    try {
                        methodAfterFinished.invoke(current);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        Toast.makeText(mainContext, "Unexpected error. (status: 0013)", Toast.LENGTH_SHORT).show();
                        System.exit(0013);
                    }
                }
                else{
                    Toast.makeText(mainContext, "Error communicating with server. Try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(mainContext, "Error communicating with server. Try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void insertStudent(final Context mainContext, String code, String name, String email){

        Annonce annonce = null;

        try {
            annonce = new Annonce(code, name, email);
        }
        catch (Exception e) {
            Toast.makeText(mainContext, "Student data was entered incorrectly.", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<Annonce> call = new RetrofitConfig().getService().insert(annonce);
        call.enqueue(new Callback<Annonce>() {
            @Override
            public void onResponse(retrofit.Response<Annonce> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    Toast.makeText(mainContext, "Student was included successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(mainContext, "Error inserting student.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(mainContext, "Error communicating with server.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public static void updateStudent(final Context mainContext, String id, String titre, String descreption){

        Annonce annonce = null;

        try {
            annonce = new Annonce(id, titre, descreption);
        }
        catch (Exception e) {
            Toast.makeText(mainContext, "Student data was entered incorrectly.", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<Annonce> call = new RetrofitConfig().getService().update(id, annonce);
        call.enqueue(new Callback<Annonce>() {
            @Override
            public void onResponse(retrofit.Response<Annonce> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    Toast.makeText(mainContext, "Student was updated successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(mainContext, "Error updating student.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(mainContext, "Error communicating with server.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public static void deleteStudent(final Context mainContext, String code) {

        Call<Annonce> call = new RetrofitConfig().getService().delete(code);
        call.enqueue(new Callback<Annonce>(){
            @Override
            public void onResponse(retrofit.Response<Annonce> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    Toast.makeText(mainContext, "Student was deleted successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(mainContext, "Error deleting student.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(mainContext, "Error communicating with server.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public static void deleteAllStudents(final Context mainContext) {

        Call<Annonce> call = new RetrofitConfig().getService().deleteAll();
        call.enqueue(new Callback<Annonce>(){
            @Override
            public void onResponse(retrofit.Response<Annonce> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    Toast.makeText(mainContext, "All annonces were deleted successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(mainContext, "Error deleting annonces.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(mainContext, "Error communicating with server.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
