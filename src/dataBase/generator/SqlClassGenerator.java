package dataBase.generator;

import dataBase.MySql;
import dataBase.actors.Prowadzacy;
import dataBase.actors.Student;
import dataBase.structure.Kierunek;
import dataBase.structure.Semestr;
import dataBase.structure.Wydzial;
import dataBase.subjects.Sala;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lewin on 12/8/16.
 */
public class SqlClassGenerator {
    private boolean check(List<ArrayList<String>> data,PreparedStatement stmt){
        if (data.isEmpty()){
            System.err.println("DATA IS EMPTY");
            return false;
        }
        if (stmt == null){
            System.err.println("SQL STATEMENT NULL");
            return false;
        }
        return true;
    }

    public void generate(Wydzial wydzial, List<ArrayList<String>> data, PreparedStatement stmt, int i) throws SQLException {
        if(!check(data,stmt)) return;
        Wydzial newObject = new Wydzial(data.get(0).get(i));
        data.get(0).remove(0);
        try {
            newObject.addObjectToBase(stmt);
        }catch (SQLException e){
            throw e;
        }
    }

    public void generate(Kierunek kierunek, List<ArrayList<String>> data, PreparedStatement stmt, int i) throws SQLException {
        if(!check(data,stmt)) return;
        String[] parameters = data.get(0).get(i).split(" ");
        Kierunek newObject = new Kierunek(parameters[0],parameters[1]);
        try {
            newObject.addObjectToBase(stmt);
        }catch (SQLException e){
            throw e;
        }
    }


    public void generate(Sala sala, List<ArrayList<String>> data, PreparedStatement stmt, int i) throws SQLException {
        if(!check(data,stmt)) return;
        String[] parameters = data.get(0).get(i).split(" ");
        Sala newObject = new Sala(parameters[0],parameters[1],Integer.parseInt(parameters[2]));
        try {
            newObject.addObjectToBase(stmt);
        }catch (SQLException e){
            throw e;
        }
    }

    public void generate(Semestr semestr, List<ArrayList<String>> data, PreparedStatement stmt, int i) throws SQLException {
        if(!check(data,stmt)) return;
        Semestr newObject = new Semestr(Integer.parseInt(data.get(0).get(i)));
        try {
            newObject.addObjectToBase(stmt);
        }catch (SQLException e){
            throw e;
        }
    }

    public void generate(Prowadzacy prowadzacy, List<ArrayList<String>> data, PreparedStatement stmt, int i) throws SQLException {
        if(!check(data,stmt)) return;
        Random rand = new Random();
        int index = rand.nextInt(data.get(0).size());
        String imie =data.get(0).get(index);
        index = rand.nextInt(data.get(1).size());
        String nazwisko = data.get(1).get(index);

        String login = Character.toString(Character.toLowerCase(imie.charAt(0)))+Character.toString(Character.toLowerCase(nazwisko.charAt(0)))+Integer.toString(rand.nextInt(2000));
        Prowadzacy newObject = new Prowadzacy(login,imie,nazwisko,login);
        prowadzacy.setTytul("mgr inż.");
        try {
            newObject.addObjectToBase(stmt);
        }catch (SQLException e){
            throw e;
        }
    }

    public void generate(Student student, List<ArrayList<String>> data, PreparedStatement stmt, int i) throws SQLException {
        if(!check(data,stmt)) return;
        Random rand = new Random();
        int index = rand.nextInt(data.get(0).size());
        String imie =data.get(0).get(index);
        index = rand.nextInt(data.get(1).size());
        String nazwisko = data.get(1).get(index);

        String login = Character.toString(Character.toLowerCase(imie.charAt(0)))+Character.toString(Character.toLowerCase(nazwisko.charAt(0)))+Integer.toString(rand.nextInt(2000));

        try {
            Connection connection = MySql.getInstance().getConnect();
            List<Wydzial> wydzialy = Wydzial.getAllObjects();
            List<Kierunek> kierunki = Kierunek.getAllObjects(wydzialy.get(0));
                String kierunek = kierunki.get(0).getNazwa();
                String wydzial = kierunki.get(0).getNazwa_wydzialu();
                int grupa = rand.nextInt(5);
                grupa++;
                Student newObject = new Student(login,imie,nazwisko,login,kierunek,wydzial,7,grupa);
                newObject.addObjectToBase(stmt);
        }catch (SQLException e){
            throw e;
        }
    }

}
