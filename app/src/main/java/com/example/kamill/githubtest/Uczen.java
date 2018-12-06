package com.example.kamill.githubtest;

public class Uczen {


    String login;
    String imie;
    String nazwisko;
    String pesel;
    String klasa;


    public Uczen() {

    }

    public Uczen(String login, String imie, String nazwisko, String pesel, String klasa) {

        this.login = login;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.klasa = klasa;

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

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getKlasa() {
        return klasa;
    }

    public void setKlasa(String klasa) {
        this.klasa = klasa;
    }

}