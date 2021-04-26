package com.example.NewSeconds;

import android.app.Activity;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;

public class JoinActivity extends Activity {
    private String id;
    private String pass;
    ContentValues param = new ContentValues();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        // 위젯에 대한 참조.
        EditText e_id = (EditText) findViewById(R.id.idInput);
        EditText e_pass=(EditText) findViewById(R.id.passwordInput);
        id=e_id.getText().toString();
        pass=e_pass.getText().toString();
        param.put("id",id);
        param.put("pw",pass);
        //param.put("news",id);

        // URL 설정.
        String url = "http://ec2-3-34-189-52.ap-northeast-2.compute.amazonaws.com:5000/signin";

        // AsyncTask를 통해 HttpURLConnection 수행.
        NetworkTask networkTask = new NetworkTask(url, param);
        networkTask.execute();
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
            if(s.equals("성공")){
                //로그인 화면으로 넘김
            }
        }
    }
}
