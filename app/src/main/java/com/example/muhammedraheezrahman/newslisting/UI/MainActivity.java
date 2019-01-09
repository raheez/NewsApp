package com.example.muhammedraheezrahman.newslisting.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.muhammedraheezrahman.newslisting.Model.News;
import com.example.muhammedraheezrahman.newslisting.Networking.APIClient;
import com.example.muhammedraheezrahman.newslisting.Networking.APIService;
import com.example.muhammedraheezrahman.newslisting.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    APIService apiService;
    private String country = "us";
    private String apiKey = "cb81f4bb3c284e799875cc1a23364334";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = APIClient.getClient().create(APIService.class);


        Call<News> callApi = apiService.getNewsList(country,apiKey);
        callApi.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News news = response.body();
                Log.d("Ran","call successfull");
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.d("Ran","call Failed");

            }
        });



    }
}
