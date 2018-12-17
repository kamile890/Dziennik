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
    private EditText email_field;
    private EditText password_field;
    private ProgressDialog proggres_dialog;
    private FirebaseAuth firebaseauth;
    private DatabaseReference Database;
    private Uczen uczen;
    private Nauczyciel nauczyciel;
    private Opiekun rodzic;
    private Admin admin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //inicjalizacja zmiennych
        email_field = findViewById(R.id.email);
        password_field = findViewById(R.id.password);
        firebaseauth = FirebaseAuth.getInstance();
        proggres_dialog = new ProgressDialog(this);
        Database = FirebaseDatabase.getInstance().getReference();




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
        finish();
        startActivity(intent);
    }

    public  void  go_to_Nauczyciel_Activity(){
        Intent i = new Intent(getApplicationContext(),Nauczyciel_Activity.class);
        startActivity(i);
    }

    public  void  go_to_Uczen_Activity(){
        Intent i = new Intent(getApplicationContext(),Uczen_activity.class);
        startActivity(i);
    }



    // logowanie(metoda dla przycisku)
    public void LogInUser(View v) {
        String email = email_field.getText().toString().trim();
        String password = password_field.getText().toString().trim();

        // jeśli pola sa puste, wyświetla się komunikat
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Wpisz login", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Wpisz hasło", Toast.LENGTH_SHORT).show();
        }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(),"Nieprawidłowy format adresu e-mail", Toast.LENGTH_SHORT).show();
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
                                Database.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        uczen = dataSnapshot.child("Users").child("Uczen").child(Uid).getValue(Uczen.class);
                                        rodzic = dataSnapshot.child("Users").child("Rodzic").child(Uid).getValue(Opiekun.class);
                                        nauczyciel = dataSnapshot.child("Users").child("Nauczyciel").child(Uid).getValue(Nauczyciel.class);
                                        admin = dataSnapshot.child("Users").child("Admin").child(Uid).getValue(Admin.class);



                                        // przejście do odpowiedniego interfejsu w zależności od statusu

                                       if(admin != null){
                                           proggres_dialog.dismiss();
                                            go_to_Admin_Activity();
                                       }else if(nauczyciel != null){
                                           proggres_dialog.dismiss();
                                           go_to_Nauczyciel_Activity();
                                       }else if(uczen != null){
                                           proggres_dialog.dismiss();
                                           go_to_Uczen_Activity();
                                       }



                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(getApplicationContext(),"Błąd z bazą danych", Toast.LENGTH_SHORT).show();
                                    }
                                });




                            } else {
                                Toast.makeText(getApplicationContext(), "Dane niepoprawne", Toast.LENGTH_SHORT).show();
                                proggres_dialog.hide();

                            }



                        }
                    });
        }
    }







}

