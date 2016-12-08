package dataBase.subjects;

import dataBase.SqlObject;
import dataBase.generator.SqlClassGenerator;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Sala extends SqlObject {
  private String sala;
  private String budynek;
  private Long liczba_miejsc;

  public String getSala() {
    return sala;
  }

  public void setSala(String sala) {
    this.sala = sala;
  }

  public String getBudynek() {
    return budynek;
  }

  public void setBudynek(String budynek) {
    this.budynek = budynek;
  }

  public Long getLiczba_miejsc() {
    return liczba_miejsc;
  }

  public void setLiczba_miejsc(Long liczba_miejsc) {
    this.liczba_miejsc = liczba_miejsc;
  }

  @Override
  public void addObjectToBase(PreparedStatement stmt) throws SQLException {


  }

  @Override
  public void generateObject(PreparedStatement stmt, List<ArrayList<String>> data, SqlClassGenerator sqlClassGenerator) throws SQLException {

  }
}

