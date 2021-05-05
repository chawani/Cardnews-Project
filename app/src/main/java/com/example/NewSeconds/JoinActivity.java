package com.example.NewSeconds;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JoinActivity extends Activity {
    private String id;
    private String pass;
    private String plus;
    AsyncTask networkTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        // 위젯에 대한 참조.
        EditText e_id = (EditText) findViewById(R.id.idInput);
        EditText e_pass=(EditText) findViewById(R.id.passwordInput);
        Button button=(Button) findViewById(R.id.submitButton);

        ImageView img=(ImageView)findViewById(R.id.loading_image);
        img.setColorFilter(Color.parseColor("#80000000"), PorterDuff.Mode.DARKEN);

        Intent intent = getIntent();
        String first_news = intent.getStringExtra("first_news");

        //Toast myToast = Toast.makeText(this.getApplicationContext(),first_news, Toast.LENGTH_SHORT);
        //myToast.show();

        e_id.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if((event.getAction()==KeyEvent.ACTION_DOWN)&&keyCode==KeyEvent.KEYCODE_ENTER){
                    e_pass.requestFocus();
                    return true;
                }
                return false;
            }
        });
        e_pass.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if((event.getAction()==KeyEvent.ACTION_DOWN)&&keyCode==KeyEvent.KEYCODE_ENTER){
                    button.performClick();
                    return true;
                }
                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id=e_id.getText().toString();
                pass=e_pass.getText().toString();
                plus="id="+id+"&pw="+pass+"&news="+first_news;

                String url = "http://ec2-54-180-133-6.ap-northeast-2.compute.amazonaws.com:5000/signin?"+plus;
                // AsyncTask를 통해 HttpURLConnection 수행.
                networkTask=new JoinActivity.NetworkTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if(networkTask!=null)
            networkTask.cancel(true);
    }

    public class NetworkTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            while(!isCancelled()) {
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
            }
            return null;
        }

        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println(result);
            if(result.equals("성공")){
                AlertDialog.Builder dlg = new AlertDialog.Builder(JoinActivity.this);
                dlg.setTitle("확인 메시지"); //제목
                dlg.setMessage("이 정보로 회원가입 하시겠습니까?"); // 메시지
//                버튼 클릭시 동작
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int whichButton){
                    }
                });
                dlg.show();
            }
            else {
                AlertDialog.Builder dlg = new AlertDialog.Builder(JoinActivity.this);
                dlg.setTitle("회원가입 실패"); //제목
                dlg.setMessage("다시 회원가입 해 주세요."); // 메시지
//                버튼 클릭시 동작
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dlg.show();
            }
        }
    }

}
