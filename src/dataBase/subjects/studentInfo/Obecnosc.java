package dataBase.subjects.studentInfo;

public class Obecnosc {
  private String typ;
  private String student;
  private java.sql.Date data;
  private Long id_zajecia;

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

  public Long getId_zajecia() {
    return id_zajecia;
  }

  public void setId_zajecia(Long id_zajecia) {
    this.id_zajecia = id_zajecia;
  }
}
