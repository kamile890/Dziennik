package com.example.kamill.githubtest;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dodawanie_nauczyciela extends AppCompatActivity {

    private EditText email;
    private EditText imie;
    private EditText nazwisko;
    private EditText pensja;
    private DatabaseReference Database;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodawanie_nauczyciela);

        email = findViewById(R.id.email_nauczyciela);
        imie = findViewById(R.id.imie_nauczyciela);
        nazwisko = findViewById(R.id.nazwisko_nauczyciela);
        pensja = findViewById(R.id.pensja_nauczyciela);
        Database = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();


    }





    public void dodaj_nauczyciela(View v){
        final String login_nauczyciela = email.getText().toString();
        final String imie_nauczyciela = imie.getText().toString();
        final String nazwisko_nauczyciela = nazwisko.getText().toString();
        final String pensja_nauczyciela = pensja.getText().toString();
        final String login_adm = firebaseAuth.getCurrentUser().getEmail();
        String haslo = randomString(10);

        firebaseAuth.createUserWithEmailAndPassword(login_nauczyciela,haslo)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Nauczyciel nauczyciel = new Nauczyciel(login_nauczyciela,imie_nauczyciela,nazwisko_nauczyciela,pensja_nauczyciela);
                            Database.child("Users").child("Nauczyciel").child(firebaseAuth.getCurrentUser().getUid()).setValue(nauczyciel);
                            firebaseAuth.sendPasswordResetEmail(login_nauczyciela);
                            firebaseAuth.signInWithEmailAndPassword(login_adm,"admin123");
                            Toast.makeText(Dodawanie_nauczyciela.this,"Dodano nauczyciela", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(Dodawanie_nauczyciela.this,"Nie udało się dodać użytkownika", Toast.LENGTH_LONG).show();
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
