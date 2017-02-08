package dataBase.structure;

import dataBase.MySql;
import dataBase.SqlObject;
import dataBase.generator.SqlClassGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Semestr extends SqlObject {
    private int numer;

    public Semestr() {
    }

    public Semestr(int numer) {
        this.numer = numer;
    }

    public int getNumer() {
        return numer;
    }

    public void setNumer(int numer) {
        this.numer = numer;
    }

    @Override
    public void addObjectToBase(PreparedStatement stmt) throws SQLException {
        try {
            stmt.setInt(1, this.numer);
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
        String SQL = "INSERT INTO Semestry " +
                "VALUES (?)";
        return SQL;
    }

    @Override
    public String getDeleteSQL() {
        String SQL = "DELETE FROM Semestry WHERE numer = ?";
        return SQL;
    }

    @Override
    public void deleteObjectFromBase(PreparedStatement stmt) throws SQLException {
        try {
            stmt.setInt(1, this.numer);
        } catch (SQLException e) {
            System.err.println("BLAD W Tworzeniu");
            throw e;
        }
    }

    public static List<Semestr> getAllObjects() throws SQLException{
        List<Semestr> list = new ArrayList<Semestr>();
        String SQL = "SELECT * FROM Semestry";
        Connection connect = MySql.getInstance().getConnect();
        try {
            PreparedStatement stmt = connect.prepareStatement(SQL);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()){
                list.add(Semestr.setValuesFromRS(rs));
            }
        } catch (SQLException e){
            throw e;
        }
        return list;
    }

    public static Semestr setValuesFromRS(ResultSet rs) throws SQLException{
        try {
            int numer = rs.getInt(1);
            Semestr newSemester= new Semestr(numer);
            return newSemester;
        } catch (SQLException e){
            throw e;
        }
    }

    @Override
    public String toString() {
        return Integer.toString(this.numer);
    }
}
