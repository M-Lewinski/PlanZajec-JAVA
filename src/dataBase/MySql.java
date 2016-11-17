package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by lewin on 11/17/16.
 */
public class MySql {
    private String dbms;
    private String serverName;
    private Integer portNumber;
    private String JDBC = "com.mysql.jdbc.Driver";
    private Connection connect = null;
    public MySql(String login, String password){
        try {
            Class.forName(JDBC);
        }
        catch (ClassNotFoundException e){
            System.out.println("Driver ERROR");
            e.printStackTrace();
        }
        this.dbms = "mysql";
        this.serverName = "127.0.0.1";
        this.portNumber = 3306;
        if (this.dbms.equals("mysql")) {
            try {
                connect = DriverManager.getConnection(
                        "jdbc:" + this.dbms + "://" +
                                this.serverName +
                                ":" + this.portNumber + "/",
                        login,
                        password
                );
                System.out.println("Connected Succesfully on " + login);
            }
            catch (SQLException e) {
                System.out.println("Connection ERROR");
                e.printStackTrace();
            }
        }
    }
    public void closeConnect(){
        try {
            if (this.connect != null){
                this.connect.close();

            }
        }catch (SQLException e){
            System.out.println("MYSQL CLOSE ERROR");
            e.printStackTrace();
        }
    }
}
