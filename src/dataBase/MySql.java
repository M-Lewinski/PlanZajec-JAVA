package dataBase;

import java.sql.*;

/**
 * Created by lewin on 11/17/16.
 */
public class MySql {

    private Connection connect = null;
    /**
     * Informacje o połączeniu z bazą danych
     */
    private static MySql instance = null;
    private String dbms = "mysql";
    private String serverName = "127.0.0.1";
    private Integer portNumber = 3306;
    private String JDBC = "com.mysql.jdbc.Driver";
    private String checkLogin = "checkLogin";
    private String dataBaseName = "PlanZajec";

    private static String userLogin = null;
    private static String userPassword = null;


    private MySql(String login, String password) {
        try {
            Class.forName(JDBC);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver ERROR");
            e.printStackTrace();
        }
        try {
            if (!this.checkLogin(login)) {
                throw new IllegalArgumentException();
            }
            this.createConnect(login, password,"ZLE HASLO");
        } catch (IllegalArgumentException e) {
            System.err.println("ZLY LOGIN : " + login);
            this.closeConnect();
        }
    }

    /**
     * Funkcja sprawdzająca, czy dany login istnieje w bazie danych. W tym celu łączy się z bazą za pomocą użytkownika,
     * który może jedynie wywołać funkcję sprawdzającą istnienie użytkownika.
     *
     * @param login - login, na który chce się zalogować użytkownik
     */
    private boolean checkLogin(String login) {
        Boolean result = false;
        this.createConnect("checkLogin","","NO CONNECTION TO DATABASE");
        PreparedStatement state = null;
        try {
            String SQL = "SELECT checkUser(?)";
            state = connect.prepareStatement(SQL);
            state.setString(1, login);

            ResultSet rs = state.executeQuery();

            if (rs.next()) {
                result = rs.getBoolean(1);
            } else {
                System.err.println("ERROR:: EXECUTE QUERY :: ERROR");
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("ERROR::LOGIN CHECK::ERROR");
            e.printStackTrace();
        } finally {
            try {
                this.closeConnect();
                if (state != null) {
                    state.close();
                }
            } catch (SQLException e) {
                System.err.println("CLOSE STATEMENT");
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * Tworzenie polaczenia z baza danych
     */
    private void createConnect(String login, String password,String error) {
        if (this.dbms.equals("mysql")) {
            try {
                connect = DriverManager.getConnection(
                        "jdbc:" + this.dbms + "://" +
                                this.serverName +
                                ":" + this.portNumber.toString() +
                                "/" + this.dataBaseName,
                        login,
                        password
                );
                System.out.println("Connected Succesfully on " + login);
                connect.setAutoCommit(false);
            } catch (SQLException e) {
                System.err.println(error);
                this.closeConnect();
            }
        }
    }

    public void closeConnect() {
        try {
            if (this.connect != null) {
                this.connect.close();
                this.connect = null;
            }
        } catch (SQLException e) {
            System.err.println("MYSQL CLOSE ERROR");
            e.printStackTrace();
        }
    }

    public Connection getConnect() {
        return connect;
    }

    public static void setUser(String login,String password){
        MySql.userLogin = login;
        MySql.userPassword = password;
    }

    public static String getUserLogin() {
        return userLogin;
    }

    public static MySql getInstance() {
        if (instance == null){
            instance = new MySql(userLogin,userPassword);
            if (instance.getConnect() == null){
                instance = null;
            }
        }
        if(instance!=null){
            return instance;
        }
        return null;
    }
}
