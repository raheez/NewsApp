package com.example.muhammedraheezrahman.newslisting.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.muhammedraheezrahman.newslisting.Model.Articles;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //region variable_declaration
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "news_db";
    Articles articles;
    //endregion

    //region constuctor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //endregion

    //region methods
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Articles.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Articles.TABLE_NAME);
    }
    //endregion

    //region CRUD_operation
    public  long insertArticles(List<Articles> list){
        long  id = 0;
        deleteAllArticles();
        SQLiteDatabase database = this.getWritableDatabase();

        for (int i = 0;i<list.size();i++) {
            articles = list.get(i);

            ContentValues contentValues = new ContentValues();
            contentValues.put(Articles.COLUMN_AUTHOR, articles.getAuthor());
            contentValues.put(Articles.COLUMN_TITLE, articles.getTitle());
            contentValues.put(Articles.COLUMN_CONTENT, articles.getContent());
            contentValues.put(Articles.COLUMN_DESCRIPTION, articles.getDescription());
            contentValues.put(Articles.COLUMN_IMAGE, articles.getUrlToImage());

            id = database.insert(Articles.TABLE_NAME,null,contentValues);
        }
        database.close();
        return id;
    }

    public List<Articles> getAllArticles(){
        List<Articles> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Articles.TABLE_NAME;
        Cursor cursor = database.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                Articles articles = new Articles();

                articles.setId(cursor.getInt(cursor.getColumnIndex(Articles.COLUMN_ID)));
                articles.setAuthor(cursor.getString(cursor.getColumnIndex(Articles.COLUMN_AUTHOR)));
                articles.setContent(cursor.getString(cursor.getColumnIndex(Articles.COLUMN_CONTENT)));
                articles.setDescription(cursor.getString(cursor.getColumnIndex(Articles.COLUMN_DESCRIPTION)));
                articles.setTitle(cursor.getString(cursor.getColumnIndex(Articles.COLUMN_TITLE)));
                articles.setUrlToImage(cursor.getString(cursor.getColumnIndex(Articles.COLUMN_IMAGE)));
                list.add(articles);

            }while (cursor.moveToNext());

        }
        database.close();
        return list;
    }

    public void deleteAllArticles(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM "+Articles.TABLE_NAME);
        database.close();
    }

    public Articles getArticle(int id){
        Articles article = new Articles();
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(Articles.TABLE_NAME,new String[]{Articles.COLUMN_ID,
                Articles.COLUMN_TITLE,Articles.COLUMN_AUTHOR,Articles.COLUMN_CONTENT,
                Articles.COLUMN_IMAGE,Articles.COLUMN_DESCRIPTION},Articles.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},null,null,null);
        if (cursor!=null){
            cursor.moveToFirst();
            article.setId(cursor.getInt(cursor.getColumnIndex(Articles.COLUMN_ID)));
            article.setAuthor(cursor.getString(cursor.getColumnIndex(Articles.COLUMN_AUTHOR)));
            article.setContent(cursor.getString(cursor.getColumnIndex(Articles.COLUMN_CONTENT)));
            article.setDescription(cursor.getString(cursor.getColumnIndex(Articles.COLUMN_DESCRIPTION)));
            article.setTitle(cursor.getString(cursor.getColumnIndex(Articles.COLUMN_TITLE)));
            article.setUrlToImage(cursor.getString(cursor.getColumnIndex(Articles.COLUMN_IMAGE)));
        }
        cursor.close();
        database.close();
        return article;
    }
    //endregion

}
