package com.example.kamill.githubtest;

public class Uczen {

    String id;
    String login;
    String imie;
    String nazwisko;
    String pesel;
    String login_rodzica;

    public Uczen() {

    }

    public Uczen(String id, String login, String imie, String nazwisko, String pesel, String login_rodzica) {
        this.id = id;
        this.login = login;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.login_rodzica = login_rodzica;
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

    public String getLogin_rodzica() {
        return login_rodzica;
    }

    public void setLogin_rodzica(String login_rodzica) {
        this.login_rodzica = login_rodzica;
    }
}