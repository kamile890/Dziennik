package com.example.kamill.githubtest;

public class Nauczyciel {

    int id;
    String login;
    String imie;
    String nazwisko;
    String pensja;

   public Nauczyciel(){

   }

    public Nauczyciel(String login, String imie, String nazwisko, String pensja) {

        this.login = login;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pensja = pensja;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPensja() {
        return pensja;
    }

    public void setPensja(String pensja) {
        this.pensja = pensja;
    }
}
