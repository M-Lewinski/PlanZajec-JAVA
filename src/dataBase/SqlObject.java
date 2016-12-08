package dataBase;

import dataBase.generator.SqlClassGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lewin on 11/19/16.
 */
public abstract class SqlObject {
    private String insertSQL;

    public abstract void addObjectToBase(PreparedStatement stmt) throws SQLException;

    public PreparedStatement addObjectToBase(Connection connection) throws SQLException {
        try {
            PreparedStatement stmt = connection.prepareStatement(this.getInsertSQL());
            addObjectToBase(stmt);
            return stmt;
        } catch (SQLException e) {
            throw e;
        }
    }

    public String getInsertSQL() {
        return insertSQL;
    }

    public void setInsertSQL(String insertSQL) {
        this.insertSQL = insertSQL;
    }

    public abstract void generateObject(PreparedStatement stmt, List<ArrayList<String>> data, SqlClassGenerator sqlClassGenerator) throws SQLException;
}
