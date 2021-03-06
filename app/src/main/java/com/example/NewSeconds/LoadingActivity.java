package com.example.NewSeconds;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.NewSeconds.dto.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class LoadingActivity extends Activity {

    SQLiteDatabase db;
    SQLiteDatabase db2;
    ArrayList<ArrayList<Article>> all_article=new ArrayList<>();
    ArrayList<Article> article_list100 = new ArrayList<Article>();
    ArrayList<Article> article_list101 = new ArrayList<Article>();
    ArrayList<Article> article_list102 = new ArrayList<Article>();
    ArrayList<Article> article_list103 = new ArrayList<Article>();
    ArrayList<Article> article_list104 = new ArrayList<Article>();
    ArrayList<Article> article_list105 = new ArrayList<Article>();
    AsyncTask networkTask;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        ImageView img=(ImageView)findViewById(R.id.loading_image);
        img.setColorFilter(Color.parseColor("#80000000"), PorterDuff.Mode.DARKEN);

        DBHelper helper;
        helper = new DBHelper(LoadingActivity.this, "articledb.db", null, 1);
        db = helper.getWritableDatabase();
        db2 = helper.getReadableDatabase();
        helper.onCreate(db);

//        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name ='article'" , null);
//        cursor.moveToFirst();
        String url="http://ec2-54-180-133-6.ap-northeast-2.compute.amazonaws.com:5000/init";
        networkTask=new LoadingActivity.JSONTask().execute(url);

    }

    @Override
    public void onStop() {
        super.onStop();
        if(networkTask!=null)
            networkTask.cancel(true);
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            while(!isCancelled()) {
                try {
                    HttpURLConnection con = null;
                    BufferedReader reader = null;

                    try {
                        //URL url = new URL("http://192.168.25.16:3000/users");
                        URL url = new URL(urls[0]);//url??? ????????????.
                        con = (HttpURLConnection) url.openConnection();
                        con.connect();//?????? ??????

                        //?????? ????????? ??????
                        InputStream stream = con.getInputStream();

                        //????????? ??????????????? ????????? ????????? ?????? ????????? ????????????.
                        reader = new BufferedReader(new InputStreamReader(stream));

                        //?????? ???????????? ?????????
                        StringBuffer buffer = new StringBuffer();

                        //line??? ???????????? ?????? ?????? temp ??????
                        String line = "";

                        //??????????????? ?????? reader?????? ???????????? ???????????? ????????????. ??? node.js??????????????? ???????????? ????????????.
                        while ((line = reader.readLine()) != null) {
                            buffer.append(line);
                        }
                        //??? ???????????? String ???????????? ????????????. ????????? protected String doInBackground(String... urls) ??????
                        return buffer.toString();

                        //????????? ???????????? ????????????.
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        //????????? ?????? disconnect???????????? ????????????.
                        if (con != null) {
                            con.disconnect();
                        }
                        try {
                            //????????? ????????????.
                            if (reader != null) {
                                reader.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }//finally ??????
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        //doInBackground???????????? ????????? ????????? ?????? ??????????????? ?????? ????????????.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //System.out.println(result);
            result.replaceAll("&", "&amp;")
                    .replaceAll("<", "&lt;")
                    .replaceAll(">", "&gt;")
                    .replaceAll("((?<!\\\\)(\\\\\\\\)*)(\\\\\\\")", "$1&quot;")
                    .replaceAll("'", "&#x27;")
                    .replaceAll("/", "&#x2F;");
            JSONParse(result);
            pref = getSharedPreferences("UserInfo", MODE_PRIVATE);
            if(pref.getString("id","").length() == 0) {
                Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        public void JSONParse(String jsonStr){
            StringBuilder stringBuilder = new StringBuilder();
            Cursor cursor;
            try {
                JSONArray jsonArray = new JSONArray(jsonStr);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String title = jsonObject.getString("TITLE");
                    String content1 = jsonObject.getString("CONTENT1");
                    String content2 = jsonObject.getString("CONTENT2");
                    String content3 = jsonObject.getString("CONTENT3");
                    String content4 = jsonObject.getString("CONTENT4");
                    String content5 = jsonObject.getString("CONTENT5");
                    String content6 = jsonObject.getString("CONTENT6");
                    String content7 = jsonObject.getString("CONTENT7");
                    String date = jsonObject.getString("DATE");
                    int category = jsonObject.getInt("CATEGORY");
                    int count = jsonObject.getInt("COUNT");
                    String image = jsonObject.getString("IMAGE");

                    String selectQuery = "select * from article where count="+count;
                    cursor = db2.rawQuery(selectQuery, null);
                    cursor.moveToFirst();
                    if (cursor.getCount() <= 0) {
                        ContentValues values = new ContentValues();
                        values.put("title",title);
                        values.put("content1",content1);
                        values.put("content2",content2);
                        values.put("content3",content3);
                        values.put("content4",content4);
                        values.put("content5",content5);
                        values.put("content6",content6);
                        values.put("content7",content7);
                        values.put("date",date);
                        values.put("category",category);
                        values.put("count",count);
                        values.put("image",image);
                        db.insert("article",null,values);
                    }
                    cursor.close();
//                    Article article=new Article(title,content1,content2,content3,content4,content5,content6,content7,date,category,count,image);
//                    switch (category){
//                        case 100:
//                            article_list100.add(article);
//                            break;
//                        case 101:
//                            article_list101.add(article);
//                            break;
//                        case 102:
//                            article_list102.add(article);
//                            break;
//                        case 103:
//                            article_list103.add(article);
//                            break;
//                        case 104:
//                            article_list104.add(article);
//                            break;
//                        case 105:
//                            article_list105.add(article);
//                            break;
//                        default:
//                            break;
//                    }
                }
            } catch (JSONException e) { e.printStackTrace(); }
//            all_article.add(article_list100);
//            all_article.add(article_list101);
//            all_article.add(article_list102);
//            all_article.add(article_list103);
//            all_article.add(article_list104);
//            all_article.add(article_list105);
        }
    }
}
