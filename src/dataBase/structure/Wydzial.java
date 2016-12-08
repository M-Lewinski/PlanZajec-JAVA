package dataBase.structure;

import dataBase.SqlObject;
import dataBase.generator.SqlClassGenerator;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Wydzial extends SqlObject {
    private String nazwa;

    public Wydzial() {
        String SQL = "INSERT INTO Wydzialy " +
                "VALUES(?)";
        this.setInsertSQL(SQL);
    }

    public Wydzial(String nazwa) {
        this();
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
    public void generateObject(PreparedStatement stmt, List<ArrayList<String>> data, SqlClassGenerator sqlClassGenerator) throws SQLException {
        try {
            sqlClassGenerator.generate(this, data, stmt);
        } catch (SQLException e) {
            throw e;
        }
    }
}
