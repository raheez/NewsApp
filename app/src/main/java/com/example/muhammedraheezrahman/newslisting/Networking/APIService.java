package com.example.muhammedraheezrahman.newslisting.Networking;

import com.example.muhammedraheezrahman.newslisting.Model.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("top-headlines")
    Call<News> getNewsList(@Query("country") String country, @Query("apiKey") String apikey);

}
