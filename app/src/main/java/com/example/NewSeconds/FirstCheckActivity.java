package com.example.NewSeconds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.NewSeconds.dto.Article;

import java.util.ArrayList;

public class FirstCheckActivity extends AppCompatActivity {

    Button checkbutton;
    ArrayList<ArrayList<Article>> all_article=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstcheck);

        checkbutton = findViewById(R.id.checkbtn);

        Intent listIntent=getIntent();
        all_article = (ArrayList<ArrayList<Article>>) listIntent.getSerializableExtra("all_article");

        checkbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
//                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
//                intent.putExtra("all_article",all_article);
                startActivity(intent);
            }
        });
    }
}