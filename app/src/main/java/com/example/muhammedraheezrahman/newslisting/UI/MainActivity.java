package com.example.muhammedraheezrahman.newslisting.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.muhammedraheezrahman.newslisting.DataBase.DatabaseHelper;
import com.example.muhammedraheezrahman.newslisting.Model.Articles;
import com.example.muhammedraheezrahman.newslisting.Model.News;
import com.example.muhammedraheezrahman.newslisting.Networking.APIClient;
import com.example.muhammedraheezrahman.newslisting.Networking.APIService;
import com.example.muhammedraheezrahman.newslisting.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //region variable_declaration
    APIService apiService;
    private String country = "us";
    private String apiKey = "cb81f4bb3c284e799875cc1a23364334";
    private static DatabaseHelper databaseHelper;
    //endregion

    //region activity_lifecyle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiService = APIClient.getClient().create(APIService.class);
        Call<News> callApi = apiService.getNewsList(country,apiKey);
        callApi.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News news = new News();
                news = response.body();
                List<Articles> list = new ArrayList<>();
                list = news.getArticles();
                insertArticles(list);
                Log.d("Ran","call successfull"+list.size());


                databaseHelper = new DatabaseHelper(getApplicationContext());
                List<Articles> lists = databaseHelper.getAllArticles();
                Log.d("Ran","Size id" + list.size());
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.d("Ran","call Failed");

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    protected void onStop() {
        super.onStop();
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.deleteAllArticles();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.deleteAllArticles();
    }
    //endregion

    //region insert_article_to_db
    private  void insertArticles(List<Articles> list) {

        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.insertArticles(list);

    }
    //endregion

}
