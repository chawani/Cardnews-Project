package com.example.NewSeconds;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.NewSeconds.dto.Article;

import java.util.ArrayList;


public class ListViewActivity extends ListActivity {
    ArrayList<Article> article_list=new ArrayList<Article>();
    ArrayList<String> title_list=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list);

        Intent listIntent=getIntent();
        article_list = (ArrayList<Article>) listIntent.getSerializableExtra("article_list");
        for(int i=0;i<article_list.size();i++){
            title_list.add(article_list.get(i).getTitle());
        }
        setListAdapter(new ArrayAdapter<String>(this,R.layout.activity_list,R.id.article_list,title_list));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Article selectedArticle=new Article();
        String selection=l.getItemAtPosition(position).toString();
        for(int i=0;i<article_list.size();i++){
            if(article_list.get(i).getTitle().equals(selection)){
                selectedArticle=article_list.get(i);
            }
        }
        Intent intent=new Intent(getApplicationContext(), FragmentActivity.class);
        intent.putExtra("selectedArticle",selectedArticle);
        startActivity(intent);
    }
}