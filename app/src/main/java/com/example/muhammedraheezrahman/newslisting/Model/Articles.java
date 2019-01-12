package com.example.muhammedraheezrahman.newslisting.Model;

public class Articles {

    //region vaiables
    private String author;
    private String title;
    private String description;
    private String urlToImage;
    private String content;
    private int id;
    private String publishedAt;
    //endregion


    //region database_variable_declarations
    public static final String TABLE_NAME = "articles";

    public static String COLUMN_ID = "id";
    public static String COLUMN_AUTHOR = "author";
    public static String COLUMN_TITLE = "title";
    public static String COLUMN_DESCRIPTION = "description";
    public static String COLUMN_IMAGE = "image";
    public static String COLUMN_CONTENT = "content";
    public static String COLUMN_DATE = "publishedAt";

    public static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME +
            "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_AUTHOR + " TEXT,"
            + COLUMN_TITLE + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_IMAGE + " TEXT,"
            + COLUMN_CONTENT + " TEXT,"
            + COLUMN_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ")";
    //endregion


    //region getters_setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
    //endregion

}
