package com.tj.thirstycatandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Called when user presses button to refresh the count
    public void reloadCountAndPicture(View view) {

        //Hit the thirstycat api to get most recent drink count
        int count = Integer.parseInt(makeGetRequest("https://www.pythonanywhere.com/drinksToday"));

        //Hit the thirstycat api to get most recent drink pic
        //type?? drinkPicture = getDrinkPicture();

        //Update the count and picture
        //updateCountAndPicture(drinkCount, drinkPicture);
    }

    private String makeGetRequest(String url) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        String response = null;

        try {
            response = client.newCall(request).execute().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }
}
