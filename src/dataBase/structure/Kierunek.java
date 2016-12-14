package dataBase.structure;

import dataBase.SqlObject;
import dataBase.generator.SqlClassGenerator;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Kierunek extends SqlObject {
    private String nazwa;
    private String nazwa_wydzialu;

    public Kierunek() {
        String SQL = "INSERT INTO Kierunki " +
                "VALUES(?,?)";
        this.setInsertSQL(SQL);
    }

    public Kierunek(String nazwa, String nazwa_wydzialu) {
        this();
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
}
