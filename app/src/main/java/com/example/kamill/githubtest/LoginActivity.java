package com.example.kamill.githubtest;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {
    //deklaracja zmiennych
    private Button zaloguj;
    private EditText email_field;
    private EditText password_field;
    private ProgressDialog proggres_dialog;
    private FirebaseAuth firebaseauth;
    private DatabaseReference Database;
    private Uczen uczen;
    private Nauczyciel nauczyciel;
    private Rodzic rodzic;
    private Admin admin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //inicjalizacja zmiennych
        zaloguj = findViewById(R.id.button);
        email_field = findViewById(R.id.email);
        password_field = findViewById(R.id.password);
        firebaseauth = FirebaseAuth.getInstance();
        proggres_dialog = new ProgressDialog(this);
        Database = FirebaseDatabase.getInstance().getReference();


        // ustawienie OnClickListenera dla przycisku
        zaloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogInUser();
            }
        });

        // sprawdzanie czy ktoś jest zalogowany
        if(firebaseauth.getCurrentUser() != null){
            firebaseauth.signOut();
        }

    }

    //przejście do aktywności resetowania hasła
    public void zapomnialem_hasla(View v){
        Intent i = new Intent(getApplicationContext(), Resetowanie_hasla.class);
        startActivity(i);
    }

    // przejście do aktywności Admina
    public void go_to_Admin_Activity(){
        Intent intent = new Intent(getApplicationContext(),admin_activity.class);
        startActivity(intent);
    }

    // przejście do aktywności ucznia
    public void go_to_Uczen_Activity(){
        Intent intent = new Intent(getApplicationContext(),Uczen_Activity.class);
        startActivity(intent);
    }

    // przejście do aktywności nauczyciela
    public void go_to_Nauczyciel_Activity(){
        Intent intent = new Intent(getApplicationContext(),Nauczyciel_Activity.class);
        startActivity(intent);
    }

    //przejście do aktywności rodzica
    public void go_to_Rodzic_Activity(){
        Intent intent = new Intent(getApplicationContext(),Rodzic_Activity.class);
        startActivity(intent);
    }

    // logowanie(metoda dla przycisku)
    private void LogInUser() {
        String email = email_field.getText().toString().trim();
        String password = password_field.getText().toString().trim();

        // jeśli pola sa puste, wyświetla się komunikat
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Wpisz login", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Wpisz hasło", Toast.LENGTH_SHORT).show();
        } else {
            // animacja logowania
            proggres_dialog.setMessage("Logowanie ...");
            proggres_dialog.show();

            // Authentication
            firebaseauth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            // jeśli się powiodło ...
                            if (task.isSuccessful()) {

                                // pobieranie Uid zalogowanego usera
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                final String Uid = user.getUid();

                                // przeszukanie bazy danych w celu sprawdzenia jaki status ma użytkownik
                                Database.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        uczen = dataSnapshot.child("Users").child("Uczen").child(Uid).getValue(Uczen.class);
                                        rodzic = dataSnapshot.child("Users").child("Rodzic").child(Uid).getValue(Rodzic.class);
                                        nauczyciel = dataSnapshot.child("Users").child("Nauczyciel").child(Uid).getValue(Nauczyciel.class);
                                        admin = dataSnapshot.child("Users").child("Admin").child(Uid).getValue(Admin.class);



                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                // przejście do odpowiedniego interfejsu w zależności od statusu
                                if(uczen != null){
                                    go_to_Uczen_Activity();
                                }else if (rodzic != null){
                                    go_to_Rodzic_Activity();
                                }else if(nauczyciel != null){
                                    go_to_Nauczyciel_Activity();
                                } else if(admin != null){
                                    go_to_Admin_Activity();
                                }
                                proggres_dialog.hide();


                            } else {
                                Toast.makeText(getApplicationContext(), "Dane niepoprawne", Toast.LENGTH_SHORT).show();
                                proggres_dialog.hide();

                            }
                        }
                    });
        }
    }







}

