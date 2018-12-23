package com.example.kamill.githubtest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class wyswietlenie_uczonych_przedmiotow_n extends Fragment {

    private ListView lista_przedmiotow;
    private DatabaseReference baza;

    public wyswietlenie_uczonych_przedmiotow_n() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_wyswietlenie_uczonych_przedmiotow_n, container, false);

        lista_przedmiotow = v.findViewById(R.id.lista_przedmiotow);
        baza = FirebaseDatabase.getInstance().getReference();


        lista_przedmiotow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String przedmiot = ((TextView)view).getText().toString();


            }
        });




        return v;
    }
}
