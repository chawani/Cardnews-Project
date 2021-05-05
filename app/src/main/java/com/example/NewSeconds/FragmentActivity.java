package com.example.NewSeconds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.NewSeconds.dto.Article;
import com.example.NewSeconds.dto.CardViewItemDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FragmentActivity extends AppCompatActivity {
    long start;
    long end;
    int selection_count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        start = System.currentTimeMillis();

//        Intent intent = getIntent();
        String select_sql=getIntent().getStringExtra("select_sql");
        String selection=getIntent().getStringExtra("selection");

        DBHelper dbHelper = new DBHelper(this,"articledb.db",null,1);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(select_sql,null);

        Cursor cursor2=database.rawQuery("select count from article where title='"+selection+"'",null);
        while(cursor2.moveToNext()){
            selection_count=cursor2.getInt(0);
        }

//        Article selectedArticle = (Article) intent.getSerializableExtra("selectedArticle");

        ViewPager viewPager=(ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),cursor));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        end = System.currentTimeMillis();

        long time=( end - start )/1000;
        System.out.println(time);
        if(time>20){
            SharedPreferences pref = getSharedPreferences("UserInfo", MODE_PRIVATE);
            String plus= pref.getString("id", "0")+"&newsid="+selection_count;

            String url = "http://ec2-54-180-133-6.ap-northeast-2.compute.amazonaws.com:5000/update?id="+plus;
            System.out.println(url);
            // AsyncTask를 통해 HttpURLConnection 수행.
            new FragmentActivity.NetworkTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
        }
    }


    public class NetworkTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
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
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }
                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
                    return buffer.toString();

                    //아래는 예외처리 부분이다.
                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //종료가 되면 disconnect메소드를 호출한다.
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        //버퍼를 닫아준다.
                        if(reader != null){
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }//finally 부분
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}