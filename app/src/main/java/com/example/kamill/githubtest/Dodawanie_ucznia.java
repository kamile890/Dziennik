package com.example.kamill.githubtest;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Dodawanie_ucznia extends AppCompatActivity {

    private EditText imie;
    private EditText nazwisko;
    private EditText login;
    private EditText PESEL;
    private EditText login_rodzica;
    private DatabaseReference Database;
    private FirebaseAuth firebaseAuth;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodawanie_ucznia);
        imie = findViewById(R.id.imie_nowego_ucznia);
        nazwisko = findViewById(R.id.nazwisko_nowego_ucznia);
        login = findViewById(R.id.login_nowego_ucznia);
        PESEL = findViewById(R.id.PESEL_nowego_ucznia);
        login_rodzica = findViewById(R.id.login_rodzica_ucznia);
        Database = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();






    }

    public void dodawanie_ucznia(View v){
        final String imie_ucznia = imie.getText().toString();
        final String nazwisko_ucznia = nazwisko.getText().toString();
        final String login_ucznia = login.getText().toString();
        final String PESEL_ucznia = PESEL.getText().toString();
        final String login_rodzica_ucznia = login_rodzica.getText().toString();
        final String haslo = randomString(10);
        final String login_adm = firebaseAuth.getCurrentUser().getEmail();


        firebaseAuth.createUserWithEmailAndPassword(login_ucznia, haslo)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isSuccessful()){
                          Uczen uczen = new Uczen(firebaseAuth.getCurrentUser().getUid(),login_ucznia, imie_ucznia, nazwisko_ucznia, PESEL_ucznia, login_rodzica_ucznia);
                          Database.child("Users").child("Uczen").child(firebaseAuth.getCurrentUser().getUid()).setValue(uczen);
                            firebaseAuth.sendPasswordResetEmail(login_ucznia);
                          firebaseAuth.signInWithEmailAndPassword(login_adm,"admin123");


                          Toast.makeText(Dodawanie_ucznia.this,"Dodano użytkownika", Toast.LENGTH_LONG).show();
                      }  else{
                          Toast.makeText(Dodawanie_ucznia.this,"Nie udało się", Toast.LENGTH_LONG).show();
                      }
                    }
                });


    }



    public String randomString(int len)
    {
        char[] str = new char[100];

        for (int i = 0; i < len; i++)
        {
            str[i] = (char) (((int)(Math.random() * 26)) + (int)'A');
        }

        return (new String(str, 0, len));
    }





}
