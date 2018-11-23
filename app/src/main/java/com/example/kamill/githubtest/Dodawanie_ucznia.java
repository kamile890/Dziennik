package com.example.kamill.githubtest;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Dodawanie_ucznia extends AppCompatActivity {

    private EditText email;
    private EditText imie;
    private EditText nazwisko;
    private EditText PESEL;
    private DatabaseReference Database;
    private FirebaseAuth firebaseAuth;
    private Spinner spinner;
    private String klasa_ucznia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodawanie_ucznia);

        email = findViewById(R.id.email_ucznia);
        imie = findViewById(R.id.imie_ucznia);
        nazwisko = findViewById(R.id.nazwisko_ucznia);
        PESEL = findViewById(R.id.PESEL_ucznia);
        Database = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        spinner = findViewById(R.id.spinner1);


        Database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<String> lista_klas = new ArrayList<>();
                for(DataSnapshot klasa: dataSnapshot.child("Klasy").getChildren()){
                    lista_klas.add(klasa.getKey());
                }

                ArrayAdapter adapter = new ArrayAdapter(Dodawanie_ucznia.this, android.R.layout.simple_spinner_item, lista_klas);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        for(int i=1; i <= lista_klas.size();i++){
                         klasa_ucznia = lista_klas.get(position);
                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    public void dodawanie_ucznia(View v){
        final String imie_ucznia = imie.getText().toString();
        final String nazwisko_ucznia = nazwisko.getText().toString();
        final String login_ucznia = email.getText().toString();
        final String PESEL_ucznia = PESEL.getText().toString();

        final String haslo = randomString(10);
        final String login_adm = firebaseAuth.getCurrentUser().getEmail();

        firebaseAuth.createUserWithEmailAndPassword(login_ucznia, haslo)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isSuccessful()){
                          Uczen uczen = new Uczen(firebaseAuth.getCurrentUser().getUid(),login_ucznia, imie_ucznia, nazwisko_ucznia, PESEL_ucznia, klasa_ucznia);
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
