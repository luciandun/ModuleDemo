package com.example.moduledemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.daily.baselibrary.views.CircleImageView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test1();
        final CircleImageView imageView = findViewById(R.id.imageView);
        imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.setBackgroundResource(R.drawable.btn_increase);
            }
        }, 1000);
    }

    private void test1(){
        System.out.println("this is test one method");
        System.out.println("this is master modify result");
        System.out.println("this is modified by brunch dev>>");
        Log.i("MainActivity:","test1(): packageName = "+getApplicationInfo().packageName);
        Log.i("MainActivity:","test2(): packageName = "+getApplicationContext().getPackageName());
    }

    public void test2(){

        System.out.println("this is modified by brunch dev>>");

    }
}
