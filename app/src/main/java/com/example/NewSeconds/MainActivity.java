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
    ArrayList<ArrayList<Article>> all_article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent listIntent=getIntent();
        all_article = (ArrayList<ArrayList<Article>>) listIntent.getSerializableExtra("all_article");
        
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
                        intent.putExtra("article_list",all_article.get(0));
                        break;
                    case "경제":
                        intent.putExtra("article_list",all_article.get(1));
                        break;
                    case "사회":
                        intent.putExtra("article_list",all_article.get(2));
                        break;
                    case "생활/문화":
                        intent.putExtra("article_list",all_article.get(3));
                        break;
                    case "세계":
                        intent.putExtra("article_list",all_article.get(4));
                        break;
                    case "IT/과학":
                        intent.putExtra("article_list",all_article.get(5));
                        break;
                    default:
                        break;
                }
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

//        new MainActivity.JSONTask().execute("http://ec2-3-34-189-52.ap-northeast-2.compute.amazonaws.com:3000/???");
        //카드 뷰 관련
        RecyclerView view=(RecyclerView)findViewById(R.id.main_reclcerview);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(new MyRecyclerViewAdapter());
    }

//    public class JSONTask extends AsyncTask<String, String, String> {
//
//        @Override
//        protected String doInBackground(String... urls) {
//            try {
//                HttpURLConnection con = null;
//                BufferedReader reader = null;
//
//                try{
//                    //URL url = new URL("http://192.168.25.16:3000/users");
//                    URL url = new URL(urls[0]);//url을 가져온다.
//                    con = (HttpURLConnection) url.openConnection();
//                    con.connect();//연결 수행
//
//                    //입력 스트림 생성
//                    InputStream stream = con.getInputStream();
//
//                    //속도를 향상시키고 부하를 줄이기 위한 버퍼를 선언한다.
//                    reader = new BufferedReader(new InputStreamReader(stream));
//
//                    //실제 데이터를 받는곳
//                    StringBuffer buffer = new StringBuffer();
//
//                    //line별 스트링을 받기 위한 temp 변수
//                    String line = "";
//
//                    //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.
//                    while((line = reader.readLine()) != null){
//                        buffer.append(line);
//                    }
//                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
//                    return buffer.toString();
//
//                    //아래는 예외처리 부분이다.
//                } catch (MalformedURLException e){
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    //종료가 되면 disconnect메소드를 호출한다.
//                    if(con != null){
//                        con.disconnect();
//                    }
//                    try {
//                        //버퍼를 닫아준다.
//                        if(reader != null){
//                            reader.close();
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }//finally 부분
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            //System.out.println(result);
//            JSONParse(result);
//        }
//
//        public void JSONParse(String jsonStr){
//
//        }
//    }
}