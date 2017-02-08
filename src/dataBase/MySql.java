package dataBase;

import GUI.Main;
import GUI.MessageMenu.Error.ErrorField;
import com.mysql.jdbc.authentication.MysqlClearPasswordPlugin;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.sql.*;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

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
    private Boolean showErrors = true;

    private static String userLogin = null;
    private static String userPassword = null;

    private MySql(String login, String password) {
        try {
            Class.forName(this.JDBC);
            DriverManager.setLoginTimeout(300);
        } catch (ClassNotFoundException e) {
            if(this.showErrors){
                System.err.println("Driver ERROR");
                e.printStackTrace();
            }
        }
        try {
            if (!this.checkLogin(login)) {
                if(this.showErrors){
                    ErrorField.error("Wrong login: " + login + "\nYou should check, if the login you typed is correct.");
                }
            }
            else {
                try {
                    this.createConnect(login, password);
                } catch (SQLException e) {
                    if (this.showErrors) {
                        ErrorField.error("Wrong password: " + password + "\nYou should check, if the password you typed is correct");
                    }
                }
            }
        } catch (SQLException e) {
            if(this.showErrors){
                ErrorField.error("NO CONNECTION TO DATABASE:\nCheck the connection with database.");
            }
        }
    }

    /**
     * Funkcja sprawdzająca, czy dany login istnieje w bazie danych. W tym celu łączy się z bazą za pomocą użytkownika,
     * który może jedynie wywołać funkcję sprawdzającą istnienie użytkownika.
     *
     * @param login - login, na który chce się zalogować użytkownik
     */
    private boolean checkLogin(String login) throws SQLException {
        Boolean result = false;
        try {
            this.createConnect("checkLogin","");
        }catch (SQLException e){
            throw e;
        }
        PreparedStatement state = null;
        try {
            String SQL = "SELECT checkUser(?)";
            state = this.connect.prepareStatement(SQL);
            state.setString(1, login);

            ResultSet rs = state.executeQuery();

            if (rs.next()) {
                result = rs.getBoolean(1);
            } else {
                if(this.showErrors){
                    System.err.println("ERROR:: EXECUTE QUERY :: ERROR");
                }
            }
            rs.close();
        } catch (SQLException e) {
            if(this.showErrors){
                System.err.println("ERROR::LOGIN CHECK::ERROR");
                e.printStackTrace();
            }
        } finally {
            try {
                this.closeConnect();
                if (state != null) {
                    state.close();
                }
            } catch (SQLException e) {
                if(this.showErrors){
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
    private void createConnect(String login, String password) throws SQLException {
        if (this.dbms.equals("mysql")) {
            try {
                this.connect = DriverManager.getConnection(
                        "jdbc:" + this.dbms + "://" +
                                this.serverName +
                                ":" + this.portNumber.toString() +
                                "/" + this.dataBaseName,
                        login,
                        password
                );
                System.out.println("Connected Succesfully on " + login);
                this.connect.setAutoCommit(false);
                this.connect.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            } catch (SQLException e) {
                if(this.showErrors){
                    this.closeConnect();
                    throw e;
                }
            }
        }
    }

    public boolean refreshConnection(){
        try {
            this.createConnect(userLogin,userPassword);
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public void closeConnect() {
        try {
            if (this.connect != null) {
                if(!this.connect.isClosed()){
                    this.connect.close();
                }
                this.connect = null;
            }
        } catch (SQLException e) {
            if(this.showErrors){
                System.err.println("MYSQL CLOSE ERROR");
                e.printStackTrace();
            }
        }
    }

    public synchronized Connection getConnect() {
        return this.connect;
    }

    public static synchronized void setUser(String login,String password){
        MySql.userLogin = login;
        MySql.userPassword = password;
    }

    public static String getUserLogin() {
        return userLogin;
    }

    public static synchronized MySql getInstance() {
        if (instance == null){
            instance = new MySql(userLogin,userPassword);
            if (instance.getConnect() == null){
                instance = null;
            }
        }
        return instance;
    }

    public void setShowErrors(Boolean showErrors) {
        this.showErrors = showErrors;
    }

    public class Refresher implements Runnable{

        private boolean threadIsAlive = true;
        private BooleanProperty valid = new SimpleBooleanProperty(true);
        private Thread thread;
        private Label label;
        private boolean refresh(){
//            System.out.println("TEST");
            return MySql.getInstance().refreshConnection();
        }
        private boolean ping(){
            try {
                return MySql.getInstance().getConnect().isValid(5);
            }catch (SQLException e){
                e.printStackTrace();
                return false;
            }
        }

        public Refresher(){
            this.thread = new Thread(this);
            thread.start();
        }

        @Override
        public void run() {
            while (this.threadIsAlive){
                boolean newValid;
                if(this.valid.getValue())
                    newValid = this.ping();
                else {
                    newValid = this.refresh();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        valid.setValue(newValid);
                    }
                });
                try {
                    TimeUnit.SECONDS.sleep(3);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

        public void setThreadIsAlive(boolean threadIsAlive) {
            this.threadIsAlive = threadIsAlive;
        }
        public Thread getThread() {
            return thread;
        }

        public void createStatusLabel(AnchorPane anchorPane){
            this.label = new Label();
            label.setText("Connection established!");
            label.setStyle("-fx-text-fill: #3c763d");
            AnchorPane.setTopAnchor(label,0.0);
            this.label.setPrefHeight(29.0);
            AnchorPane.setRightAnchor(label,10.0);
            this.valid.addListener((observable, oldValue, newValue) -> {
                if(newValue){
                    label.setText("Connection established!");
                    label.setStyle("-fx-text-fill: #3c763d");
                }
                else {
                    label.setText("Connection problem!");
                    label.setStyle("-fx-text-fill: #a94442");
                }
            });
            this.label.toFront();
            anchorPane.getChildren().add(this.label);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    valid.setValue(valid.getValue());
                }
            });
        }
    }

    public void createRefresher(){
        Refresher refresher = new Refresher();
        Main.setRefresher(refresher);
    }

}