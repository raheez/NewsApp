package com.example.muhammedraheezrahman.newslisting.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.muhammedraheezrahman.newslisting.Model.Articles;
import com.example.muhammedraheezrahman.newslisting.R;
import com.example.muhammedraheezrahman.newslisting.UI.NewsDetailActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class ViewHolder extends RecyclerView.ViewHolder{

    //region variable_declaration
    TextView titleTV, descriptionTV,dateTv;
    CircleImageView imageView;
    //endregion


    //region constructor_method
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTV = (TextView) itemView.findViewById(R.id.titleTv);
        descriptionTV = (TextView) itemView.findViewById(R.id.messageTv);
        imageView = (CircleImageView) itemView.findViewById(R.id.statusImage);
        dateTv = (TextView) itemView.findViewById(R.id.dateTv);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Articles articles = RecyclerAdapter.list.get(getAdapterPosition());
                Intent i = new Intent(v.getContext(),NewsDetailActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                int id = articles.getId();
                Bundle bundle = new Bundle();
                bundle.putInt("ID",id);
                i.putExtras(bundle);
                v.getContext().startActivity(i);

            }
        });
    }
    //endregion

}

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    //region variable_declaration
    private Context context;
    public static List<Articles> list;
    //endregion


    //region adapter_methods

    public RecyclerAdapter(Context context, List<Articles> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.listitem,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String imageUrl,title,content,publishedDate;
        Articles articles = list.get(i);
        title = String.valueOf(articles.getTitle());
        content = String.valueOf(articles.getContent());
        imageUrl = String.valueOf(articles.getUrlToImage());
        publishedDate = String.valueOf(articles.getPublishedAt());
        if (articles!=null){
            if (!title.equals("null")){
                viewHolder.titleTV.setText(String.valueOf(title));
            }
            else if (title.equals("null")){
                viewHolder.titleTV.setText("Title Not Available");
            }
            if (!content.equals("null")){
                viewHolder.descriptionTV.setText(String.valueOf(content));
            }
            else if (content.equals("null")){
                viewHolder.descriptionTV.setText("Content Not Available");
            }
            if (!imageUrl.equals("null")){
                Glide.with(context).load(imageUrl).into(viewHolder.imageView);
            }
            else if (imageUrl.equals("null"))
                Glide.with(context).load(R.drawable.placeholdericon).into(viewHolder.imageView);

        }
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
    //endregion


    //region adding_to_list_method
    public  void addToList(List<Articles> articleList){
       this.list.clear();
       this.list.addAll(articleList);
       notifyDataSetChanged();
    }
    //endregion



}

