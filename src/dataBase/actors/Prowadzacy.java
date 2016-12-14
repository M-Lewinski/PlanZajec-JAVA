package dataBase.actors;

import dataBase.generator.SqlClassGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Prowadzacy extends Uzytkownik {
  private String login;
  private String tytul;

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getTytul() {
    return tytul;
  }

  public void setTytul(String tytul) {
    this.tytul = tytul;
  }

  @Override
  public void addObjectToBase(PreparedStatement stmt) throws SQLException {

  }

  @Override
  public PreparedStatement addObjectToBase(Connection connection) throws SQLException {
    return null;
  }

  @Override
  public void generateObject(PreparedStatement stmt, List<ArrayList<String>> data, SqlClassGenerator sqlClassGenerator, int i) throws SQLException {

  }
}
