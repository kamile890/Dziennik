package com.example.kamill.githubtest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class edycja_opiekuna_a extends Fragment {

    private Spinner spinner_opiekunow;
    private EditText imie;
    private EditText nazwisko;
    private EditText telefon;
    private Button zapisz_btn;
    private DatabaseReference baza;
    private ArrayList lista_opiekunow;
    private ArrayList lista_UID_opiekunow;

    public edycja_opiekuna_a() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edycja_opiekuna_a, container, false);

        spinner_opiekunow = v.findViewById(R.id.spinner_opiekunow);
        imie = v.findViewById(R.id.imie_opiekuna_EditText);
        nazwisko = v.findViewById(R.id.nazwisko_opiekuna_EditText);
        telefon = v.findViewById(R.id.telefon_opiekuna_EditText);
        zapisz_btn = v.findViewById(R.id.zapisz_btn);
        baza = FirebaseDatabase.getInstance().getReference();

        return v;
    }

    //metoda ładująca aktualną liste opiekunów do spinnera
    public void aktualizuj_liste_opiekunow(){
        baza.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                     lista_opiekunow = new ArrayList();
                     lista_UID_opiekunow = new ArrayList();
                     for (DataSnapshot opiekun: dataSnapshot.child("Users").child("Opiekun").getChildren()){
                            Opiekun opiekunn = opiekun.getValue(Opiekun.class);
                            String imie = opiekunn.imie;
                            String nazwisko = opiekunn.nazwisko;
                            String UID = opiekun.getKey();
                            lista_UID_opiekunow.add(UID);
                            lista_opiekunow.add(imie +" "+nazwisko);
                     }
                ArrayAdapter adapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,lista_opiekunow);
                spinner_opiekunow.setAdapter(adapter);
                spinner_opiekunow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String UID = (String)lista_UID_opiekunow.get(position);
                        Opiekun opiekun = dataSnapshot.child("Users").child("Opikun").child(UID).getValue(Opiekun.class);
                        String imie = opiekun.imie;
                        String nazwisko = opiekun.nazwisko;
                        String telefon = opiekun.nr_telefonu;
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







}
