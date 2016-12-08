package dataBase.structure;

import dataBase.SqlObject;
import dataBase.generator.SqlClassGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Semestr extends SqlObject {
  private Long numer;

  public Long getNumer() {
    return numer;
  }

  public void setNumer(Long numer) {
    this.numer = numer;
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
