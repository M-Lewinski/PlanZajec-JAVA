package dataBase.subjects;

import GUI.MessageMenu.Error.ErrorField;
import dataBase.MySql;
import dataBase.SqlObject;
import dataBase.actors.Prowadzacy;
import dataBase.generator.SqlClassGenerator;
import javafx.scene.paint.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Zajecie extends SqlObject {
    private int id;
    private String rocznik;
    private int dzien;
    private int godzina;
    private int tydzien;
    private String rodzaj;
    private int liczba_godzin;
    private int grupa;
    private int podgrupa;
    private String przedmiot;
    private String login_prowadzacego;
    private String sala;
    private String budynek;

    private Prowadzacy professor;
    private Przedmiot subject;

    public Zajecie(int id, String rocznik, int dzien, int godzina, int tydzien, String rodzaj, int liczba_godzin, int grupa, int podgrupa, String przedmiot, String login_prowadzacego, String sala, String budynek,Prowadzacy professor, Przedmiot subject) {
        this.id = id;
        this.rocznik = rocznik;
        this.dzien = dzien;
        this.godzina = godzina;
        this.tydzien = tydzien;
        this.rodzaj = rodzaj;
        this.liczba_godzin = liczba_godzin;
        this.grupa = grupa;
        this.podgrupa = podgrupa;
        this.przedmiot = przedmiot;
        this.login_prowadzacego = login_prowadzacego;
        this.sala = sala;
        this.budynek = budynek;

        this.professor = professor;
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRocznik() {
        return rocznik;
    }

    public void setRocznik(String rocznik) {
        this.rocznik = rocznik;
    }

    public int getDzien() {
        return dzien;
    }

    public void setDzien(int dzien) {
        this.dzien = dzien;
    }

    public int getGodzina() {
        return godzina;
    }

    public void setGodzina(int godzina) {
        this.godzina = godzina;
    }

    public int getTydzien() {
        return tydzien;
    }

    public void setTydzien(int tydzien) {
        this.tydzien = tydzien;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
    }

    public int getLiczba_godzin() {
        return liczba_godzin;
    }

    public void setLiczba_godzin(int liczba_godzin) {
        this.liczba_godzin = liczba_godzin;
    }

    public int getGrupa() {
        return grupa;
    }

    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }

    public int getPodgrupa() {
        return podgrupa;
    }

    public void setPodgrupa(int podgrupa) {
        this.podgrupa = podgrupa;
    }

    public String getPrzedmiot() {
        return przedmiot;
    }

    public void setPrzedmiot(String przedmiot) {
        this.przedmiot = przedmiot;
    }

    public String getLogin_prowadzacego() {
        return login_prowadzacego;
    }

    public void setLogin_prowadzacego(String login_prowadzacego) {
        this.login_prowadzacego = login_prowadzacego;
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


    @Override
    public void addObjectToBase(PreparedStatement stmt) throws SQLException {
        try {
            this.setValueSQL(stmt, 1, this.rocznik);
            this.setValueSQL(stmt, 2, this.dzien);
            this.setValueSQL(stmt, 3, this.godzina);
            this.setValueSQL(stmt, 4, this.tydzien);
            this.setValueSQL(stmt, 5, this.rodzaj);
            this.setValueSQL(stmt, 6, this.liczba_godzin);
            this.setValueSQL(stmt, 7, this.grupa);
            this.setValueSQL(stmt, 8, this.podgrupa);
            this.setValueSQL(stmt, 9, this.przedmiot);
            this.setValueSQL(stmt, 10, this.login_prowadzacego);
            this.setValueSQL(stmt, 11, this.sala);
            this.setValueSQL(stmt, 12, this.budynek);
        } catch (SQLException e) {
            ErrorField.error("Failure while inserting new class to database");
            throw e;
        }
    }

    @Override
    public void deleteObjectFromBase(PreparedStatement stmt) throws SQLException {
        try {
            this.setValueSQL(stmt, 1, this.id);
        } catch (SQLException e) {
            ErrorField.error("Failure while deleting class from database");
            throw e;
        }
    }

    @Override
    public String getInsertSQL() {
        String SQL = "INSERT INTO Zajecia (rocznik,dzien,godzina,tydzien,rodzaj,liczba_godzin,grupa,podgrupa,przedmiot,login_prowadzacego,sala,budynek) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        return SQL;
    }

    @Override
    public String getDeleteSQL() {
        String SQL = "DELETE FROM Zajecia WHERE id = ?";
        return SQL;
    }

    @Override
    public void generateObject(PreparedStatement stmt, List<ArrayList<String>> data, SqlClassGenerator sqlClassGenerator, int i) throws SQLException {

    }

    public static List<Zajecie> getAllObjects() throws SQLException {
        List<Zajecie> list = new ArrayList<Zajecie>();
        String SQL = "SELECT * FROM Zajecia z NATURAL JOIN UzytkownicyView u NATURAL JOIN Przedmioty p";
        Connection connect = MySql.getInstance().getConnect();
        try {
            Statement stmt = connect.createStatement();
            stmt.executeQuery(SQL);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                list.add(Zajecie.setValuesFromRS(rs,0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return list;
    }


    public static Zajecie setValuesFromRS(ResultSet rs,int i) throws SQLException {
        try {
            int id = rs.getInt(i+1);
            String rocznik = rs.getString(i+2);
            int dzien = rs.getInt(i+3);
            int godzina = rs.getInt(i+4);
            int tydzien = rs.getInt(i+5);
            String rodzaj = rs.getString(i+6);
            int liczba_godzin = rs.getInt(i+7);
            int grupa = rs.getInt(i+8);
            int podgrupa = rs.getInt(i+9);
            String przedmiot = rs.getString(i+10);
            String login_prowadzacego = rs.getString(i+11);
            String sala = rs.getString(i+12);
            String budynek = rs.getString(i+13);

            String imie = rs.getString(i+15);
            String nazwisko = rs.getString(i+16);
            String kierunek = rs.getString(i+18);
            String wydzial = rs.getString(i+19);
            int semestr = rs.getInt(i+20);
            int red = rs.getInt(i+21);
            int green = rs.getInt(i+22);
            int blue = rs.getInt(i+23);
            Prowadzacy prowadzacy = new Prowadzacy(login_prowadzacego,imie,nazwisko,"");
            Color color = new Color(red/255.0,green/255.0,blue/255.0,1.0);
            Przedmiot subject = new Przedmiot(przedmiot,kierunek,wydzial,semestr,color);

            Zajecie newZajecie = new Zajecie(id, rocznik, dzien, godzina, tydzien, rodzaj, liczba_godzin, grupa, podgrupa, przedmiot, login_prowadzacego, sala, budynek,prowadzacy,subject);
            return newZajecie;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static int convertWeek(String week) {
        String test = week.toLowerCase();
        switch (test) {
            case "all":
                return 0;
            case "odd":
                return 1;
            case "even":
                return 2;
        }
        return -1;
    }

    public static String convertWeek(int week) {
        switch (week) {
            case 0:
                return "All";
            case 1:
                return "Odd";
            case 2:
                return "Even";
        }
        return null;
    }


    public static int convertHour(String hour) {
        String test = hour.toLowerCase();
        switch (test) {
            case "8:00 - 9:30":
                return 1;
            case "9:45 - 11:15":
                return 2;
            case "11:45 - 13:15":
                return 3;
            case "13:30 - 15:00":
                return 4;
            case "15:10 - 16:40":
                return 5;
            case "16:50 - 18:20":
                return 6;
            case "18:30 - 20:00":
                return 7;
            case "20:10 - 21:40":
                return 8;
        }
        return -1;
    }

    public static String convertHour(int hour) {
        switch (hour) {
            case 1:
                return "8:00 - 9:30";
            case 2:
                return "9:45 - 11:15";
            case 3:
                return "11:45 - 13:15";
            case 4:
                return "13:30 - 15:00";
            case 5:
                return "15:10 - 16:40";
            case 6:
                return "16:50 - 18:20";
            case 7:
                return "18:30 - 20:00";
            case 8:
                return "20:10 - 21:40";
        }
        return null;
    }

    public static int convertDay(String day) {
        String test = day.toLowerCase();
        switch (test) {
            case "monday":
                return 0;
            case "tuesday":
                return 1;
            case "wednesday":
                return 2;
            case "thursday":
                return 3;
            case "friday":
                return 4;
        }
        return -1;
    }

    public static String convertDay(int day) {
        switch (day) {
            case 0:
                return "Monday";
            case 1:
                return "Tuesday";
            case 2:
                return "Wednesday";
            case 3:
                return "Thursday";
            case 4:
                return "Friday";
        }
        return null;
    }

    public Prowadzacy getProfessor() {
        return professor;
    }

    public void setProfessor(Prowadzacy professor) {
        this.professor = professor;
    }

    public Przedmiot getSubject() {
        return subject;
    }

    public void setSubject(Przedmiot subject) {
        this.subject = subject;
    }

    @Override
    public void delete(){
        this.professor = null;
        this.subject = null;
    }
}
