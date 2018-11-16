package com.example.kamill.githubtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Profile extends AppCompatActivity {
    private TextView text;
    private FirebaseAuth fire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        text = findViewById(R.id.textView2);
        fire = FirebaseAuth.getInstance();

        FirebaseUser user = fire.getCurrentUser();
        text.setText("Witaj "+user.getEmail());


    }
}
