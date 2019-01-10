package com.example.muhammedraheezrahman.newslisting.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.muhammedraheezrahman.newslisting.DataBase.DatabaseHelper;
import com.example.muhammedraheezrahman.newslisting.Model.Articles;
import com.example.muhammedraheezrahman.newslisting.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsDetailActivity  extends RootActivity {
    private int id;
    private DatabaseHelper databaseHelper;
    private Articles article;
    private TextView titleTv,contentTv,authorTv;
    ImageView imageTv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        titleTv = (TextView) findViewById(R.id.titleTv);
        contentTv = (TextView) findViewById(R.id.contentTv);
        imageTv = (ImageView) findViewById(R.id.image);
        authorTv = (TextView) findViewById(R.id.authorTv);


        Bundle p = getIntent().getExtras();
        if (p!=null){
            id =p.getInt("ID");
        }
        Articles articles = fetchArticleFromDb(id);

        titleTv.setText(String.valueOf(articles.getTitle()));
        contentTv.setText(String.valueOf(articles.getContent()));
        authorTv.setText(String.valueOf("-- "+articles.getAuthor()));
        Glide.with(getApplicationContext()).load(articles.getUrlToImage()).into(imageTv);

    }

    private Articles fetchArticleFromDb(int id) {

        databaseHelper = new DatabaseHelper(getApplicationContext());
        article = databaseHelper.getArticle(id);
        return article;
    }
}
