package com.example.NewSeconds;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
    private String id;
    private String pass;
    ContentValues param = new ContentValues();
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 위젯에 대한 참조.
        EditText e_id = (EditText) findViewById(R.id.loginidInput);
        EditText e_pass=(EditText) findViewById(R.id.loginpasswordInput);
        Button login_button=(Button) findViewById(R.id.loginButton);
        Button join_button=(Button) findViewById(R.id.signupButton);

        id=e_id.getText().toString();
        pass=e_pass.getText().toString();
        param.put("id",id);
        param.put("pw",pass);

        preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);

        // URL 설정.
        String url = "http://ec2-3-34-189-52.ap-northeast-2.compute.amazonaws.com:5000/signin";

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // AsyncTask를 통해 HttpURLConnection 수행.
                LoginActivity.NetworkTask networkTask = new LoginActivity.NetworkTask(url, param);
                networkTask.execute();
            }
        });
        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        ContentValues values = new ContentValues();

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어옴
            System.out.println(s);
            if(s.equals("로그인 성공")){
                //Editor를 preferences에 쓰겠다고 연결
                SharedPreferences.Editor editor = preferences.edit();
                //putString(KEY,VALUE)
                editor.putString("userid",id);
                editor.putString("userpwd",pass);
                //항상 commit & apply 를 해주어야 저장이 된다.
                editor.commit();

                //메인화면으로 넘김
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            else{

            }
        }
    }
}
