package com.example.NewSeconds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.NewSeconds.dto.Article;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
//    ArrayList<ArrayList<Article>> all_article;
    String cate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent listIntent=getIntent();
//        all_article = (ArrayList<ArrayList<Article>>) listIntent.getSerializableExtra("all_article");
        
        //네비게이터 관련
        drawerLayout = findViewById(R.id.drawer_layout);

        findViewById(R.id.iv_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start에 지정된 Drawer 열기
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        //네비게이터 메뉴 관련
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent=new Intent(getApplicationContext(), ListViewActivity.class);
                String title=item.getTitle().toString();
                switch (title){
                    case "정치":
                        cate="100";
//                        intent.putExtra("article_list",all_article.get(0));
                        break;
                    case "경제":
                        cate="101";
//                        intent.putExtra("article_list",all_article.get(1));
                        break;
                    case "사회":
                        cate="102";
//                        intent.putExtra("article_list",all_article.get(2));
                        break;
                    case "생활/문화":
                        cate="103";
//                        intent.putExtra("article_list",all_article.get(3));
                        break;
                    case "세계":
                        cate="104";
//                        intent.putExtra("article_list",all_article.get(4));
                        break;
                    case "IT/과학":
                        cate="105";
 //                       intent.putExtra("article_list",all_article.get(5));
                        break;
                    default:
                        break;
                }
                intent.putExtra("cate",cate);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        //카드 뷰 관련
        RecyclerView view=(RecyclerView)findViewById(R.id.main_reclcerview);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(new MyRecyclerViewAdapter());
    }
}