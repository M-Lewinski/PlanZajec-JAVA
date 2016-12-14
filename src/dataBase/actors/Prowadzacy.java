package dataBase.actors;

import dataBase.MySql;
import dataBase.generator.SqlClassGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
}
