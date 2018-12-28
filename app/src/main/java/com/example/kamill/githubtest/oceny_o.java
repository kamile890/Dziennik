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

    Spinner spinner;
    DatabaseReference baza;
    FirebaseAuth firebaseAuth;
    String wybrany_uczen;
    List<String> lista_ocen;
    List<String> lista_za_co;
    GridView grid;
    String klasa;

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



        //tworzenie spinnera z uczniami
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
                    lista_uczniow_UID.add(uczen.getValue());
                }
                if(lista_uczniow_UID.isEmpty()){

                }else{
                for(int i =0; i<lista_uczniow_UID.size(); i++){
                    String imie = (String)dataSnapshot.child("Users").child("Uczen").child(lista_uczniow_UID.get(i).toString()).child("imie").getValue();
                    String nazwisko = (String)dataSnapshot.child("Users").child("Uczen").child(lista_uczniow_UID.get(i).toString()).child("nazwisko").getValue();
                    lista_uczniow_nazwa.add(imie+" "+nazwisko);
                }}
                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_checked,lista_uczniow_nazwa);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        wybrany_uczen = (String)lista_uczniow_UID.get(position);
                        pobierz_liste_ocen(wybrany_uczen);
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
    public void pobierz_liste_ocen(final String uczen){
        baza.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lista_ocen = new ArrayList();
                lista_za_co = new ArrayList();
                if (uczen == null) {
                    Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
                } else {
                    klasa = (String) dataSnapshot.child("Users").child("Uczen").child(uczen).child("klasa").getValue();
                    for (DataSnapshot ocena : dataSnapshot.child("Klasy").child(klasa).child("Uczniowie").child(uczen).child("Oceny").child("Goegrafia").getChildren()) {
                        String za_co = ocena.getKey();
                        String ocenaa = (String) ocena.getValue();
                        lista_za_co.add(za_co);
                        lista_ocen.add(ocenaa);
                    }

                    GridView_Adapter_o adapter_o = new GridView_Adapter_o(lista_ocen, getContext(), lista_za_co);
                    grid.setAdapter(adapter_o);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }





}
