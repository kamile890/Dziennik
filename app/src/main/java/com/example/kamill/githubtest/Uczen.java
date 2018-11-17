package com.example.kamill.githubtest;

public class Uczen {

    int id;
    String login;
    String haslo;
    String imie;
    String nazwisko;
    String pesel;
    int id_rodzica;

    public Uczen() {

    }

    public Uczen(int id, String login, String haslo, String imie, String nazwisko, String pesel, int id_rodzica) {
        this.id = id;
        this.login = login;
        this.haslo = haslo;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.id_rodzica = id_rodzica;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
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

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public int getId_rodzica() {
        return id_rodzica;
    }

    public void setId_rodzica(int id_rodzica) {
        this.id_rodzica = id_rodzica;
    }
}