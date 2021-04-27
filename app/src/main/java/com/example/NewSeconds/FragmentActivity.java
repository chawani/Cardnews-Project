package com.example.NewSeconds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.NewSeconds.dto.Article;

public class FragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

//        Intent intent = getIntent();
        String select_sql=getIntent().getStringExtra("select_sql");

        DBHelper dbHelper = new DBHelper(this,"articledb.db",null,1);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(select_sql,null);

//        Article selectedArticle = (Article) intent.getSerializableExtra("selectedArticle");

        ViewPager viewPager=(ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),cursor));
    }
}