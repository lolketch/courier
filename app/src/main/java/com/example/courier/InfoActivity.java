package com.example.courier;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {

    WebView webView;
    String tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        webView = findViewById(R.id.webView);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String description = intent.getStringExtra("description");

        String in_address = intent.getStringExtra("in_address");
        String out_address = intent.getStringExtra("out_address");
        String cost = intent.getStringExtra("cost");
        tmp = " id: "+id + "<br><br>" + " Описание: " +description + "<br><br>"+" Куда: "+in_address + "<br><br>"+" Откуда: "+out_address + "<br><br>"+" Стоимость заказа: "+cost + "<br><br>";

        webView.loadData(tmp,"text/html; charset=UTF-8", null);


    }
}
