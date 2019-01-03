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

public class Oceny_u extends Fragment {

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

    public Oceny_u() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_oceny_u, container, false);

        spinner = v.findViewById(R.id.spinner_uczniow);
        baza = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        spinner_przedmioty = v.findViewById(R.id.spinner_przedmioty);


        //tworzenie spinnera z uczniami i przedmiotami

        stworz_spinnera_z_przedmiotami();
        grid = v.findViewById(R.id.grid_oceny);

        return v;
    }



    //tworzenie spinnera z przedmiotami
    public void stworz_spinnera_z_przedmiotami(){
        baza.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lista_przedmiotow = new ArrayList();
                String klasa = (String)dataSnapshot.child("Users").child("Uczen").child(firebaseAuth.getCurrentUser().getUid()).child("klasa").getValue();
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

                klasa = (String) dataSnapshot.child("Users").child("Uczen").child(firebaseAuth.getCurrentUser().getUid()).child("klasa").getValue();
                for (DataSnapshot ocena : dataSnapshot.child("Klasy").child(klasa).child("Uczniowie").child(firebaseAuth.getCurrentUser().getUid()).child("Oceny").child(wybrany_przedmiot).getChildren()) {
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
