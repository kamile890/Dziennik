package com.example.kamill.githubtest;

public class Uczen {

    String id;
    String login;
    String imie;
    String nazwisko;
    String pesel;
    String klasa;
    String login_opiekuna;

    public Uczen() {

    }

    public Uczen(String id, String login, String imie, String nazwisko, String pesel, String klasa, String login_o) {
        this.id = id;
        this.login = login;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.klasa = klasa;
        this.login_opiekuna = login_o;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLogin_opiekuna() {
        return login_opiekuna;
    }

    public void setLogin_opiekuna(String login_opiekuna) {
        this.login_opiekuna = login_opiekuna;
    }
}