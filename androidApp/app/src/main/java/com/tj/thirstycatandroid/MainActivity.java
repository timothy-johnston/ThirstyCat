package com.tj.thirstycatandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadNewData();
    }


    final OkHttpClient client = new OkHttpClient();
    final Request request = new Request.Builder()
            .url("http://tewardj11.pythonanywhere.com/api/drinks/drinkstoday")
            .build();

    public void loadNewData () {
        new Thread(new Runnable() {

            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String responseString = "hi";
                try {
                    responseString = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final String responseBodyString = responseString;
                //int responseInt = Integer.parseInt(responseString);
                final TextView countText;
                countText = (TextView) findViewById(R.id.countText);



                runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       countText.setText("Drinks today: " + responseBodyString);
                   }
                });


            }
        }).start();
    }

}
