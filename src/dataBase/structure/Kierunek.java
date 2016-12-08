package dataBase.structure;

import dataBase.SqlObject;
import dataBase.generator.SqlClassGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Kierunek extends SqlObject {
  private String nazwa;
  private String nazwa_wydzialu;


  public Kierunek(String nazwa, String nazwa_wydzialu) {
      String SQL = "INSERT INTO Kierunki " +
              "VALUES(?,?)";
      this.setInsertSQL("SQL");

    this.nazwa = nazwa;
    this.nazwa_wydzialu = nazwa_wydzialu;
  }
  public String getNazwa() {
    return nazwa;
  }

  public void setNazwa(String nazwa) {
    this.nazwa = nazwa;
  }

  public String getNazwa_wydzialu() {
    return nazwa_wydzialu;
  }

  public void setNazwa_wydzialu(String nazwa_wydzialu) {
    this.nazwa_wydzialu = nazwa_wydzialu;
  }

  @Override
  public void addObjectToBase(PreparedStatement stmt) throws SQLException {
  }

    @Override
    public PreparedStatement addObjectToBase(Connection connection) throws SQLException {
        return null;
    }

  @Override
  public void generateObject(PreparedStatement stmt, List<ArrayList<String>> data, SqlClassGenerator sqlClassGenerator) {

  }
}
