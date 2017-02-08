package dataBase.actors;

import dataBase.MySql;
import dataBase.generator.SqlClassGenerator;
import dataBase.subjects.Przedmiot;
import javafx.scene.paint.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Prowadzacy extends Uzytkownik {
    private String login;
    private String tytul;

    public Prowadzacy() {}

    public Prowadzacy(String login, String imie, String nazwisko, String haslo) {
        super(login, imie, nazwisko, haslo);
        this.login = login;
        this.tytul = null;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    @Override
    public void addObjectToBase(PreparedStatement stmt) throws SQLException {
        try {
            this.setValueSQL(stmt,1,this.login);
            this.setValueSQL(stmt,2,this.tytul);
            Connection connect = MySql.getInstance().getConnect();
            PreparedStatement stmt2 = connect.prepareStatement(super.getInsertSQL());
            super.addObjectToBase(stmt2);
            stmt2.executeUpdate();
        } catch (SQLException e){
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
        String SQL = "INSERT INTO Prowadzacy " +
                "VALUES(?,?)";
        return SQL;
    }

    @Override
    public void deleteObjectFromBase(PreparedStatement stmt) throws SQLException {
        try {
            this.setValueSQL(stmt,1,this.login);
            Connection connect = MySql.getInstance().getConnect();
            PreparedStatement stmt2 = connect.prepareStatement(super.getDeleteSQL());
            super.deleteObjectFromBase(stmt2);
            stmt2.executeUpdate();
        } catch (SQLException e){
            throw e;
        }
    }

    @Override
    public String getDeleteSQL() {
        String SQL = "DELETE FROM Prowadzacy WHERE login = ?";
        return SQL;
    }



    public static List<Prowadzacy> getAllObjects() throws SQLException {
        List<Prowadzacy> list = new ArrayList<Prowadzacy>();
        String SQL = "SELECT * FROM Prowadzacy NATURAL JOIN UzytkownicyView";
        Connection connect = MySql.getInstance().getConnect();
        try {
            Statement stmt = connect.createStatement();
            stmt.executeQuery(SQL);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()){
                list.add(Prowadzacy.setValuesFromRS(rs));
            }
        } catch (SQLException e){
            throw e;
        }
        return list;
    }


    public static Prowadzacy setValuesFromRS(ResultSet rs) throws SQLException {
        try {
            String login = rs.getString(1);
            String tytul = rs.getString(2);
            String name = rs.getString(3);
            String surname = rs.getString(4);
            Prowadzacy newProwadzacy= new Prowadzacy(login,name,surname,"");
            newProwadzacy.setTytul(tytul);
            return newProwadzacy;
        } catch (SQLException e){
            throw e;
        }
    }
}
