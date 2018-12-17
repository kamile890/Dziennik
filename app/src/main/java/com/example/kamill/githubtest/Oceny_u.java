package com.example.kamill.githubtest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Oceny_u extends Fragment {

    public Oceny_u() {
        // Required empty public constructor
    }

    private Spinner spinner;
    private String klasaUcznia;
    FirebaseAuth firebaseAuth;
    DatabaseReference baza;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_oceny_u, container, false);
        spinner = v.findViewById(R.id.SpinnerPrzedmiotyID);
        baza = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        baza.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                klasaUcznia = (String)dataSnapshot.child("Users").child("Uczen").child(firebaseAuth.getCurrentUser().getUid()).child("klasa").getValue();
                ArrayList listaPrzedmiotow = new ArrayList();
                for (DataSnapshot przedmiot: dataSnapshot.child("Klasy").child(klasaUcznia).child("Przedmioty").getChildren()){
                    listaPrzedmiotow.add(przedmiot.getValue());
                }

                ArrayAdapter adapter = new ArrayAdapter(getContext(),android.R.layout.simple_dropdown_item_1line, listaPrzedmiotow);
                spinner.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }



}

