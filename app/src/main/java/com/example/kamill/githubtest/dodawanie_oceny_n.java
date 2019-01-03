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
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class dodawanie_oceny_n extends Fragment {

    private DatabaseReference baza;
    private Spinner spinner_klas;
    private Spinner spinner_przedmiotow;
    private Spinner spinner_uczniow;
    private Spinner spinner_za_co;
    private FirebaseAuth firebaseAuth;
    private String wybrana_klasa;
    private String wybrany_przedmiot;
    private String wybrany_uczen;


    public dodawanie_oceny_n() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dodawanie_oceny_n, container, false);

        baza = FirebaseDatabase.getInstance().getReference();
        spinner_klas = v.findViewById(R.id.spinner_klasy);
        spinner_przedmiotow = v.findViewById(R.id.spinner_przedmioty);
        spinner_uczniow = v.findViewById(R.id.spinner_uczniowie);
        spinner_za_co = v.findViewById(R.id.spinner_zaco2);
        firebaseAuth = FirebaseAuth.getInstance();

        stworz_spinner_klas();
        stworz_spinnera_z();

        return v;
    }

    //tworzenie spinnera z klasami nauczyciela
    public void stworz_spinner_klas(){
        baza.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList lista_klas = new ArrayList();
                for(DataSnapshot klasa: dataSnapshot.child("Users").child("Nauczyciel").child(firebaseAuth.getCurrentUser().getUid()).child("lista_klas").getChildren()){
                        lista_klas.add(klasa.getValue());
                }
                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, lista_klas);
                spinner_klas.setAdapter(adapter);
                spinner_klas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        wybrana_klasa = (String)lista_klas.get(position);
                        stworz_spinner_przedmiotow();
                        stworz_spinner_uczniow();
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

    //Tworzenie spinnera z przedmiotami
    public void stworz_spinner_przedmiotow(){
        baza.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList lista_przedmiotow = new ArrayList();
                for(DataSnapshot przedmiot: dataSnapshot.child("Klasy").child(wybrana_klasa).child("Przedmioty").getChildren()){
                    lista_przedmiotow.add(przedmiot.getValue());
                }
                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, lista_przedmiotow);
                spinner_przedmiotow.setAdapter(adapter);
                spinner_przedmiotow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        wybrany_przedmiot = (String)lista_przedmiotow.get(position);
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

    public void stworz_spinner_uczniow(){
        baza.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList lista_uczniow_UID = new ArrayList();
                final ArrayList lista_uczniow = new ArrayList();
                for(DataSnapshot uczen: dataSnapshot.child("Klasy").child(wybrana_klasa).child("Uczniowie").getChildren()){
                    lista_uczniow_UID.add(uczen.getKey());
                    String imie = (String)dataSnapshot.child("Users").child("Uczen").child(uczen.getKey()).child("imie").getValue();
                    String nazwisko = (String)dataSnapshot.child("Users").child("Uczen").child(uczen.getKey()).child("nazwisko").getValue();
                    String pesel = (String)dataSnapshot.child("Users").child("Uczen").child(uczen.getKey()).child("pesel").getValue();
                    lista_uczniow.add(imie + " " + nazwisko);
                }
                ArrayAdapter adapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_activated_1, lista_uczniow);
                spinner_uczniow.setAdapter(adapter);
                spinner_uczniow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        wybrany_uczen = (String)lista_uczniow_UID.get(position);
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

    //tworzenie spinnera z (sprawdzian, kartkówka, odpowiedź)
    public void stworz_spinnera_z(){
        ArrayList lista = new ArrayList();
        lista.add("Sprawdzian");
        lista.add("Kartkówka");
        lista.add("Odpowiedź");
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, lista);
        spinner_za_co.setAdapter(adapter);
    }

}
