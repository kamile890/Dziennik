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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class zarzadzanie_klasami_a extends Fragment {

    //zmienne
    private DatabaseReference baza;
    private EditText nazwa_nowej_klasy;
    private Button dodaj_nowa_klase_btn;
    private Spinner spinner_wyboru_klasy;
    private ArrayList lista_klas;
    private ArrayList lista_przedmiotow;
    private ArrayList lista_przedmiotow_dla_list_view;
    private ArrayList lista_uczniow;
    private ArrayList lista_uczniow_list_view;
    private RadioButton dodawanie_przedmiotu_radio_button;
    private RadioButton dodawanie_ucznia_radio_button;
    private Spinner spinner_wybieranie_przedmiotu;
    private Button dodawanie_przedmiotu_btn;
    private String przedmiot;
    private String uczen;
    private ListView lista_przemiotow_wyswietlanie;
    private String wybrana_klasa;


    public zarzadzanie_klasami_a() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_zarzadzanie_klasami_a, container, false);
        baza = FirebaseDatabase.getInstance().getReference();
        nazwa_nowej_klasy = v.findViewById(R.id.nazwa_nowej_klasy_edit_text);
        dodaj_nowa_klase_btn = v.findViewById(R.id.dodaj_klase_btn);
        spinner_wyboru_klasy = v.findViewById(R.id.wybor_klasy_spinner);
        spinner_wybieranie_przedmiotu = v.findViewById(R.id.wybor_przedmiotu_spinner);
        lista_przemiotow_wyswietlanie = v.findViewById(R.id.lista_przedmiotow_list_view);
        dodawanie_przedmiotu_btn = v.findViewById(R.id.dodaj_przedmiot_btn);


        //spinner wyboru klasy
        aktualizuj_spinnera_klas();

        //spinner wyboru przedmiotu
        baza.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lista_przedmiotow = new ArrayList<>();
                for (DataSnapshot przedmiot : dataSnapshot.child("Przedmioty").getChildren()) {
                    lista_przedmiotow.add(przedmiot.getKey());
                }
                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, lista_przedmiotow);
                spinner_wybieranie_przedmiotu.setAdapter(adapter);
                spinner_wybieranie_przedmiotu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        for (int i = 1; i <= lista_przedmiotow.size(); i++) {
                            przedmiot = (String)lista_przedmiotow.get(position);
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




        dodawanie_przedmiotu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodaj_przedmiot_do_listy();
            }
        });




        dodaj_nowa_klase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodaj_klase();
            }
        });

        return v;
    }


    //metoda dodająca nową klase do bazy
    public void dodaj_klase(){
        String nazwa_klasy = nazwa_nowej_klasy.getText().toString();
        baza.child("Klasy").child(nazwa_klasy).setValue("a");
        Toast.makeText(getContext(),"Dodano '"+nazwa_klasy+"' do bazy", Toast.LENGTH_SHORT).show();
    }

    //metoda dodająca przedmiot to listy
    public void dodaj_przedmiot_do_listy(){
        pobierz_listę_przedmiotow_dla_klasy(wybrana_klasa);
        if(!lista_przedmiotow_dla_list_view.contains(przedmiot)){
            lista_przedmiotow_dla_list_view.add(przedmiot);
            baza.child("Klasy").child(wybrana_klasa).child("Przedmioty").setValue(lista_przedmiotow_dla_list_view);
            wyświetl_liste_przedmiotow();
        }else{
            Toast.makeText(getContext(),"Przedmiot '"+przedmiot+"' znajduje się już w liście",Toast.LENGTH_SHORT).show();
        }
    }

    //metoda aktualizująca listę w spinnerze
    public void aktualizuj_spinnera_klas(){
        baza.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lista_klas = new ArrayList<>();
                for (DataSnapshot klasa : dataSnapshot.child("Klasy").getChildren()) {
                    lista_klas.add(klasa.getKey());
                }
                ArrayAdapter adapter_wyboru_klasy = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, lista_klas);
                spinner_wyboru_klasy.setAdapter(adapter_wyboru_klasy);
                spinner_wyboru_klasy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { wybrana_klasa = (String)lista_klas.get(position);
                       baza.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               lista_przedmiotow_dla_list_view = new ArrayList<>();
                               for (DataSnapshot przedmiot : dataSnapshot.child("Klasy").child(wybrana_klasa).child("Przedmioty").getChildren()) {
                                   lista_przedmiotow_dla_list_view.add(przedmiot.getValue());
                               }
                               ArrayAdapter adapterek = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item, lista_przedmiotow_dla_list_view);
                               lista_przemiotow_wyswietlanie.setAdapter(adapterek);
                               lista_przemiotow_wyswietlanie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                   @Override
                                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                   }
                               });
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError databaseError) {

                           }
                       });
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

    //metoda pobierająca listę przedmiotów dla klasy
    public void pobierz_listę_przedmiotow_dla_klasy(final String klasa_a){

        baza.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lista_przedmiotow_dla_list_view = new ArrayList<>();
                for (DataSnapshot przedmiot : dataSnapshot.child("Klasy").child(klasa_a).child("Przedmioty").getChildren()) {
                    lista_przedmiotow_dla_list_view.add(przedmiot.getValue());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //metoda wyświetlająca liste przedmiotów dla klasy
    public void wyświetl_liste_przedmiotow(){
        ArrayAdapter adapterek = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item, lista_przedmiotow_dla_list_view);
        lista_przemiotow_wyswietlanie.setAdapter(adapterek);
        lista_przemiotow_wyswietlanie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


}
