package com.example.david.dawi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click_signin(View v){
        Intent i = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(i);
    }

    public void click_signup(View v){
        Intent i = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(i);
    }

}
