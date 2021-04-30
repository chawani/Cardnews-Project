package com.example.NewSeconds;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.NewSeconds.dto.Article;
import com.example.NewSeconds.dto.CardViewItemDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FirstCheckActivity extends AppCompatActivity {

    CheckBox chk1;
    CheckBox chk2;
    CheckBox chk3;
    CheckBox chk4;
    CheckBox chk5;
    CheckBox chk6;
    CheckBox chk7;
    CheckBox chk8;
    CheckBox chk9;
    CheckBox chk10;
    List<Integer> count_list=new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstcheck);

        //gif 이미지 로딩
        ImageView imgGif = (ImageView) findViewById(R.id.gif_image);
        Glide.with(this)
                .asGif()    // GIF 로딩
                .load(R.raw.checkmark)
                .into(imgGif);


        int cnt=0;
        DBHelper dbHelper = new DBHelper(this,"articledb.db",null,1);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //Cursor cursor = database.rawQuery("select * from article",null);
        Cursor cursor = database.rawQuery("select count from article",null);
        while (cursor.moveToNext()){
            count_list.add(cursor.getInt(0));
        }
        cursor.moveToFirst();
        Random rand = new Random();

        chk1 = findViewById(R.id.checkBox1);
        chk2 = findViewById(R.id.checkBox2);
        chk3 = findViewById(R.id.checkBox3);
        chk4 = findViewById(R.id.checkBox4);
        chk5 = findViewById(R.id.checkBox5);
        chk6 = findViewById(R.id.checkBox6);
        chk7 = findViewById(R.id.checkBox7);
        chk8 = findViewById(R.id.checkBox8);
        chk9 = findViewById(R.id.checkBox9);
        chk10 = findViewById(R.id.checkBox10);
        Button submit=findViewById(R.id.checkbtn);

        String str="select * from article where count='";
        int[] num=new int[10];
        Cursor cursor2;
        String sql;
        for(int i = 0 ; i < 10 ; i++) {
            num[i]=rand.nextInt(count_list.size());
        }
        sql=str+count_list.get(num[0])+"'";
        cursor2 = database.rawQuery(sql,null);
        cursor2.moveToFirst();
        chk1.setText(cursor2.getString(1));
        sql=str+count_list.get(num[1])+"'";
        cursor2 = database.rawQuery(sql,null);
        cursor2.moveToFirst();
        chk2.setText(cursor2.getString(1));
        sql=str+count_list.get(num[2])+"'";
        cursor2 = database.rawQuery(sql,null);
        cursor2.moveToFirst();
        chk3.setText(cursor2.getString(1));
        sql=str+count_list.get(num[3])+"'";
        cursor2 = database.rawQuery(sql,null);
        cursor2.moveToFirst();
        chk4.setText(cursor2.getString(1));
        sql=str+count_list.get(num[4])+"'";
        cursor2 = database.rawQuery(sql,null);
        cursor2.moveToFirst();
        chk5.setText(cursor2.getString(1));
        sql=str+count_list.get(num[5])+"'";
        cursor2 = database.rawQuery(sql,null);
        cursor2.moveToFirst();
        chk6.setText(cursor2.getString(1));
        sql=str+count_list.get(num[6])+"'";
        cursor2 = database.rawQuery(sql,null);
        cursor2.moveToFirst();
        chk7.setText(cursor2.getString(1));
        sql=str+count_list.get(num[7])+"'";
        cursor2 = database.rawQuery(sql,null);
        cursor2.moveToFirst();
        chk8.setText(cursor2.getString(1));
        sql=str+count_list.get(num[8])+"'";
        cursor2 = database.rawQuery(sql,null);
        cursor2.moveToFirst();
        chk9.setText(cursor2.getString(1));
        sql=str+count_list.get(num[9])+"'";
        cursor2 = database.rawQuery(sql,null);
        cursor2.moveToFirst();
        chk10.setText(cursor2.getString(1));

        cursor.close();
        cursor2.close();

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String result = "";  // 결과를 출력할 문자열  ,  항상 스트링은 빈문자열로 초기화 하는 습관을 가지자
                if(chk1.isChecked() == true) result += (num[0]+",");
                if(chk2.isChecked() == true) result += (num[1]+",");
                if(chk3.isChecked() == true) result += (num[2]+",");
                if(chk4.isChecked() == true) result += (num[3]+",");
                if(chk5.isChecked() == true) result += (num[4]+",");
                if(chk6.isChecked() == true) result += (num[5]+",");
                if(chk7.isChecked() == true) result += (num[6]+",");
                if(chk8.isChecked() == true) result += (num[7]+",");
                if(chk9.isChecked() == true) result += (num[8]+",");
                if(chk10.isChecked() == true) result += (num[9]+",");
                result=result.substring(0,result.length()-1);
                System.out.println(result);
                Intent intent=new Intent(getApplicationContext(), JoinActivity.class);
                intent.putExtra("first_news",result);
                startActivity(intent);
                finish();
            }
        });
    }
}