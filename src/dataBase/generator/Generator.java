package dataBase.generator;

import dataBase.MySql;
import dataBase.SqlObject;
import dataBase.structure.Kierunek;
import dataBase.structure.Wydzial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lewin on 11/17/16.
 */
public class Generator {
    /**
     * Pusty konstruktor
     */
    public Generator() {}
    /**
     * Czytanie z pliku linii i zapisywanie ich do listy, która jest zwracana.
     * @param filePaths - Ścieżki do plikow.
     */
    private List<ArrayList<String>> readFile(List<String> filePaths){
        List fileList = new ArrayList<ArrayList<String>>();
        for (String path: filePaths){
            BufferedReader buffer = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));
            try {
                ArrayList lineList = new ArrayList<String>();
                String line;
                while((line = buffer.readLine())!= null){
                    lineList.add(line);
                }
                if (lineList.isEmpty()) continue;
                fileList.add(lineList);
            }
            catch (IOException e){
                System.err.println("Generowanie z pliku");
                e.printStackTrace();
            }
            finally {
                try {
                    buffer.close();
                }
                catch (IOException e){
                    System.err.println("Zamykanie buffora");
                    e.printStackTrace();
                }
            }
        }
        return fileList;
    }

    public void generating(List<ArrayList<String>> data, int howMany, SqlObject newObject){
//        List<String> list = readFile(fileName);
//        if (list.isEmpty()){
//            System.err.println("PUSTY PLIK");
//            return;
//        }
        Connection connect = MySql.getInstance().getConnect();
        Savepoint save = null;
        try {
            save = connect.setSavepoint("GENERATING");
            PreparedStatement stmt = connect.prepareStatement(newObject.getInsertSQL());
            SqlClassGenerator sqlClassGenerator = new SqlClassGenerator();
            for (int i = 0; i < howMany; i++) {
                newObject.generateObject(stmt,data,sqlClassGenerator);
            }
            stmt.executeBatch();
            connect.commit();
        } catch (SQLException e){
            e.printStackTrace();
            try {
                if (save !=null){
                    connect.rollback(save);
                }
            } catch (SQLException e2){
                System.err.println("ROLLBACK ERROR");
                e2.printStackTrace();
            }
        }
        finally {
            if(save!=null){
                try {
                    connect.releaseSavepoint(save);
                }catch (SQLException e){
                    System.err.println("SAVE POINT ERROR");
                    e.printStackTrace();
                }
            }
        }
    }


    private interface  Command{
        public PreparedStatement createSqlObject(Connection connection, List<String> list) throws SQLException;
    }

    private class GenWydzialy implements Command{
        @Override
        public PreparedStatement createSqlObject(Connection connection, List<String> list) throws SQLException {
            try {
                PreparedStatement stmt = null;
                for  (String line: list) {
                    Wydzial wydzial = new Wydzial(line);
                    if (stmt == null){
                        stmt = wydzial.addObjectToBase(connection);
                    }
                    else {
                        wydzial.addObjectToBase(stmt);
                    }
                    stmt.addBatch();
                }
                return stmt;
            } catch (SQLException e){
                System.err.println("BATCH ERROR");
                throw e;
            }
        }
    }

    private class GenKierunki implements Command{
        @Override
        public PreparedStatement createSqlObject(Connection connection, List<String> list) throws SQLException {
            try {
                PreparedStatement stmt = null;
                for  (String line: list) {
                    String[] parts = line.split(" ");
                    Kierunek kierunek = new Kierunek(parts[0],parts[1]);
                    if (stmt == null){
                        stmt = kierunek.addObjectToBase(connection);
                    }
                    else {
                        kierunek.addObjectToBase(stmt);
                    }
                    stmt.addBatch();
                }
                return stmt;
            } catch (SQLException e){
                System.err.println("BATCH ERROR");
                throw e;
            }
        }
    }

    public void createWydzialy(){
        List<String> filePaths = new ArrayList<String>();
        filePaths.add("wydzialy.txt");
        List<ArrayList<String>> data = this.readFile(filePaths);
        this.generating(data,data.get(0).size(),new Wydzial());
    }

//    public void createKierunki(){
//        this.generating("kierunki.txt", new GenKierunki());
//    }


}
