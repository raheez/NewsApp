package com.example.muhammedraheezrahman.newslisting.UI;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.muhammedraheezrahman.newslisting.Adapters.RecyclerAdapter;
import com.example.muhammedraheezrahman.newslisting.DataBase.DatabaseHelper;
import com.example.muhammedraheezrahman.newslisting.Model.Articles;
import com.example.muhammedraheezrahman.newslisting.Model.News;
import com.example.muhammedraheezrahman.newslisting.Networking.APIClient;
import com.example.muhammedraheezrahman.newslisting.Networking.APIService;
import com.example.muhammedraheezrahman.newslisting.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends RootActivity {

    //region variable_declaration
    APIService apiService;
    private String country = "us";
    private String apiKey = "cb81f4bb3c284e799875cc1a23364334";
    private static DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private List<Articles> list;
    private RecyclerAdapter adapter;
    private FloatingActionButton updateButton;
    private ShimmerFrameLayout shimmerFrameLayout;
    private RelativeLayout listContent;
    //endregion

    //region activity_lifecyle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_layout);
        listContent = (RelativeLayout) findViewById(R.id.conent_relativelayout);
        updateButton = (FloatingActionButton) findViewById(R.id.fab);
        list = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        llm = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(llm);
        adapter = new RecyclerAdapter(getApplicationContext(),list);
        recyclerView.setAdapter(adapter);
        apiService = APIClient.getClient().create(APIService.class);
        fetchArticleFromWeb();
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchArticleFromWeb();
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


    //region article_manipulation
    private void fetchArticleFromWeb(){
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        listContent.setVisibility(View.GONE);
        Call<News> callApi = apiService.getNewsList(country,apiKey);
        callApi.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News news = new News();
                news = response.body();
                List<Articles> list = new ArrayList<>();
                list = news.getArticles();
                insertArticlesToDb(list);
                Log.d("Ran","call successfull"+list.size());

                fetchArticlesFromDb();

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.d("Ran","call Failed");

            }
        });
    }

    private  void insertArticlesToDb(List<Articles> list) {

        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.insertArticles(list);
    }

    private void fetchArticlesFromDb(){
        databaseHelper = new DatabaseHelper(getApplicationContext());
        List<Articles> lists = databaseHelper.getAllArticles();
        Log.d("Ran","Size id" + list.size());
        adapter.addToList(lists);
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        listContent.setVisibility(View.VISIBLE);

    }
    //endregion

}
