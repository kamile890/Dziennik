package com.example.kamill.githubtest;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class dodawanie_ucznia_a extends Fragment {

    private DatabaseReference baza;
    private FirebaseAuth firebaseAuth;
    private Button btn;
    private EditText login;
    private EditText imie;
    private EditText nazwisko;
    private EditText pesel;
    private EditText klasa;



    public dodawanie_ucznia_a() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        baza = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        View v = inflater.inflate(R.layout.fragment_dodawanie_ucznia_a, container, false);
        login = v.findViewById(R.id.login_ucznia);
        imie = v.findViewById(R.id.imie_ucznia);
        nazwisko = v.findViewById(R.id.nazwisko_ucznia);
        pesel = v.findViewById(R.id.pesel_ucznia);
        btn = v.findViewById(R.id.dodaj_ucznia_btn);
        // onClickListener dla przycisku
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodaj_ucznia_a();
            }
        });

        return v;
        }

        public void dodaj_ucznia_a(){
        final String login_ucznia = login.getText().toString();
        final String imie_ucznia = imie.getText().toString();
        final String nazwisko_ucznia = nazwisko.getText().toString();
        final String pesel_ucznia = pesel.getText().toString();
        String haslo = randomString(10);
        final String login_admina = firebaseAuth.getCurrentUser().getEmail();

        firebaseAuth.createUserWithEmailAndPassword(login_ucznia,haslo)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Uczen uczen = new Uczen(login_ucznia,imie_ucznia,nazwisko_ucznia,pesel_ucznia,"Klasa 1");
                            baza.child("Users").child("Uczen").child(firebaseAuth.getCurrentUser().getUid()).setValue(uczen);
                            firebaseAuth.sendPasswordResetEmail(login_ucznia);
                            firebaseAuth.signInWithEmailAndPassword(login_admina,"admin123");
                            Toast.makeText(getContext(),"Dodano ucznia do bazy", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(),"Nie udało się dodać do bazy", Toast.LENGTH_SHORT).show();
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
