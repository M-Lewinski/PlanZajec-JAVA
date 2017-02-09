package dataBase.subjects;

import GUI.MessageMenu.Error.ErrorField;
import dataBase.MySql;
import dataBase.SqlObject;
import dataBase.generator.SqlClassGenerator;
import javafx.scene.paint.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Sala extends SqlObject {
    private String sala;
    private String budynek;
    private int liczba_miejsc;

    public Sala() {}

    public Sala(String sala, String budynek, int liczba_miejsc) {
        this();
        this.sala = sala;
        this.budynek = budynek;
        this.liczba_miejsc = liczba_miejsc;
    }

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

    public int getLiczba_miejsc() {
        return liczba_miejsc;
    }

    public void setLiczba_miejsc(int liczba_miejsc) {
        this.liczba_miejsc = liczba_miejsc;
    }

    @Override
    public void addObjectToBase(PreparedStatement stmt) throws SQLException {
        try {
            this.setValueSQL(stmt,1,this.sala);
            this.setValueSQL(stmt,2,this.budynek);
            this.setValueSQL(stmt,3,this.liczba_miejsc);
        } catch (SQLException e) {
            ErrorField.error("Failure while inserting new Rooms to database");
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
        String SQL = "INSERT INTO Sale " +
                "VALUES(?,?,?)";
        return SQL;
    }

    @Override
    public void deleteObjectFromBase(PreparedStatement stmt) throws SQLException {
        try {
            this.setValueSQL(stmt,1,this.sala);
            this.setValueSQL(stmt,2,this.budynek);
        } catch (SQLException e) {
            ErrorField.error("Failure while deleting Room from database");
            throw e;
        }
    }

    @Override
    public String getDeleteSQL() {
        String SQL = "DELETE FROM Sale WHERE sala = ? AND budynek = ?";
        return SQL;
    }


    public static List<Sala> getAllObjects() throws SQLException {
        List<Sala> list = new ArrayList<Sala>();
        String SQL = "SELECT * FROM Sale";
        Connection connect = MySql.getInstance().getConnect();
        try {
            Statement stmt = connect.createStatement();
            stmt.executeQuery(SQL);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()){
                list.add(Sala.setValuesFromRS(rs));
            }
        } catch (SQLException e){
            throw e;
        }
        return list;
    }


    public static Sala setValuesFromRS(ResultSet rs) throws SQLException {
        try {
            String nazwa = rs.getString(1);
            String building = rs.getString(2);
            int slots = rs.getInt(3);
            Sala newSala = new Sala(nazwa,building,slots);
            return newSala;
        } catch (SQLException e){
            throw e;
        }
    }

    @Override
    public String toString() {
        return this.sala + " " + this.budynek + " (" + Integer.toString(this.liczba_miejsc) +")";
    }
}

