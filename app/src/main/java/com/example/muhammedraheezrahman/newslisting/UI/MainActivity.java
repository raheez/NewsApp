package com.example.muhammedraheezrahman.newslisting.UI;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.muhammedraheezrahman.newslisting.Adapter.RecyclerAdapter;
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

public class MainActivity extends RootActivity implements RecyclerAdapter.ClickListener {

    //region variable_declaration
    APIService apiService;
    private static final String country = "us";
    private static final String apiKey = "cb81f4bb3c284e799875cc1a23364334";
    private static DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private List<Articles> list;
    private RecyclerAdapter adapter;
    private FloatingActionButton updateButton;
    private ShimmerFrameLayout shimmerFrameLayout;
    private RelativeLayout listContent;
    private static final String TAG = "MAIN_ACTIVITY";
    private boolean isConnected;
    private RelativeLayout relativeLayout;
    //endregion


    //region activity_lifecyle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
        shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_layout);
        listContent = (RelativeLayout) findViewById(R.id.conent_relativelayout);
        updateButton = (FloatingActionButton) findViewById(R.id.fab);
        list = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        llm = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new RecyclerAdapter(getApplicationContext(),list,this);
        recyclerView.setAdapter(adapter);
        apiService = APIClient.getClient().create(APIService.class);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        listContent.setVisibility(View.GONE);
        isConnected = isNetworkConnected();
        if (isConnected) {
            fetchArticleFromWeb();
        }
        else {
            final Snackbar snackbar = Snackbar.make(relativeLayout,"Connection not Available",Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //dismiss
                        }
                    });
            snackbar.show();
        }
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected) {
                    fetchArticleFromWeb();
                }
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    //endregion


    //region article_manipulation
    private void fetchArticleFromWeb(){
        Call<News> callApi = apiService.getNewsList(country,apiKey);
        callApi.enqueue(new Callback<News>() {

            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News news = new News();
                news = response.body();
                List<Articles> list = new ArrayList<>();
                list = news.getArticles();
                insertArticlesToDb(list);
                Log.d(TAG,"Fetch from web successfull"+list.size());
                fetchArticlesFromDb();
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.d(TAG,"Fetch from web failed");
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
        Log.d(TAG,"Size id" + list.size());
        adapter.addToList(lists);
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        listContent.setVisibility(View.VISIBLE);
    }
    //endregion


    //region chech_network_status_method
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    //endregion


    //region move_to_detail_screen_activity
    @Override
    public void propagateToNewsDetailActivity(int id) {
        Intent i = new Intent(MainActivity.this,NewsDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ID",id);
        i.putExtras(bundle);
        startActivity(i);
    }
    //endregion
}
