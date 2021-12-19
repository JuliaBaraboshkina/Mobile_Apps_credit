package com.example.kontrolwork;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    TextView resTxt;
    String renam;
    String strRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        resTxt = (TextView) findViewById(R.id.resTxt);
        Intent intent = getIntent();
        strRes = intent.getStringExtra("name");
        renam = strRes.replaceAll("\\<.*?\\>", "");
        resTxt.setText(renam.trim());


    }
}
