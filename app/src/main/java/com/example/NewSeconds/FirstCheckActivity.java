package com.example.NewSeconds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.example.NewSeconds.dto.Article;

import java.util.ArrayList;

public class FirstCheckActivity extends AppCompatActivity {

    CheckBox chk1;
    CheckBox chk2;
    CheckBox chk3;
    CheckBox chk4;
    CheckBox chk5;
    CheckBox chk6;
    ArrayList<ArrayList<Article>> all_article=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstcheck);

        chk1 = findViewById(R.id.checkBox1);
        chk2 = findViewById(R.id.checkBox2);
        chk3 = findViewById(R.id.checkBox3);
        chk4 = findViewById(R.id.checkBox4);
        chk5 = findViewById(R.id.checkBox5);
        chk6 = findViewById(R.id.checkBox6);
        Button submit=findViewById(R.id.checkbtn);

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String result = "";  // 결과를 출력할 문자열  ,  항상 스트링은 빈문자열로 초기화 하는 습관을 가지자
                if(chk1.isChecked() == true) result += "1,";
                if(chk2.isChecked() == true) result += "2,";
                if(chk3.isChecked() == true) result += "3,";
                if(chk4.isChecked() == true) result += "4,";
                if(chk5.isChecked() == true) result += "5,";
                if(chk6.isChecked() == true) result += "6,";
                result=result.substring(0,result.length()-1);
                Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("first_news",result);
                startActivity(intent);
                finish();
            }
        });
    }
}