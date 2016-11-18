package dataBase;

import java.sql.*;

/**
 * Created by lewin on 11/17/16.
 */
public class MySql {
    private String dbms = "mysql";
    private String serverName = "127.0.0.1";
    private Integer portNumber = 3306;
    private String JDBC = "com.mysql.jdbc.Driver";
    private Connection connect = null;
    private String checkLogin = "checkLogin";
    private String dataBaseName = "PlanZajec";

    public MySql(String login, String password){
        try {
            Class.forName(JDBC);
        }
        catch (ClassNotFoundException e){
            System.err.println("Driver ERROR");
            e.printStackTrace();
        }
        try{
            if(!this.checkLogin(login)){
                throw new IllegalArgumentException();
            }
            this.createConnect(login,password);
        }catch (IllegalArgumentException e){
            System.err.println("ZLY LOGIN : " + login);
        }
    }

    /**
     * Funkcja sprawdzająca, czy dany login istnieje w bazie danych. W tym celu łączy się z bazą za pomocą użytkownika,
     * który może jedynie wywołać funkcję sprawdzającą istnienie użytkownika.
     * @param login - login, na który chce się zalogować użytkownik
     */
    private boolean checkLogin(String login){
        Boolean result = false;
        if (this.dbms.equals("mysql")) {
            try {
                connect = DriverManager.getConnection(
                        "jdbc:" + this.dbms + "://" +
                                this.serverName +
                                ":" + this.portNumber.toString() +
                                "/" + this.dataBaseName,
                        "checkLogin",
                        ""
                );
                connect.setAutoCommit(false);
            }
            catch (SQLException e) {
                System.err.println("NO CONNECTION TO DATABASE");
                e.printStackTrace();
            }

            PreparedStatement state = null;
            try {
                String SQL = "SELECT checkUser(?)";
                state = connect.prepareStatement(SQL);
                state.setString(1,login);

                ResultSet rs = state.executeQuery();

                if(rs.next()){
                    result = rs.getBoolean(1);
                }
                else{
                    System.err.println("ERROR:: EXECUTE QUERY :: ERROR");
                }
            }
            catch (SQLException e){
                System.err.println("ERROR::LOGIN CHECK::ERROR");
                e.printStackTrace();
            }
            finally {
                try {
                    this.closeConnect();
                    if(state!=null){
                        state.close();
                    }
                }
                catch (SQLException e){
                    System.err.println("CLOSE STATEMENT");
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * Tworzenie polaczenia z baza danych
     */
    public void createConnect(String login, String password){
        if (this.dbms.equals("mysql")) {
            try {
                connect = DriverManager.getConnection(
                        "jdbc:" + this.dbms + "://" +
                                this.serverName +
                                ":" + this.portNumber.toString() + "/",
                        login,
                        password
                );
                System.out.println("Connected Succesfully on " + login);
                connect.setAutoCommit(false);
            }
            catch (SQLException e) {
                System.err.println("ZLE HASLO");
//                e.printStackTrace();
            }
        }
    }

    public void closeConnect(){
        try {
            if (this.connect != null){
                this.connect.close();
                this.connect = null;
            }
        }catch (SQLException e){
            System.err.println("MYSQL CLOSE ERROR");
            e.printStackTrace();
        }
    }
}
