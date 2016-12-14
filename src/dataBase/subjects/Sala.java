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
    private int liczba_miejsc;

    public Sala() {
        String SQL = "INSERT INTO Sale " +
                "VALUES(?,?,?)";
        this.setInsertSQL(SQL);
    }

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
            stmt.setString(1, this.sala);
            stmt.setString(2, this.budynek);
            stmt.setInt(3, this.liczba_miejsc);
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
}

