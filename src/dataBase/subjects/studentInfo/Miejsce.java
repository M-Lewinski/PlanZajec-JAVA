package dataBase.subjects.studentInfo;

import GUI.MessageMenu.Error.ErrorField;
import dataBase.SqlObject;
import dataBase.generator.SqlClassGenerator;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Miejsce extends SqlObject {
  private int numer;
  private int  id_zajecia;
  private String student;


  public Miejsce(int numer, int id_zajecia, String student) {
    this.numer = numer;
    this.id_zajecia = id_zajecia;
    this.student = student;
  }

  public int getNumer() {
    return numer;
  }

  public void setNumer(int numer) {
    this.numer = numer;
  }

  public int getId_zajecia() {
    return id_zajecia;
  }

  public void setId_zajecia(int id_zajecia) {
    this.id_zajecia = id_zajecia;
  }

  public String getStudent() {
    return student;
  }

  public void setStudent(String student) {
    this.student = student;
  }

  @Override
  public void addObjectToBase(PreparedStatement stmt) throws SQLException {
      try {
          this.setValueSQL(stmt,1,this.id_zajecia);
          this.setValueSQL(stmt,2,this.student);
      } catch (SQLException e) {
          ErrorField.error("Failure while inserting Spot to database");
          throw e;
      }
  }

  @Override
  public void deleteObjectFromBase(PreparedStatement stmt) throws SQLException {
      try {
            this.setValueSQL(stmt,1,this.numer);
            this.setValueSQL(stmt,2,this.id_zajecia);
      } catch (SQLException e) {
          ErrorField.error("Failure while deleting Spot from database");
          throw e;
      }
  }

  @Override
  public String getInsertSQL(){
      String SQL = "INSERT INTO Miejsca(id_zajecia,student) " +
              "VALUES(?,?)";
      return SQL;
  }

  @Override
  public String getDeleteSQL() {
    String SQL = "DELETE FROM Miejsca WHERE numer = ? AND id_zajecia = ?";
    return SQL;
  }

  @Override
  public void generateObject(PreparedStatement stmt, List<ArrayList<String>> data, SqlClassGenerator sqlClassGenerator, int i) throws SQLException {

  }
}
