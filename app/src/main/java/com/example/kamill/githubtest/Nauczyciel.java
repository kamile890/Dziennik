package com.example.kamill.githubtest;

import java.util.ArrayList;

public class Nauczyciel {


    String login;
    String imie;
    String nazwisko;
    String pensja;
    ArrayList<String> lista_przedmiotów;
    ArrayList<String> lista_klas;

   public Nauczyciel(){

   }

    public Nauczyciel(String login, String imie, String nazwisko, String pensja, ArrayList<String> lista_przedmiotów, ArrayList<String> lista_klas) {

        this.login = login;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pensja = pensja;
        this.lista_przedmiotów = lista_przedmiotów;
        this.lista_klas = lista_klas;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }


    public String getPensja() {
        return pensja;
    }

    public void setPensja(String pensja) {
        this.pensja = pensja;
    }

    public ArrayList<String> getLista_przedmiotów() {
        return lista_przedmiotów;
    }

    public void setLista_przedmiotów(ArrayList<String> lista_przedmiotów) {
        this.lista_przedmiotów = lista_przedmiotów;
    }

    public ArrayList<String> getLista_klas() {
        return lista_klas;
    }

    public void setLista_klas(ArrayList<String> lista_klas) {
        this.lista_klas = lista_klas;
    }
}
