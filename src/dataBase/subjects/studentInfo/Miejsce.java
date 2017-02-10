package dataBase.subjects.studentInfo;

import GUI.MessageMenu.Error.ErrorField;
import dataBase.MySql;
import dataBase.SqlObject;
import dataBase.generator.SqlClassGenerator;
import dataBase.subjects.Przedmiot;
import dataBase.subjects.ZaplanowaneZajecie;
import javafx.scene.paint.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Miejsce extends SqlObject {
  private int numer;
  private int  id_zajecia;
  private String student;
    public List<Obecnosc> obecnosci = new ArrayList<>();

  public Miejsce(int numer, int id_zajecia, String student) {
    this.numer = numer;
    this.id_zajecia = id_zajecia;
    this.student = student;
  }
  private String imie;
    private String nazwisko;

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

    private static Miejsce previouse;
    public static List<Miejsce> getAllObjects() throws SQLException {
        previouse = null;
        List<Miejsce> list = new ArrayList<Miejsce>();
        String SQL = "SELECT * FROM Miejsca m LEFT OUTER JOIN Obecnosci o ON m.student = o.student LEFT OUTER JOIN UzytkownicyView u ON m.student = u.login";
        Connection connect = MySql.getInstance().getConnect();
        try {
            Statement stmt = connect.createStatement();
            stmt.executeQuery(SQL);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()){
                Miejsce.setValuesFromRS(rs,list);
            }
        } catch (SQLException e){
            throw e;
        }
        return list;
    }


    public static void setValuesFromRS(ResultSet rs,List<Miejsce> list) throws SQLException {
        try {
            int numer = rs.getInt(1);
            int id = rs.getInt(2);
            String student = rs.getString(3);
            if(previouse == null || previouse.getNumer() != numer){
                Miejsce newMiejsce= new Miejsce(numer,id,student);
                list.add(newMiejsce);
                previouse = newMiejsce;
                String imie = rs.getString(9);
                String nazwisko = rs.getString(10);
                previouse.setImie(imie);
                previouse.setNazwisko(nazwisko);
            }
            String typ = rs.getString(4);
            if(typ!=null){
                Date data = rs.getDate(6);
                previouse.obecnosci.add(new Obecnosc(typ,student,data,id));
            }
        } catch (SQLException e){
            throw e;
        }
    }

    @Override
    public void delete() {
        this.obecnosci.clear();
    }

    public String getAttendance(ZaplanowaneZajecie zajecie){
        if(zajecie==null){
            return  "";
        }
        if(!this.obecnosci.isEmpty()){
            for (Obecnosc o:
                 obecnosci) {
                if(o.getData().getTime() == zajecie.getData().getTime() && o.getId_zajecia() == zajecie.getId_zajecia()){
                    return o.getTyp();
                }
            }
        }
        return "";
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

    public String getStudentName(){
        if(this.imie == null || this.nazwisko == null){
            return "";
        }
        return this.imie + " " + this.nazwisko;
    }
}
