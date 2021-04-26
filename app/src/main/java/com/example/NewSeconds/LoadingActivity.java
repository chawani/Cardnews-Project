package com.example.NewSeconds;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.example.NewSeconds.dto.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class LoadingActivity extends Activity {

    ArrayList<ArrayList<Article>> all_article=new ArrayList<>();
    ArrayList<Article> article_list100 = new ArrayList<Article>();
    ArrayList<Article> article_list101 = new ArrayList<Article>();
    ArrayList<Article> article_list102 = new ArrayList<Article>();
    ArrayList<Article> article_list103 = new ArrayList<Article>();
    ArrayList<Article> article_list104 = new ArrayList<Article>();
    ArrayList<Article> article_list105 = new ArrayList<Article>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new LoadingActivity.JSONTask().execute("http://ec2-3-34-189-52.ap-northeast-2.compute.amazonaws.com:3000/users");
        startLoading();

    }

    private void startLoading(){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(LoadingActivity.this,FirstCheckActivity.class);
//                Intent intent=new Intent(LoadingActivity.this,MainActivity.class);
                intent.putExtra("all_article",all_article);
                startActivity(intent);
                finish();
            }
        },2000);
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    //URL url = new URL("http://192.168.25.16:3000/users");
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
            //System.out.println(result);
            result.replaceAll("&", "&amp;")
                    .replaceAll("<", "&lt;")
                    .replaceAll(">", "&gt;")
                    .replaceAll("((?<!\\\\)(\\\\\\\\)*)(\\\\\\\")", "$1&quot;")
                    .replaceAll("'", "&#x27;")
                    .replaceAll("/", "&#x2F;");
            JSONParse(result);
        }

        public void JSONParse(String jsonStr){
            StringBuilder stringBuilder = new StringBuilder();
            try {
                JSONArray jsonArray = new JSONArray(jsonStr);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String title = jsonObject.getString("TITLE");
                    String content1 = jsonObject.getString("CONTENT1");
                    String content2 = jsonObject.getString("CONTENT2");
                    String content3 = jsonObject.getString("CONTENT3");
                    String content4 = jsonObject.getString("CONTENT4");
                    String content5 = jsonObject.getString("CONTENT5");
                    String content6 = jsonObject.getString("CONTENT6");
                    String content7 = jsonObject.getString("CONTENT7");
                    String date = jsonObject.getString("DATE");
                    int category = jsonObject.getInt("CATEGORY");
                    int count = jsonObject.getInt("COUNT");
                    String image = jsonObject.getString("IMAGE");
                    Article article=new Article(title,content1,content2,content3,content4,content5,content6,content7,date,category,count,image);
                    switch (category){
                        case 100:
                            article_list100.add(article);
                            break;
                        case 101:
                            article_list101.add(article);
                            break;
                        case 102:
                            article_list102.add(article);
                            break;
                        case 103:
                            article_list103.add(article);
                            break;
                        case 104:
                            article_list104.add(article);
                            break;
                        case 105:
                            article_list105.add(article);
                            break;
                        default:
                            break;
                    }
                }
            } catch (JSONException e) { e.printStackTrace(); }
            all_article.add(article_list100);
            all_article.add(article_list101);
            all_article.add(article_list102);
            all_article.add(article_list103);
            all_article.add(article_list104);
            all_article.add(article_list105);
        }
    }
}
