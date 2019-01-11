package com.example.muhammedraheezrahman.newslisting.Model;

import java.util.List;

public class News {

    //region variable_declaration
   private String status;
   private int totalResults;
   List<Articles> articles;
   //endregion

    //region getters_setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }
    //endregion
}
