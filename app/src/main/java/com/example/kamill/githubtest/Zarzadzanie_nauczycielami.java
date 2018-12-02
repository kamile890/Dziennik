package com.example.kamill.githubtest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Zarzadzanie_nauczycielami extends AppCompatActivity {

    private Spinner spinner;
    private DatabaseReference Database;
    private FirebaseAuth firebaseAuth;
    private String klasa;
    private EditText nazwa_klasy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zarzadzanie_nauczycielami);

        Database = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        spinner = findViewById(R.id.spinner1);
        nazwa_klasy = findViewById(R.id.nazwa_klasy);


        Database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<String> lista_klas = new ArrayList<>();
                for(DataSnapshot klasa: dataSnapshot.child("Klasy").getChildren()){
                    lista_klas.add(klasa.getKey());
                }

                ArrayAdapter adapter = new ArrayAdapter(Zarzadzanie_nauczycielami.this, android.R.layout.simple_spinner_item, lista_klas);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        for(int i=1; i <= lista_klas.size();i++){
                            klasa = lista_klas.get(position);
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

    public void dodaj_nauczyciela(View v){
        Intent intent = new Intent(this, Dodawanie_nauczyciela.class);
        startActivity(intent);
    }
}
