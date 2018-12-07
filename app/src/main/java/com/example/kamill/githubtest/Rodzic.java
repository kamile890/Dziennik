package com.example.kamill.githubtest;

public class Rodzic {


    String login;
    String imie;
    String nazwisko;
    String nr_telefonu;
    String login_dziecka;

   public Rodzic(){

   }

    public Rodzic(String login, String imie, String nazwisko, String nr_telefonu, String login_dziecka) {
        this.login = login;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nr_telefonu = nr_telefonu;
        this.login_dziecka = login_dziecka;
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

    public String getNr_telefonu() {
        return nr_telefonu;
    }

    public void setNr_telefonu(String nr_telefonu) {
        this.nr_telefonu = nr_telefonu;
    }
}
