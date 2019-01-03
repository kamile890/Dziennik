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
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class oceny_o extends Fragment {

    private Spinner spinner;
    private Spinner spinner_przedmioty;
    private DatabaseReference baza;
    private FirebaseAuth firebaseAuth;
    private String wybrany_uczen;
    private List<String> lista_ocen;
    private List<String> lista_za_co;
    private ArrayList lista_przedmiotow;
    private GridView grid;
    private String klasa;
    private String wybrany_przedmiot;

    public oceny_o() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_oceny_o, container, false);

        spinner = v.findViewById(R.id.spinner_uczniow);
        baza = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        spinner_przedmioty = v.findViewById(R.id.spinner_przedmioty);


        //tworzenie spinnera z uczniami i przedmiotami
        stworz_spinner_z_uczniami();

        grid = v.findViewById(R.id.grid_oceny);

        return v;
    }


    //stwórz spinner z uczniami
    public void stworz_spinner_z_uczniami(){
        baza.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList lista_uczniow_nazwa = new ArrayList();
                final ArrayList lista_uczniow_UID = new ArrayList();
                for(DataSnapshot uczen: dataSnapshot.child("Users").child("Opiekun").child(firebaseAuth.getCurrentUser().getUid()).child("lista_dzieci").getChildren()){
                    String klasa = (String)dataSnapshot.child("Users").child("Uczen").child((String)uczen.getValue()).child("klasa").getValue();
                    String imie = (String)dataSnapshot.child("Users").child("Uczen").child((String)uczen.getValue()).child("imie").getValue();
                    String nazwisko = (String)dataSnapshot.child("Users").child("Uczen").child((String)uczen.getValue()).child("nazwisko").getValue();
                    if(!klasa.equals("null")) {
                        lista_uczniow_UID.add(uczen.getValue());
                        lista_uczniow_nazwa.add(imie + " " + nazwisko);
                    }
                }

                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_checked,lista_uczniow_nazwa);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        wybrany_uczen = (String)lista_uczniow_UID.get(position);
                        stworz_spinnera_z_przedmiotami();


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

    //tworzenie spinnera z przedmiotami
    public void stworz_spinnera_z_przedmiotami(){
        baza.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lista_przedmiotow = new ArrayList();
                String klasa = (String)dataSnapshot.child("Users").child("Uczen").child(wybrany_uczen).child("klasa").getValue();
                for(DataSnapshot przedmiot: dataSnapshot.child("Klasy").child(klasa).child("Przedmioty").getChildren()){
                    lista_przedmiotow.add(przedmiot.getKey());
                }
                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_checked, lista_przedmiotow);
                spinner_przedmioty.setAdapter(adapter);
                spinner_przedmioty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        wybrany_przedmiot = (String)lista_przedmiotow.get(position);
                        pobierz_liste_ocen();
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


    //pobieranie listy ocen
    public void pobierz_liste_ocen(){
        baza.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lista_ocen = new ArrayList();
                lista_za_co = new ArrayList();

                    klasa = (String) dataSnapshot.child("Users").child("Uczen").child(wybrany_uczen).child("klasa").getValue();
                    for (DataSnapshot ocena : dataSnapshot.child("Klasy").child(klasa).child("Uczniowie").child(wybrany_uczen).child("Oceny").child(wybrany_przedmiot).getChildren()) {
                        String za_co = ocena.getKey();
                        String ocenaa = (String) ocena.getValue();
                        lista_za_co.add(za_co);
                        lista_ocen.add(ocenaa);
                    }


                    GridView_Adapter_o adapter_o = new GridView_Adapter_o(lista_ocen, getContext(), lista_za_co);
                    if(lista_za_co.isEmpty()){
                        Toast.makeText(getContext(),"Jeszcze nie wpisano żadnych ocen", Toast.LENGTH_SHORT).show();
                        grid.setAdapter(null);
                    }else {
                        grid.setAdapter(adapter_o);
                    }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }





}
