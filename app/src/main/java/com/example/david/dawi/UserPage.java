package com.example.david.dawi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class UserPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
    }

    public void onclick_Mainpage(View v){
        Intent i = new Intent(UserPage.this, MainActivity.class);
        startActivity(i);
    }
}
