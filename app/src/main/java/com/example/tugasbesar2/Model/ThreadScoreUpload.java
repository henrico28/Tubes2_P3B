package com.example.tugasbesar2.Model;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.tugasbesar2.View.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ThreadScoreUpload extends AsyncTask<String,Integer,String> {
    final String BASE_URL = "http://p3b.labftis.net/api.php";
    final String api_key = "api_key";
    final String order = "order";
    final String value = "value";
    private MainActivity activity;
    private String urutanKe;
    private String isi;

    public ThreadScoreUpload(MainActivity activity, String urutanKe, String isi){
        this.activity = activity;
        this.urutanKe = urutanKe;
        this.isi = isi;
    }

    @Override
    protected String doInBackground(String... strings) {
        String hasil = "";
        Uri buildURI = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(api_key,"2017730023") //Append api_key
                .appendQueryParameter(order,this.urutanKe)
                .appendQueryParameter(isi,this.isi)
                .build();
        URL requestURL =null;
        HttpURLConnection conn = null;
        InputStream is = null;
        try{
            requestURL = new URL(buildURI.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }

        try{
            conn = (HttpURLConnection)requestURL.openConnection();
            conn.setReadTimeout(10000);//ReadTimeout
            conn.setConnectTimeout(15000);//ConnectionTimeout
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();

            //Kalau Response benar
            if(response==200){
                is = conn.getInputStream();
                hasil = "SUCCESS";
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                conn.disconnect();
                if(is!=null){
                    is.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return hasil;
    }

    @Override
    protected void onPostExecute(String s) {
        activity.scoreUploaded(s);
    }
}
