package com.example.kamill.githubtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public void zarzadzanie_klasami(View v){
        Intent intent = new Intent(this, Zarzadzenie_klasami.class);
        startActivity(intent);
    }

    public void zarzadzanie_nauczycielami(View v){
        Intent intent = new Intent(this, Zarzadzanie_nauczycielami.class);
        startActivity(intent);
    }





}
