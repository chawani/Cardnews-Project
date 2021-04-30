package com.example.NewSeconds;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.NewSeconds.dto.CardViewItemDTO;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private ArrayList<CardViewItemDTO> card=new ArrayList<>();
    DBHelper dbHelper;
    SQLiteDatabase database;
    RecyclerView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardlist);

        dbHelper = new DBHelper(this,"articledb.db",null,1);
        database = dbHelper.getReadableDatabase();
        //카드 뷰 관련
        view=(RecyclerView)findViewById(R.id.cate_recyclerview);
        view.setLayoutManager(new LinearLayoutManager(this));

        String sql = "select * from article where category=";
        sql=sql+getIntent().getStringExtra("cate");

        Cursor cursor = database.rawQuery(sql,null);
        while(cursor.moveToNext()){
            card.add(new CardViewItemDTO(cursor.getString(12),cursor.getString(1)));
        }
        view.setAdapter(new MyRecyclerViewAdapter(card));
        cursor.close();
    }
}
