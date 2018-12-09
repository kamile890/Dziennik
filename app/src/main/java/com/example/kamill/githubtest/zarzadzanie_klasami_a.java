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
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private ListView lista_przemiotow_ListView;
    private String wybrana_klasa;
    private TextView lista_przedmiotow_dla;


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
        lista_przemiotow_ListView = v.findViewById(R.id.lista_przedmiotow_list_view);
        dodawanie_przedmiotu_btn = v.findViewById(R.id.dodaj_przedmiot_btn);
        dodawanie_przedmiotu_radio_button = v.findViewById(R.id.dodaj_przedmiot_radio_button);
        lista_przedmiotow_dla = v.findViewById(R.id.lista_przedmiotow_dla);

        //radiobutton "dodawanie przedmiotu" zanaczony jako domyślny
        dodawanie_przedmiotu_radio_button.setChecked(true);

        // sprawdzanie, który radiobutton jest wciśnięty


        //spinner wyboru przedmiotu
        stworz_spinnera_z_przedmiotami();

        //spinner wyboru klasy
        aktualizacja_spinnera_z_klasami();


        //onClickListener dodawanie przedmiotu do listy
        dodawanie_przedmiotu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodaj_przedmiot_do_listy();
            }
        });



        // onClickListener dodawanie nowej klasy
        dodaj_nowa_klase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodaj_klase();
            }
        });

        return v;
    }


    //metody-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //metoda dodająca nową klase do bazy
    public void dodaj_klase(){
        String nazwa_klasy = nazwa_nowej_klasy.getText().toString();
        if(!lista_klas.contains(nazwa_klasy)) {
            baza.child("Klasy").child(nazwa_klasy).setValue("a");
            aktualizacja_spinnera_z_klasami();
            Toast.makeText(getContext(), "Dodano '" + nazwa_klasy + "' do bazy", Toast.LENGTH_SHORT).show();
          }else{
           Toast.makeText(getContext(),"'"+nazwa_klasy+"' już istnieje", Toast.LENGTH_SHORT).show();
        }
    }
//----------------------------------------------------------------
    //metoda dodająca wszystkie przedmioty do spinnera z przedmiotami
    public void stworz_spinnera_z_przedmiotami(){
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
    }
//----------------------------------------------------------------
    //metoda dodająca nowy przedmiot dla klasy
    public void dodaj_przedmiot_do_listy(){

        if(!lista_przedmiotow_dla_list_view.contains(przedmiot)){
            baza.child("Klasy").child(wybrana_klasa).child("Przedmioty").child(przedmiot).setValue(przedmiot);
            wyświetl_liste_przedmiotow_w_List_View(wybrana_klasa);
            Toast.makeText(getContext(),"Dodano '"+przedmiot+"' do listy",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(),"Przedmiot '"+przedmiot+"' znajduje się już w liście",Toast.LENGTH_SHORT).show();
        }
    }
//----------------------------------------------------------------
    //metoda pobierająca z bazy i wyświetlająca listę przedmiotów w ListView dla danej klasy
    public void wyświetl_liste_przedmiotow_w_List_View(final String klasa){
        baza.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lista_przedmiotow_dla_list_view = new ArrayList<>();
                for(DataSnapshot przedmiot: dataSnapshot.child("Klasy").child(klasa).child("Przedmioty").getChildren()){
                    lista_przedmiotow_dla_list_view.add(przedmiot.getValue());
                }
                ArrayAdapter adapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,lista_przedmiotow_dla_list_view);
                lista_przemiotow_ListView.setAdapter(adapter);
                lista_przemiotow_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        final String przedmiot = ((TextView)view).getText().toString();
                        final AlertDialog.Builder alert_dialog = new AlertDialog.Builder(getContext());
                        alert_dialog.setMessage("Czy na pewno chcesz usunąć '"+przedmiot+"' z '"+wybrana_klasa+"' ?")
                                .setCancelable(false)
                                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        usun_przedmiot(przedmiot,wybrana_klasa);
                                        Toast.makeText(getContext(),"Usunięto '"+przedmiot+"' z '"+wybrana_klasa+"'", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = alert_dialog.create();
                        alert.setTitle("Usuń");
                        alert.show();

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //---------------------------------------------------------------------------
    //metoda tworząca spinnera z wszystkimi klasami
        public void aktualizacja_spinnera_z_klasami() {
            baza.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    lista_klas = new ArrayList<>();
                    for (DataSnapshot przedmiot : dataSnapshot.child("Klasy").getChildren()) {
                        lista_klas.add(przedmiot.getKey());
                    }
                    ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, lista_klas);
                    spinner_wyboru_klasy.setAdapter(adapter);
                    spinner_wyboru_klasy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            for (int i = 1; i <= lista_klas.size(); i++) {
                                wybrana_klasa = (String) lista_klas.get(position);
                            }
                            wyświetl_liste_przedmiotow_w_List_View(wybrana_klasa);
                            lista_przedmiotow_dla.setText("Lista przedmiotów dla: "+wybrana_klasa);
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


        //----------------------------------------------------------------------------
            //metoda usuwająca wybrany przedmiot z bazy wybranej klasy
    public void usun_przedmiot(String przedmiot, String klasa){
        lista_przedmiotow_dla_list_view.remove(przedmiot);
        baza.child("Klasy").child(klasa).child("Przedmioty").setValue(lista_przedmiotow_dla_list_view);
        wyświetl_liste_przedmiotow_w_List_View(wybrana_klasa);
    }








}
