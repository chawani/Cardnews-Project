package com.example.NewSeconds;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.NewSeconds.dto.Article;

import java.util.ArrayList;


public class ListViewActivity extends ListActivity {
//    ArrayList<Article> article_list=new ArrayList<Article>();
    ArrayList<String> title_list=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list);

//        Intent listIntent=getIntent();
//        article_list = (ArrayList<Article>) listIntent.getSerializableExtra("article_list");

        String sql = "select * from article where category=";
        sql=sql+getIntent().getStringExtra("cate");

        DBHelper dbHelper = new DBHelper(this,"articledb.db",null,1);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql,null);
        while(cursor.moveToNext()){
            title_list.add(cursor.getString(1));
        }

//        for(int i=0;i<article_list.size();i++){
//            title_list.add(article_list.get(i).getTitle());
//        }
        setListAdapter(new ArrayAdapter<String>(this,R.layout.activity_list,R.id.article_list,title_list));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
//        Article selectedArticle=new Article();
        String selection=l.getItemAtPosition(position).toString();
        String select_sql="select * from article where title='"+selection+"'";
//        for(int i=0;i<article_list.size();i++){
//            if(article_list.get(i).getTitle().equals(selection)){
//                selectedArticle=article_list.get(i);
//            }
//        }
        Intent intent=new Intent(getApplicationContext(), FragmentActivity.class);
        intent.putExtra("select_sql",select_sql);
        startActivity(intent);
    }
}