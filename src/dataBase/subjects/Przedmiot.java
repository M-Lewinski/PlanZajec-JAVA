package dataBase.subjects;

import GUI.MessageMenu.Error.ErrorField;
import dataBase.MySql;
import dataBase.SqlObject;
import dataBase.generator.SqlClassGenerator;
import dataBase.structure.Kierunek;
import dataBase.structure.Wydzial;
import javafx.scene.paint.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Przedmiot extends SqlObject {
    private String nazwa;
    private String kierunek;
    private String wydzial;
    private Color color;

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getKierunek() {
        return kierunek;
    }

    public void setKierunek(String kierunek) {
        this.kierunek = kierunek;
    }

    public Przedmiot(String nazwa, String kierunek,String wydzial, Color color) {
        this.nazwa = nazwa;
        this.kierunek = kierunek;
        this.wydzial = wydzial;
        this.color = color;
    }

    @Override
    public void addObjectToBase(PreparedStatement stmt) throws SQLException {
        try {
            this.setValueSQL(stmt,1,this.nazwa);
            this.setValueSQL(stmt, 2, this.kierunek);
            this.setValueSQL(stmt, 3, this.wydzial);
            this.setValueSQL(stmt, 4, this.getRed());
            this.setValueSQL(stmt, 5, this.getGreen());
            this.setValueSQL(stmt, 6, this.getBlue());
        } catch (SQLException e) {
            ErrorField.error("Failure while inserting new subject to database");
            throw e;
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String getInsertSQL() {
        String SQL = "INSERT INTO Przedmioty " +
                "VALUES(?,?,?,?,?,?,?)";
        return SQL;
    }

    public int getGreen(){
        return (int) (this.color.getGreen()*255);
    }

    public int getRed(){
        return (int) (this.color.getRed()*255);
    }

    public int getBlue(){
        return (int) (this.color.getBlue()*255);
    }
    @Override
    public void generateObject(PreparedStatement stmt, List<ArrayList<String>> data, SqlClassGenerator sqlClassGenerator, int i) throws SQLException {

    }


    public static List<Przedmiot> getAllObjects() throws SQLException {
        List<Przedmiot> list = new ArrayList<Przedmiot>();
        String SQL = "SELECT * FROM Przedmioty";
        Connection connect = MySql.getInstance().getConnect();
        try {
            Statement stmt = connect.createStatement();
            stmt.executeQuery(SQL);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()){
                list.add(Przedmiot.setValuesFromRS(rs));
            }
        } catch (SQLException e){
            throw e;
        }
        return list;
    }


    public static Przedmiot setValuesFromRS(ResultSet rs) throws SQLException {
        try {
            String nazwa = rs.getString(1);
            String kierunek = rs.getString(2);
            String wydzial = rs.getString(3);
            int red = rs.getInt(4);
            int green = rs.getInt(5);
            int blue = rs.getInt(6);
            Color color = new Color(red/255.0,green/255.0,blue/255.0,1.0);
            Przedmiot newPrzedmiot = new Przedmiot(nazwa,kierunek,wydzial,color);
            return newPrzedmiot;
        } catch (SQLException e){
            throw e;
        }
    }

    public String getWydzial() {
        return wydzial;
    }

    @Override
    public void deleteObjectFromBase(PreparedStatement stmt) throws SQLException {
        try {
        this.setValueSQL(stmt,1,this.nazwa);
    } catch (SQLException e) {
        ErrorField.error("Failure while deleting subject from database");
        throw e;
    }
    }

    @Override
    public String getDeleteSQL() {
        String SQL = "DELETE FROM Przedmioty WHERE nazwa = ?";
    return SQL;
    }
}
