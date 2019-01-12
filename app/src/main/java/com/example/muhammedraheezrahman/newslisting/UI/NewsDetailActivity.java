package com.example.muhammedraheezrahman.newslisting.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.muhammedraheezrahman.newslisting.DataBase.DatabaseHelper;
import com.example.muhammedraheezrahman.newslisting.Model.Articles;
import com.example.muhammedraheezrahman.newslisting.R;
import com.facebook.shimmer.ShimmerFrameLayout;

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
    private CardView cardView;
    private ShimmerFrameLayout shimmerFrameLayout;
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
        cardView = (CardView) findViewById(R.id.cardview);
        shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_detail_layout);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.INVISIBLE);

        Bundle p = getIntent().getExtras();
        if (p!=null){
            id =p.getInt("ID");
        }

        if (id!=0){
            Articles articles = fetchArticleFromDb(id);
            displayArticle(articles);
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


    //region display_article_method
    public void displayArticle(Articles article){
        Articles articles = article;

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
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        cardView.setVisibility(View.VISIBLE);
    }
    //endregion


    //region date_formatting_method
    public String formatDate(String dateString){
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date date = dateFormat.parse(dateString);
            SimpleDateFormat fmtOut = new SimpleDateFormat("HH:mm:ss, dd MMM yyyy ");

            return fmtOut.format(date);

        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    //endregion
}
