package dataBase.generator;

import dataBase.structure.Wydzial;

import java.sql.PreparedStatement;
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

    public void generate(Wydzial wydzial, List<ArrayList<String>> data, PreparedStatement stmt){
        if(!check(data,stmt)) return;
        Wydzial newObject = new Wydzial(data.get(0).get(0));
        data.get(0).remove(0);
    }

}
