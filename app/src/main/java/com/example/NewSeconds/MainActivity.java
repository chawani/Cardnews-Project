package com.example.NewSeconds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.NewSeconds.dto.Article;
import com.example.NewSeconds.dto.CardViewItemDTO;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
//    ArrayList<ArrayList<Article>> all_article;
    String cate;
    private ArrayList<CardViewItemDTO> card=new ArrayList<>();
    DBHelper dbHelper;
    SQLiteDatabase database;
    RecyclerView view;
    AsyncTask networkTask;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        card.clear();

        pref = getSharedPreferences("UserInfo", MODE_PRIVATE);
        editor = pref.edit();

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
                String title=item.getTitle().toString();
                if(title.equals("Logout")) {
                    editor.remove("id");
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                    switch (title) {
                        case "정치":
                            cate = "100";
//                        intent.putExtra("article_list",all_article.get(0));
                            break;
                        case "경제":
                            cate = "101";
//                        intent.putExtra("article_list",all_article.get(1));
                            break;
                        case "사회":
                            cate = "102";
//                        intent.putExtra("article_list",all_article.get(2));
                            break;
                        case "생활/문화":
                            cate = "103";
//                        intent.putExtra("article_list",all_article.get(3));
                            break;
                        case "세계":
                            cate = "104";
//                        intent.putExtra("article_list",all_article.get(4));
                            break;
                        case "IT/과학":
                            cate = "105";
//                       intent.putExtra("article_list",all_article.get(5));
                            break;
                        default:
                            break;
                    }
                    intent.putExtra("cate", cate);
                    intent.putExtra("title",title);
                    startActivity(intent);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        SharedPreferences pref = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String plus= pref.getString("id", "0");

        String url = "http://ec2-54-180-133-6.ap-northeast-2.compute.amazonaws.com:5000/select?id="+plus;
        // AsyncTask를 통해 HttpURLConnection 수행.
        networkTask=new MainActivity.NetworkTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);

        dbHelper = new DBHelper(this,"articledb.db",null,1);
        database = dbHelper.getReadableDatabase();
        //카드 뷰 관련
        view=(RecyclerView)findViewById(R.id.main_reclcerview);
        view.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onStop() {
        super.onStop();
        if(networkTask!=null)
            networkTask.cancel(true);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        SharedPreferences pref = getSharedPreferences("UserInfo", MODE_PRIVATE);
//        String plus= pref.getString("id", "0");
//
//        String url = "http://ec2-54-180-133-6.ap-northeast-2.compute.amazonaws.com:5000/select?id="+plus;
//        // AsyncTask를 통해 HttpURLConnection 수행.
//        networkTask=new MainActivity.NetworkTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
//    }

    public class NetworkTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            while(!isCancelled()) {
                try {
                    HttpURLConnection con = null;
                    BufferedReader reader = null;

                    try {
                        URL url = new URL(urls[0]);//url을 가져온다.
                        con = (HttpURLConnection) url.openConnection();
                        con.connect();//연결 수행

                        //입력 스트림 생성
                        InputStream stream = con.getInputStream();

                        //속도를 향상시키고 부하를 줄이기 위한 버퍼를 선언한다.
                        reader = new BufferedReader(new InputStreamReader(stream));

                        //실제 데이터를 받는곳
                        StringBuffer buffer = new StringBuffer();

                        //line별 스트링을 받기 위한 temp 변수
                        String line = "";

                        //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.
                        while ((line = reader.readLine()) != null) {
                            buffer.append(line);
                        }
                        //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
                        return buffer.toString();

                        //아래는 예외처리 부분이다.
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        //종료가 되면 disconnect메소드를 호출한다.
                        if (con != null) {
                            con.disconnect();
                        }
                        try {
                            //버퍼를 닫아준다.
                            if (reader != null) {
                                reader.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }//finally 부분
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            result=result.substring(1, result.length()-1);
            String[] result_arr=result.split(" ");
            System.out.println(result);

            Cursor cursor;
            String sql;
            String str="select * from article where count=";
            for(int i=0;i<result_arr.length;i++){
                sql=str+result_arr[i];
                System.out.println(sql);
                cursor = database.rawQuery(sql,null);
                while(cursor.moveToNext()){
                    if(cursor.getString(12).equals("NO IMAGE"))
                    {
                        card.add(new CardViewItemDTO("https://cdn.pixabay.com/photo/2019/04/29/16/11/new-4166472_1280.png",cursor.getString(1)));
                    }
                    else{card.add(new CardViewItemDTO(cursor.getString(12),cursor.getString(1)));}
                }
                cursor.close();
            }
            view.setAdapter(new MyRecyclerViewAdapter(card));
        }
    }

}