package com.example.muhammedraheezrahman.newslisting.Networking;

import com.example.muhammedraheezrahman.newslisting.Model.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

public interface APIService {

    @GET("top-headlines?")
    Call<News> getNewsLsit(@Field("country")String country, @Field("apiKey") String apikey);
}
