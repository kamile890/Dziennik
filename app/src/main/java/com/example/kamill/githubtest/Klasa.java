package com.example.kamill.githubtest;


import java.util.List;

public class Klasa {

    int id;
    String nazwa;
    List<String> lista_uczniow;

    public Klasa(){

    }

    public Klasa(int id, String nazwa, List<String> lista_uczniow) {
        this.id = id;
        this.nazwa = nazwa;
        this.lista_uczniow = lista_uczniow;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public List<String> getLista_uczniow() {
        return lista_uczniow;
    }

    public void setLista_uczniow(List<String> lista_uczniow) {
        this.lista_uczniow = lista_uczniow;
    }
}
