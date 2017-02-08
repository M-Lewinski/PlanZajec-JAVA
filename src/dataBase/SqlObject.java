package dataBase;

import dataBase.generator.SqlClassGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lewin on 11/19/16.
 */
public abstract class SqlObject {
//    private String insertSQL;

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

    public abstract String getInsertSQL();

//    public void setInsertSQL(String insertSQL) {
//        this.insertSQL = insertSQL;
//    }

    public abstract void generateObject(PreparedStatement stmt, List<ArrayList<String>> data, SqlClassGenerator sqlClassGenerator, int i) throws SQLException;

    public void setValueSQL(PreparedStatement stmt,int position, String string) throws SQLException{
        if (string == null)
            stmt.setNull(position, Types.VARCHAR);
        else
            stmt.setString(position,string);
    }

    public void setValueSQL(PreparedStatement stmt,int position, Float f) throws SQLException{
        if (f == null)
            stmt.setNull(position, Types.FLOAT);
        else
            stmt.setFloat(position,f);
    }

    public void setValueSQL(PreparedStatement stmt,int position, Integer i) throws SQLException{
        if (i == null)
            stmt.setNull(position, Types.INTEGER);
        else
            stmt.setInt(position,i);
    }


    public void setValueSQL(PreparedStatement stmt,int position, Date date) throws SQLException{
        if (date == null)
            stmt.setNull(position, Types.DATE);
        else
            stmt.setDate(position,date);
    }

    public List<String> getRelatedNames() throws SQLException {return null;};

}
