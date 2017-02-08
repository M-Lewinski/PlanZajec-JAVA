package dataBase.structure;

import dataBase.MySql;
import dataBase.SqlObject;
import dataBase.generator.SqlClassGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Kierunek extends SqlObject {
    private String nazwa;
    private String nazwa_wydzialu;

    public Kierunek() {}

    public Kierunek(String nazwa, String nazwa_wydzialu) {
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
        try {
            stmt.setString(1, this.nazwa);
            stmt.setString(2, this.nazwa_wydzialu);
        } catch (SQLException e) {
            System.err.println("BLAD W Tworzeniu");
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
        String SQL = "INSERT INTO Kierunki " +
                "VALUES(?,?)";
        return SQL;
    }

    @Override
    public void deleteObjectFromBase(PreparedStatement stmt) throws SQLException {
        try {
            this.addObjectToBase(stmt);
        }
        catch (SQLException e){
            throw e;
        }
    }

    @Override
    public String getDeleteSQL() {
        String SQL = "DELETE FROM Kierunki WHERE nazwa = ? AND nazwa_wydzialu = ?";
        return SQL;
    }

    public static List<Kierunek> getAllObjects(Wydzial wydzial) throws SQLException{
        List<Kierunek> list = new ArrayList<Kierunek>();
        String SQL = "SELECT * FROM Kierunki k WHERE k.nazwa_wydzialu = ?";
        Connection connect = MySql.getInstance().getConnect();
        try {
            PreparedStatement stmt = connect.prepareStatement(SQL);
            stmt.setString(1,wydzial.getNazwa());
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()){
                list.add(Kierunek.setValuesFromRS(rs));
            }
        } catch (SQLException e){
            throw e;
        }
        return list;
    }

    public static Kierunek setValuesFromRS(ResultSet rs) throws SQLException{
        try {
            String nazwa = rs.getString(1);
            String nazwa_wydzialu = rs.getString(2);
            Kierunek newKierunek = new Kierunek(nazwa,nazwa_wydzialu);
            return newKierunek;
        } catch (SQLException e){
            throw e;
        }
    }

    @Override
    public String toString() {
        return this.nazwa;
    }

    @Override
    public List<? extends SqlObject> getRelatedObjects() throws SQLException {
        return Semestr.getAllObjects();
    }
}
