package com.example.kontrolwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import java.util.Map;

public class MainActivity extends Activity {
    Button transfer;
    Button save;
    EditText entry;
    EditText entry2;
    RequestQueue newRequestQueue;
    public static String result = "no replacement";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        transfer = (Button) findViewById(R.id.transfer);
        save = (Button) findViewById(R.id.save);
        entry2 = (EditText) findViewById(R.id.entry2);
        entry = (EditText) findViewById(R.id.entry);
        newRequestQueue = Volley.newRequestQueue(this);
        save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            String url = "http://abashin.ru/cgi-bin/ru/tests/burnout";
                            //формирование запроса
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(MainActivity.this, "Запрос совершен!",Toast.LENGTH_LONG).show();
                                            result= response;

                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                                            result = error.toString();
                                        }
                                    }){

                                // отправка данных
                                @Override
                                protected Map<String, String> getParams () {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("m2", entry2.getText().toString());
                                    params.put("m1", entry.getText().toString());
                                    params.put("sex", "1");
                                    params.put("year", "1990");
                                    params.put("month", "12");
                                    params.put("day", "15");
                                    return params; }
                                //заполенение заголовков и отправка
                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String>  params = new HashMap<String, String>();
                                    Integer len = 40 + entry2.length() + entry.length();
                                    params.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
                                    params.put("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
                                    params.put("Content-Type", "application/x-www-form-urlencoded");
                                    params.put("Content-Length", len.toString());
                                    return params;
                                }
                                @Override
                                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                                    String newString = null;
                                    try { newString = new String(response.data, "UTF-8");
                                        return Response.success(newString, HttpHeaderParser.parseCacheHeaders(response));
                                    } catch (UnsupportedEncodingException e) {
                                        return Response.error(new ParseError(e));
                                    }
                                }
                            };
                            //добавление в очередь запросов
                            newRequestQueue.add(stringRequest);
                        }
                }
        );
        transfer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent("com.example.kontrolwork");
                        intent.putExtra("name", result);
                        startActivity(intent);
                    }
                }
        );
    }
}
