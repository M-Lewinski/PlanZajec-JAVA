package dataBase.structure;

import dataBase.MySql;
import dataBase.SqlObject;
import dataBase.generator.SqlClassGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Wydzial extends SqlObject {
    private String nazwa;

    public Wydzial() {}

    public Wydzial(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @Override
    public void addObjectToBase(PreparedStatement stmt) throws SQLException {
        try {
            stmt.setString(1, this.nazwa);
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
        String SQL = "INSERT INTO Wydzialy " +
                "VALUES(?)";
        return SQL;
    }

    @Override
    public String getDeleteSQL() {
        String SQL = "DELETE FROM Wydzialy WHERE nazwa = ?";
        return SQL;
    }

    @Override
    public void deleteObjectFromBase(PreparedStatement stmt) throws SQLException {
        try {
            stmt.setString(1, this.nazwa);
        } catch (SQLException e) {
            System.err.println("BLAD W Tworzeniu");
            throw e;
        }
    }

    @Override
    public List<? extends SqlObject> getRelatedObjects() throws SQLException {
        return Kierunek.getAllObjects(this);
    }

    public static List<Wydzial> getAllObjects() throws SQLException {
        List<Wydzial> list = new ArrayList<Wydzial>();
        String SQL = "SELECT * FROM Wydzialy";
        Connection connect = MySql.getInstance().getConnect();
        try {
            Statement stmt = connect.createStatement();
            stmt.executeQuery(SQL);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()){
                list.add(Wydzial.setValuesFromRS(rs));
            }
        } catch (SQLException e){
            throw e;
        }
        return list;
    }

    public static Wydzial setValuesFromRS(ResultSet rs) throws SQLException {
        try {
            String nazwa = rs.getString(1);
            Wydzial newWydzial = new Wydzial(nazwa);
            return newWydzial;
        } catch (SQLException e){
            throw e;
        }
    }

    @Override
    public String toString() {
        return this.nazwa;
    }
}
