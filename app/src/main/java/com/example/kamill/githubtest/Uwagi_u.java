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


public class Uwagi_u extends Fragment {


    private Spinner spinner_uczniow;
    private Spinner spinner_przedmiotow;
    private DatabaseReference baza;
    private FirebaseAuth firebaseAuth;
    private GridView grid;
    private String wybrany_uczen;
    private String wybrany_przedmiot;



    public Uwagi_u() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_uwagi_u, container, false);

        spinner_przedmiotow = v.findViewById(R.id.spinner_przedmioty);
        baza = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        grid = v.findViewById(R.id.grid_uwagi);

        stworz_spinnera_z_przedmiotami();

        return v;
    }


    //tworzenie spinnera z przedmiotami
    public void stworz_spinnera_z_przedmiotami(){
        baza.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList lista_przedmiotow = new ArrayList();
                String klasa = (String)dataSnapshot.child("Users").child("Uczen").child(firebaseAuth.getCurrentUser().getUid()).child("klasa").getValue();
                for(DataSnapshot przedmiot: dataSnapshot.child("Klasy").child(klasa).child("Przedmioty").getChildren()){
                    lista_przedmiotow.add(przedmiot.getKey());
                }
                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_checked, lista_przedmiotow);
                spinner_przedmiotow.setAdapter(adapter);
                spinner_przedmiotow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        wybrany_przedmiot = (String)lista_przedmiotow.get(position);
                        pobierz_liste_uwag();
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

    public void pobierz_liste_uwag(){
        baza.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList lista_uwag = new ArrayList();
                ArrayList lista_za_co = new ArrayList();
                ArrayList lista_kto = new ArrayList();

                String klasa = (String) dataSnapshot.child("Users").child("Uczen").child(firebaseAuth.getCurrentUser().getUid()).child("klasa").getValue();
                for (DataSnapshot uwaga : dataSnapshot.child("Klasy").child(klasa).child("Uczniowie").child(firebaseAuth.getCurrentUser().getUid()).child("Uwagi").child(wybrany_przedmiot).getChildren()) {
                    lista_kto.add(uwaga.child("kto").getValue());
                    lista_uwag.add(uwaga.child("tresc").getValue());
                    lista_za_co.add(uwaga.child("za_co").getValue());
                }


                GridView_Adapter_Uwagi_o adapter_o = new GridView_Adapter_Uwagi_o(lista_za_co, lista_kto, getContext(), lista_uwag);
                if(lista_za_co.isEmpty()){
                    Toast.makeText(getContext(),"Jeszcze nie wpisano żadnych uwag", Toast.LENGTH_SHORT).show();
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
