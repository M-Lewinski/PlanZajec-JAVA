package dataBase.actors;

import dataBase.generator.SqlClassGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Student extends Uzytkownik {
  private String login;
  private String kierunek;
  private Long semestr;
  private Long grupa;
  private Long indeks;

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getKierunek() {
    return kierunek;
  }

  public void setKierunek(String kierunek) {
    this.kierunek = kierunek;
  }

  public Long getSemestr() {
    return semestr;
  }

  public void setSemestr(Long semestr) {
    this.semestr = semestr;
  }

  public Long getGrupa() {
    return grupa;
  }

  public void setGrupa(Long grupa) {
    this.grupa = grupa;
  }

  public Long getIndeks() {
    return indeks;
  }

  public void setIndeks(Long indeks) {
    this.indeks = indeks;
  }

  @Override
  public void addObjectToBase(PreparedStatement stmt) throws SQLException {

  }

  @Override
  public void generateObject(PreparedStatement stmt, List<ArrayList<String>> data, SqlClassGenerator sqlClassGenerator, int i) throws SQLException {

  }
}
