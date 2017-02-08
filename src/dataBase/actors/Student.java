package dataBase.actors;

import GUI.MessageMenu.Error.ErrorField;
import dataBase.MySql;
import dataBase.generator.SqlClassGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Student extends Uzytkownik {
    private String login;
    private String kierunek;
    private String wydzial;
    private int semestr;
    private int grupa;
    private int indeks;

    public Student(){};

    public Student(String login, String imie, String nazwisko, String haslo, String kierunek,String wydzial, int semestr, int grupa) {
        super(login, imie, nazwisko, haslo);
        this.login = login;
        this.kierunek = kierunek;
        this.semestr = semestr;
        this.grupa = grupa;
        this.wydzial = wydzial;
    }

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

    public int getSemestr() {
        return semestr;
    }

    public void setSemestr(int semestr) {
        this.semestr = semestr;
    }

    public int getGrupa() {
        return grupa;
    }

    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }

    public int getIndeks() {
        return indeks;
    }

    public void setIndeks(int indeks) {
        this.indeks = indeks;
    }

    public String getWydzial() {
        return wydzial;
    }

    @Override
    public void addObjectToBase(PreparedStatement stmt) throws SQLException {
        try {
            this.setValueSQL(stmt, 1, this.login);
            this.setValueSQL(stmt, 2, this.kierunek);
            this.setValueSQL(stmt, 3, this.wydzial);
            this.setValueSQL(stmt, 4, this.semestr);
            this.setValueSQL(stmt, 5, this.grupa);
            Connection connect = MySql.getInstance().getConnect();
            PreparedStatement stmt2 = connect.prepareStatement(super.getInsertSQL());
            super.addObjectToBase(stmt2);
            stmt2.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void generateObject(PreparedStatement stmt, List<ArrayList<String>> data, SqlClassGenerator sqlClassGenerator, int i) throws SQLException {
        try {
            sqlClassGenerator.generate(this, data, stmt, i);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public String getInsertSQL() {
        String SQL = "INSERT INTO Studenci (login,kierunek,wydzial,semestr,grupa) " +
                "VALUES(?,?,?,?,?)";
        return SQL;
    }

    @Override
    public void deleteObjectFromBase(PreparedStatement stmt) throws SQLException {
        try {
            this.setValueSQL(stmt, 1, this.login);
            Connection connect = MySql.getInstance().getConnect();
            PreparedStatement stmt2 = connect.prepareStatement(super.getDeleteSQL());
            super.deleteObjectFromBase(stmt2);
            stmt2.executeUpdate();
        } catch (SQLException e) {
            ErrorField.error("Failure while deleting Student from Database!");
            throw e;
        }
    }

    @Override
    public String getDeleteSQL() {
        String SQL = "DELETE FROM Studenci WHERE login = ?";
        return SQL;
    }


    public static List<Student> getAllObjects() throws SQLException {
        List<Student> list = new ArrayList<Student>();
        String SQL = "SELECT * FROM Studenci NATURAL JOIN UzytkownicyView";
        Connection connect = MySql.getInstance().getConnect();
        try {
            Statement stmt = connect.createStatement();
            stmt.executeQuery(SQL);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                list.add(Student.setValuesFromRS(rs));
            }
        } catch (SQLException e) {
            throw e;
        }
        return list;
    }


    public static Student setValuesFromRS(ResultSet rs) throws SQLException {
        try {
            String login = rs.getString(1);
            String kierunek = rs.getString(2);
            String wydzial = rs.getString(3);
            int semestr = rs.getInt(4);
            int grupa = rs.getInt(5);
            String name = rs.getString(6);
            String surname = rs.getString(7);
            Student newStudent= new Student(login,name,surname,"",kierunek,wydzial,semestr,grupa);
            return newStudent;
        } catch (SQLException e)
        {
            throw e;
        }
    }
}
