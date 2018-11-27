package com.example.kamill.githubtest;


import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    // deklaracja zmiennych
    private EditText email;
    private EditText imie;
    private EditText nazwisko;
    private EditText PESEL;
    private DatabaseReference Database;
    private FirebaseAuth firebaseAuth;
    private Spinner spinner;
    private String klasa_ucznia;
    private EditText login_opiekuna;
    private EditText imie_opiekuna;
    private EditText nazwisko_opiekuna;
    private EditText tel_opiekuna;
    private ProgressDialog proggres_dialog;


    // podczas dodawania do bazy wychodzi z aktywności(do poprawy)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodawanie_ucznia);
        // inicjalizacja zmiennych
        email = findViewById(R.id.email_ucznia);
        imie = findViewById(R.id.imie_ucznia);
        nazwisko = findViewById(R.id.nazwisko_ucznia);
        PESEL = findViewById(R.id.PESEL_ucznia);
        login_opiekuna = findViewById(R.id.login_opiekuna);
        imie_opiekuna = findViewById(R.id.imie_opiekuna);
        nazwisko_opiekuna = findViewById(R.id.nazwisko_opiekuna);
        tel_opiekuna = findViewById(R.id.telefon_opiekuna);
        Database = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        spinner = findViewById(R.id.spinner1);
        proggres_dialog = new ProgressDialog(this);


        // pobieranie z bazy danych listy klas i wrzucenie ich do spinnera(rozwijalne menu)
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

    // metoda tworzenia nowego ucznia i opiekuna
    public void dodawanie_ucznia(View v){
        final String imie_ucznia = imie.getText().toString();
        final String nazwisko_ucznia = nazwisko.getText().toString();
        final String login_ucznia = email.getText().toString();
        final String PESEL_ucznia = PESEL.getText().toString();
        final String haslo = randomString(10);
        final String login_op = login_opiekuna.getText().toString();
        final String imie_op = imie_opiekuna.getText().toString();
        final String nazwisko_op = nazwisko_opiekuna.getText().toString();
        final String tel_op = tel_opiekuna.getText().toString();
        final String login_adm = firebaseAuth.getCurrentUser().getEmail();

        // animacja oczekiwania
        proggres_dialog.setMessage("Dodawanie użytkowników ...");
        proggres_dialog.show();

        // rejestracja ucznia
        firebaseAuth.createUserWithEmailAndPassword(login_ucznia, haslo)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isSuccessful()){
                          // dodawanie obiektu ucznia do bazy danych
                          Uczen uczen = new Uczen(firebaseAuth.getCurrentUser().getUid(),login_ucznia, imie_ucznia, nazwisko_ucznia, PESEL_ucznia, klasa_ucznia, login_op);
                          Database.child("Users").child("Uczen").child(firebaseAuth.getCurrentUser().getUid()).setValue(uczen);
                          // wysyłanie maila resetującego hasło
                          firebaseAuth.sendPasswordResetEmail(login_ucznia);
                          // rejestracja opiekuna tego ucznia
                          firebaseAuth.createUserWithEmailAndPassword(login_op,haslo)
                                  .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                      @Override
                                      public void onComplete(@NonNull Task<AuthResult> task) {
                                          if(task.isSuccessful()){
                                              // dodawanie do bazy danych obiektu rodzica

                                              Rodzic opiekun = new Rodzic(firebaseAuth.getCurrentUser().getUid(),login_op, imie_op, nazwisko_op, tel_op);
                                              Database.child("Users").child("Opiekun").child(firebaseAuth.getCurrentUser().getUid()).setValue(opiekun);
                                              // wysyłanie maila resetującego hasło
                                              firebaseAuth.sendPasswordResetEmail(login_op);
                                              // pokazanie komunikatu, że się udało dodać
                                              Toast.makeText(Dodawanie_ucznia.this,"Dodano użytkowników", Toast.LENGTH_SHORT).show();
                                              // schowanie animacji oczekiwania
                                              proggres_dialog.hide();
                                          }
                                      }
                                  });
                      } else {
                          // schowanie animacji oczekiwania i pokazanie komunikatu, że się nie udało
                          proggres_dialog.hide();
                          Toast.makeText(Dodawanie_ucznia.this, "Nie dodano użytkowników", Toast.LENGTH_SHORT).show();
                      }
                    }

                });
        // ponowne zalogowanie admina
        firebaseAuth.signInWithEmailAndPassword(login_adm,"admin123");

    }




    // metoda generująca losowe hasło
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
