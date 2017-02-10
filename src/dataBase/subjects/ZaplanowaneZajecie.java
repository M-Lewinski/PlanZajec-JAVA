package dataBase.subjects;

import GUI.MessageMenu.Error.ErrorField;
import dataBase.MySql;
import dataBase.SqlObject;
import dataBase.generator.SqlClassGenerator;
import javafx.scene.paint.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZaplanowaneZajecie extends SqlObject {
    private java.sql.Date data;
    private int id_zajecia;
    private Zajecie zajecie;

    public java.sql.Date getData() {
        return data;
    }

    public void setData(java.sql.Date data) {
        this.data = data;
    }

    public int getId_zajecia() {
        return id_zajecia;
    }

    public void setId_zajecia(int id_zajecia) {
        this.id_zajecia = id_zajecia;
    }

    public ZaplanowaneZajecie(Date data, int id_zajecia, Zajecie zajecie) {
        this.data = data;
        this.id_zajecia = id_zajecia;
        this.zajecie = zajecie;
    }

    @Override
    public void addObjectToBase(PreparedStatement stmt) throws SQLException {
        try {
            this.setValueSQL(stmt, 1, this.data);
            this.setValueSQL(stmt, 2, this.id_zajecia);
        } catch (SQLException e) {
            ErrorField.error("Failure while inserting new class to database");
            throw e;
        }
    }

    @Override
    public void deleteObjectFromBase(PreparedStatement stmt) throws SQLException {
        try {
            this.setValueSQL(stmt, 1, this.data);
            this.setValueSQL(stmt, 2, this.id_zajecia);
        } catch (SQLException e) {
            ErrorField.error("Failure while deleting class from database");
            throw e;
        }
    }

    @Override
    public String getInsertSQL() {
        String SQL = "INSERT INTO Zaplanowane_zajecia " +
                "VALUES(?,?)";
        return SQL;
    }

    @Override
    public String getDeleteSQL() {
        String SQL = "DELETE FROM Zaplanowane_zajecia WHERE data = ? AND id_zajecia = ?";
        return SQL;
    }

    @Override
    public void generateObject(PreparedStatement stmt, List<ArrayList<String>> data, SqlClassGenerator sqlClassGenerator, int i) throws SQLException {

    }

    @Override
    public void delete() {
        this.zajecie.delete();
        this.zajecie = null;
    }

    public Zajecie getZajecie() {
        return zajecie;
    }

    public void setZajecie(Zajecie zajecie) {
        this.zajecie = zajecie;
    }


    public static List<ZaplanowaneZajecie> getAllObjects(Zajecie zajecie) throws SQLException {
        List<ZaplanowaneZajecie> list = new ArrayList<ZaplanowaneZajecie>();
        String SQL = "SELECT * FROM Zaplanowane_zajecia zz JOIN Zajecia z JOIN UzytkownicyView u  JOIN Przedmioty p WHERE zz.id_zajecia = ? AND zz.id_zajecia = z.id AND z.login_prowadzacego = u.login AND z.przedmiot = p.nazwa ORDER BY zz.data ASC";
        Connection connect = MySql.getInstance().getConnect();
        try {
            PreparedStatement stmt = connect.prepareStatement(SQL);
            stmt.setInt(1,zajecie.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                list.add(ZaplanowaneZajecie.setValuesFromRS(rs));
            }
        } catch (SQLException e){
            throw e;
        }
        return list;
    }


    public static ZaplanowaneZajecie setValuesFromRS(ResultSet rs) throws SQLException {
        try {
            Date date = rs.getDate(1);
            int id_zajecia = rs.getInt(2);
            Zajecie zajecie = Zajecie.setValuesFromRS(rs,2);
            ZaplanowaneZajecie newZaplanowaneZajecie = new ZaplanowaneZajecie(date,id_zajecia,zajecie);
            return newZaplanowaneZajecie;
        } catch (SQLException e){
            throw e;
        }
    }

    @Override
    public String toString() {
        return this.data.toString();
    }
}
