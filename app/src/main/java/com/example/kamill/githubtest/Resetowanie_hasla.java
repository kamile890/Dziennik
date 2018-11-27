package com.example.kamill.githubtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Resetowanie_hasla extends AppCompatActivity {

    private EditText email;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetowanie_hasla);

        email = findViewById(R.id.email);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void resetowanie(View v){
        String Email = email.getText().toString();
        firebaseAuth.sendPasswordResetEmail(Email);
        Toast.makeText(Resetowanie_hasla.this,"Na twoją skrzynkę pocztową wysłano maila resetujacego hasło.", Toast.LENGTH_SHORT).show();
    }
}
