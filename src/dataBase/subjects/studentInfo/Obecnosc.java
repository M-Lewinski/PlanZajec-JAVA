package dataBase.subjects.studentInfo;

import dataBase.SqlObject;
import dataBase.generator.SqlClassGenerator;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Obecnosc extends SqlObject{
  private String typ;
  private String student;
  private java.sql.Date data;
  private int id_zajecia;

    public Obecnosc(String typ, String student, Date data, int id_zajecia) {
        this.typ = typ;
        this.student = student;
        this.data = data;
        this.id_zajecia = id_zajecia;
    }

    public String getTyp() {
    return typ;
  }

  public void setTyp(String typ) {
    this.typ = typ;
  }

  public String getStudent() {
    return student;
  }

  public void setStudent(String student) {
    this.student = student;
  }

  public java.sql.Date getData() {
    return data;
  }

  public void setData(java.sql.Date data) {
    this.data = data;
  }

  public int getId_zajecia() {
    return id_zajecia;
  }

  public void setId_zajecia(int id_zajecia) {
    this.id_zajecia = id_zajecia;
  }

  @Override
  public void addObjectToBase(PreparedStatement stmt) throws SQLException {

  }

  @Override
  public void deleteObjectFromBase(PreparedStatement stmt) throws SQLException {

  }

  @Override
  public String getInsertSQL() {
    return null;
  }

  @Override
  public String getDeleteSQL() {
    return null;
  }

  @Override
  public void generateObject(PreparedStatement stmt, List<ArrayList<String>> data, SqlClassGenerator sqlClassGenerator, int i) throws SQLException {

  }
}
