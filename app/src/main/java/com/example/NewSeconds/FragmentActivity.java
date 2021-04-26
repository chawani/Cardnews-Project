package com.example.NewSeconds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.NewSeconds.dto.Article;

public class FragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        Intent intent = getIntent();

        Article selectedArticle = (Article) intent.getSerializableExtra("selectedArticle");

        ViewPager viewPager=(ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),selectedArticle));
    }
}