package dataBase.actors;

import dataBase.SqlObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Uzytkownik extends SqlObject {
  private String login;
  private String imie;
  private String nazwisko;
  private String haslo;

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

  public String getHaslo() {
    return haslo;
  }

  public void setHaslo(String haslo) {
    this.haslo = haslo;
  }

  @Override
  public void addObjectToBase(PreparedStatement stmt) throws SQLException {

  }
}
