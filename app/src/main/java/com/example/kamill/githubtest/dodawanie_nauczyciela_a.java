package com.example.kamill.githubtest;


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
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class dodawanie_nauczyciela_a extends Fragment {

    private EditText login_nauczyciela;
    private EditText imie_nauczyciela;
    private EditText nazwisko_nauczyciela;
    private EditText pensja_nauczyciela;
    private Spinner spinner_przedmioty;
    private Spinner spinner_klasy;
    private Button dodaj_nauczyciela_btn;
    private Button dodaj_przedmiot_do_listy_btn;
    private Button dodaj_klase_do_listy_btn;
    private ListView lista_przedmiotow;
    private ArrayList array_lista_przedmiotow;
    private ArrayList array_lista_klas;
    private ListView lista_klas;
    private DatabaseReference baza;
    private FirebaseAuth firebaseAuth;
    private String przedmiot;
    private String klasa;
    private ArrayList lista_przedmiotow_do_listy;
    private ArrayList lista_klas_do_listy;



    public dodawanie_nauczyciela_a() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_dodawanie_nauczyciela_a, container, false);
        login_nauczyciela = v.findViewById(R.id.login_nauczyciela);
        imie_nauczyciela = v.findViewById(R.id.imie_nauczyciela);
        nazwisko_nauczyciela = v.findViewById(R.id.nazwisko_nauczyciela);
        pensja_nauczyciela = v.findViewById(R.id.pensja_nauczyciela);
        spinner_przedmioty = v.findViewById(R.id.spinner_przedmiot);
        spinner_klasy = v.findViewById(R.id.spinner_klasa);
        baza = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        lista_przedmiotow = v.findViewById(R.id.lista_przedmiotow);
        lista_klas = v.findViewById(R.id.lista_klas);
        dodaj_przedmiot_do_listy_btn = v.findViewById(R.id.dodaj_przedmiot_do_listy_btn);
        lista_klas_do_listy = new ArrayList<>();

        //tworzenie listy przedmiotów
        baza.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 array_lista_przedmiotow = new ArrayList<String>();
                 for(DataSnapshot przedmiot: dataSnapshot.child("Przedmioty").getChildren()){
                     array_lista_przedmiotow.add(przedmiot.getKey());
                 }
                //tworzenie spinnera z listą przedmiotów
                ArrayAdapter adapter_lista_przedmiotow_spinner = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, array_lista_przedmiotow);
                spinner_przedmioty.setAdapter(adapter_lista_przedmiotow_spinner);
                spinner_przedmioty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        for (int i = 1; i <= array_lista_przedmiotow.size(); i++) {
                            przedmiot = (String) array_lista_przedmiotow.get(position);
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

        //tworzenie listy klas
        baza.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                array_lista_klas = new ArrayList<String>();
                for(DataSnapshot klasa: dataSnapshot.child("Klasy").getChildren()){
                    array_lista_klas.add(klasa.getKey());
                }
                //tworzenie spinnera z listą przedmiotów
                ArrayAdapter adapter_lista_klas_spinner = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, array_lista_klas);
                spinner_klasy.setAdapter(adapter_lista_klas_spinner);
                spinner_klasy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        for (int i = 1; i <= array_lista_klas.size(); i++) {
                            klasa = (String) array_lista_klas.get(position);
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

        dodaj_przedmiot_do_listy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodaj_przedmiot_do_listy();
            }
        });




        return v;
    }

    //metoda tworząca nowego nauczyciela
    public void dodaj_nauczyciela(){

    }

    //metoda dodająca przedmioty do listy
    public void dodaj_przedmiot_do_listy(){

        lista_klas_do_listy.add(przedmiot);
        ArrayAdapter adapter_listy_przedmiotow = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, lista_klas_do_listy);
        lista_przedmiotow.setAdapter(adapter_listy_przedmiotow);



    }

    //metoda dodająca klase do listy
    public void dodaj_klase_do_listy(){

    }

}
