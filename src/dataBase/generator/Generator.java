package dataBase.generator;

import dataBase.MySql;
import dataBase.SqlObject;

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
     * Empty Constructor
     */
    public Generator() {
    }

    /**
     * Czytanie z pliku linii i zapisywanie ich do listy, która jest następnie zwracana.
     *
     * @param filePaths - Ścieżki do plikow.
     */
    private List<ArrayList<String>> readFiles(String[] filePaths) throws NullPointerException {
        List fileList = new ArrayList<ArrayList<String>>();
        for (String path : filePaths) {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));
            try {
                ArrayList lineList = new ArrayList<String>();
                String line;
                while ((line = buffer.readLine()) != null) {
                    lineList.add(line);
                }
                if (lineList.isEmpty()) continue;
                fileList.add(lineList);
            } catch (IOException e) {
                System.err.println("Generowanie z pliku");
                e.printStackTrace();
            } catch (NullPointerException e){
                System.err.println("Nie ma takiego pliku");
            } finally {
                try {
                    buffer.close();
                } catch (IOException e) {
                    System.err.println("Zamykanie buffora");
                    e.printStackTrace();
                }
            }
        }
        return fileList;
    }

    public void generating(List<ArrayList<String>> data, int howMany, SqlObject newObject) {
        Connection connect = MySql.getInstance().getConnect();
        Savepoint save = null;
        try {
            save = connect.setSavepoint("GENERATING");
            PreparedStatement stmt = connect.prepareStatement(newObject.getInsertSQL());
            SqlClassGenerator sqlClassGenerator = new SqlClassGenerator();
            for (int i = 0; i < howMany; i++) {
                newObject.generateObject(stmt, data, sqlClassGenerator, i);
                stmt.addBatch();
            }
            stmt.executeBatch();
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (save != null) {
                    connect.rollback(save);
                }
            } catch (SQLException e2) {
                System.err.println("ROLLBACK ERROR");
                e2.printStackTrace();
            }
        } finally {
            if (save != null) {
                try {
                    connect.releaseSavepoint(save);
                } catch (SQLException e) {
                    System.err.println("SAVE POINT ERROR");
                    e.printStackTrace();
                }
            }
        }
    }

    public void createSqlObjectsFromFiles(SqlObject sqlObject,int howMany, String[] filePaths){
        List<ArrayList<String>> data = this.readFiles(filePaths);
        if (howMany > 0){
            this.generating(data,howMany,sqlObject);
        }
        else{
            this.generating(data,data.get(0).size(),sqlObject);
        }

    }

}
