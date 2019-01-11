package com.example.muhammedraheezrahman.newslisting.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.muhammedraheezrahman.newslisting.DataBase.DatabaseHelper;
import com.example.muhammedraheezrahman.newslisting.Model.Articles;
import com.example.muhammedraheezrahman.newslisting.R;

public class NewsDetailActivity  extends RootActivity {

    //region variable_declaration
    private int id;
    private DatabaseHelper databaseHelper;
    private Articles article;
    private TextView titleTv,contentTv,authorTv;
    private ImageView imageTv;
    private String title,content,author,imageURL;
    //endregion


    //region activity_lifecycle
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

        title = String.valueOf(articles.getTitle());
        content = String.valueOf(articles.getContent());
        author = String.valueOf("-- "+articles.getAuthor());
        imageURL = String.valueOf(articles.getUrlToImage());
        titleTv.setText(title);
        contentTv.setText(content);
        authorTv.setText(author);
        Glide.with(getApplicationContext()).load(imageURL).into(imageTv);

    }
    //endregion


    //region fetch_method
    private Articles fetchArticleFromDb(int id) {

        databaseHelper = new DatabaseHelper(getApplicationContext());
        article = databaseHelper.getArticle(id);
        return article;
    }
    //endregion
}
