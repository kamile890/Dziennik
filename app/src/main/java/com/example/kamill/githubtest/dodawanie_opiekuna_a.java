package com.example.kamill.githubtest;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class dodawanie_opiekuna_a extends Fragment {

    private EditText login;
    private EditText imie;
    private EditText nazwisko;
    private EditText telefon;
    private EditText login_dziecka;
    private DatabaseReference baza;
    private FirebaseAuth firebaseAuth;
    private Button btn;

    public dodawanie_opiekuna_a() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_dodawanie_opiekuna_a, container, false);
        login = v.findViewById(R.id.login_opiekuna);
        btn = v.findViewById(R.id.dodaj_opiekuna_btn);
        imie = v.findViewById(R.id.imie_opiekuna);
        nazwisko = v.findViewById(R.id.nazwisko_opiekuna);
        telefon = v.findViewById(R.id.telefon_opiekuna);
        login_dziecka = v.findViewById(R.id.login_dziecka_opiekuna);
        baza = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodaj_opiekuna();
            }
        });

        return v;
    }

    public void dodaj_opiekuna(){
        final String login_opiekuna = login.getText().toString();
        final String imie_opiekuna = imie.getText().toString();
        final String nazwisko_opiekuna = nazwisko.getText().toString();
        final String telefon_opiekuna = telefon.getText().toString();
        final String login_dziecka_opiekuna = login_dziecka.getText().toString();
        String haslo_opiekuna = randomString(10);
        final String login_admina = firebaseAuth.getCurrentUser().getEmail();

        firebaseAuth.createUserWithEmailAndPassword(login_opiekuna,haslo_opiekuna)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Rodzic rodzic = new Rodzic(login_opiekuna,imie_opiekuna,nazwisko_opiekuna,telefon_opiekuna,login_dziecka_opiekuna);
                            baza.child("Users").child("Opiekun").child(firebaseAuth.getCurrentUser().getUid()).setValue(rodzic);
                            firebaseAuth.sendPasswordResetEmail(login_opiekuna);
                            firebaseAuth.signInWithEmailAndPassword(login_admina,"admin123");
                            Toast.makeText(getContext(),"Dodano opiekuna", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getContext(),"Nie udało się dodać opiekuna", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



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
