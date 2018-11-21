package com.example.kamill.githubtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    public void nowy_uczen(View v){
        Intent intent = new Intent(this, Dodawanie_ucznia.class);
        startActivity(intent);
    }





}
