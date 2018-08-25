package com.example.niloofar.multithreadinghandlerapi;

import android.annotation.SuppressLint;
import android.media.tv.TvContract;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.MalformedInputException;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle dd=msg.getData();
            String result=dd.getString("JsonReasult");
            TextView nameObj=findViewById(R.id.txv_name);
            TextView familyObj=findViewById(R.id.txv_family);
            TextView ageObj=findViewById(R.id.txv_age);
            try {
                JSONObject object=new JSONObject(result);
                nameObj.setText(object.getString("name"));
                familyObj.setText(object.getString("family"));
                int byear=object.getInt("BirthYear");
                int age=2018-byear;
                ageObj.setText(String.valueOf(age));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    Runnable r=new Runnable() {
        @Override
        public void run() {
            URL url=null;
            try{
                url=new URL(/* your link*/);
                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                InputStream stream=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(stream);
                String result="";
                int data;
                data=reader.read();
                while(data!=-1){
                    result+=(char)data;
                    data=reader.read();
                }
                Log.i("result", result);
                Message mm=new Message();
                Bundle dd=new Bundle();
                dd.putString("JsonReasult",result);
                mm.setData(dd);
                handler.sendMessage(mm);
            }catch (MalformedInputException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    public void btnGet(View view){
        Thread thread=new Thread(r);
        thread.start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
