package com.towatt.charge.web;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main2Activity extends AppCompatActivity {

    private String urlText="http://192.168.1.219:8080/towatt-oa/rs/android/task/tasksPersonal";
    private String sessionId="64b50cfc-beb3-477a-ab5e-6e2b7aa8189b";
    private String data="taskId=317069798866944";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getData();
    }

    private void getData() {
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected void onPreExecute() {

            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL(urlText);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(10000);

                    conn.setRequestMethod("POST");
                    conn.addRequestProperty("sessionId", sessionId);
                    conn.setDoOutput(true);

                    conn.getOutputStream().write(data.getBytes());

                    InputStream is = conn.getInputStream();
                    String s = readText(is);
                    Log.e("TAG", "s="+s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                
            }
        }.execute();
    }

    public String readText(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = -1;

            while ((len = is.read(b, 0, 1024)) != -1) {
                baos.write(b, 0, len);
            }

            is.close();

            return new String(baos.toByteArray(), "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();

            return null;
        }
    }
}
