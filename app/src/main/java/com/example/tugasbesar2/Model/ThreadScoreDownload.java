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

public class ThreadScoreDownload extends AsyncTask<String,Integer,String> {
    private final String BASE_URL = "http://p3b.labftis.net/api.php";
    private final String api_key = "api_key";
    private MainActivity activity;

    public ThreadScoreDownload(MainActivity activity){
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(String s) {
        activity.showHighScore(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        String hasilAkhir = "";
        Uri buildURI = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(api_key,"2017730023") //Append Query
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
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();

            //Kalau Response benar
            if(response==200){
                is = conn.getInputStream();
                hasilAkhir = convertToString(is);
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
        return hasilAkhir;
    }

    public String convertToString(InputStream is){
        StringBuilder builder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            if(builder.length()==0){
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return builder.toString();
    }

}
