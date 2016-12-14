package dataBase.generator;

import dataBase.structure.Kierunek;
import dataBase.structure.Semestr;
import dataBase.structure.Wydzial;
import dataBase.subjects.Sala;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
}
