package com.example.muhammedraheezrahman.newslisting.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.muhammedraheezrahman.newslisting.DataBase.DatabaseHelper;
import com.example.muhammedraheezrahman.newslisting.Model.Articles;
import com.example.muhammedraheezrahman.newslisting.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsDetailActivity  extends RootActivity {

    //region variable_declaration
    private int id;
    private DatabaseHelper databaseHelper;
    private Articles article;
    private TextView titleTv,contentTv,authorTv,dateTv;
    private ImageView imageTv;
    private String title,content,author,imageURL;
    private String publishedDate;
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
        dateTv = (TextView) findViewById(R.id.dateTv);


        Bundle p = getIntent().getExtras();
        if (p!=null){
            id =p.getInt("ID");
        }
        Articles articles = fetchArticleFromDb(id);

        title = String.valueOf(articles.getTitle());
        content = String.valueOf(articles.getContent());
        author = String.valueOf("-- "+articles.getAuthor());
        imageURL = String.valueOf(articles.getUrlToImage());
        publishedDate = String.valueOf(articles.getPublishedAt());

        if (!title.equals("null")){
            titleTv.setText(title);
        }
        else if (title.equals("null")){
            titleTv.setText("Title Not Available");
        }

        if (!content.equals("null")){
            contentTv.setText(content);
        }
        else if (content.equals("null")){
            contentTv.setText("Content Not Available");
        }
        if (!author.equals("null")){
            authorTv.setText(author);
        }
        else if (author.equals("null")){
            authorTv.setText("Author Not Available");
        }

        if (!imageURL.equals("null"))
        Glide.with(getApplicationContext()).load(imageURL).into(imageTv);
        else if (imageURL.equals("null")){
            Glide.with(getApplicationContext()).load(R.drawable.placeholdericon).into(imageTv);
        }
        if (!publishedDate.equals("null")){
            dateTv.setText(String.valueOf(formatDate(publishedDate)));
        }
        else if (publishedDate.equals("null")){
            dateTv.setText(String.valueOf(""));
        }

    }
    //endregion


    //region fetch_method
    private Articles fetchArticleFromDb(int id) {

        databaseHelper = new DatabaseHelper(getApplicationContext());
        article = databaseHelper.getArticle(id);
        return article;
    }
    //endregion

    //region date_formatting_method
    public String formatDate(String dateString){
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = dateFormat.parse(dateString);
            return dateFormat.format(date);

        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    //endregion
}
