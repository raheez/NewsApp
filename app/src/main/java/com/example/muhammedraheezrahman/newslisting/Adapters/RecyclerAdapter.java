package com.example.muhammedraheezrahman.newslisting.Adapters;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class ViewHolder extends RecyclerView.ViewHolder{

    TextView titleTV, descriptionTV;
    CircleImageView imageView;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTV = (TextView) itemView.findViewById(R.id.titleTv);
        descriptionTV = (TextView) itemView.findViewById(R.id.messageTv);
        imageView = (CircleImageView) itemView.findViewById(R.id.statusImage);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Articles articles = RecyclerAdapter.list.get(getAdapterPosition());

                Toast.makeText(v.getContext(),"id is "+ articles.getId(),Toast.LENGTH_SHORT).show();

            }
        });
    }


}
public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    public static List<Articles> list;

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
        String imageUrl,title,content;
        Articles articles = list.get(i);
        title = articles.getTitle();
        content = articles.getContent();
        imageUrl = articles.getUrlToImage();
        if (articles!=null){
            if (title!="null"){
                viewHolder.titleTV.setText(String.valueOf(articles.getTitle()));
            }
            if (content!="null"){
                viewHolder.descriptionTV.setText(String.valueOf(articles.getContent()));
            }
            if (imageUrl=="null"){
                Glide.with(context).load(R.drawable.placeholdericon).into(viewHolder.imageView);
            }
            else if (imageUrl != null)
                Glide.with(context).load(articles.getUrlToImage()).error(R.drawable.placeholdericon).into(viewHolder.imageView);

        }

    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public  void addToList(List<Articles> articleList){
       this.list.clear();
       this.list.addAll(articleList);
       notifyDataSetChanged();
    }
}

