package GUI.MainMenu;

import GUI.Controller;
import GUI.Main;
import dataBase.MySql;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

/**
 * Created by lewin on 1/9/17.
 */
public class ControllerMenuBar extends Controller {

    @FXML
    public Menu schedulTab;
    @FXML
    public Menu adminTab;
    @FXML
    public Menu accountTab;
    @FXML
    public MenuItem settings;
    @FXML
    public MenuItem logout;
    @FXML
    public AnchorPane mainPane;


    public void goToScheduleMenu(){
        try {
            Main.changeScene("Schedule Menu", "ScheduleMenu/ScheduleMenu.fxml");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void goToAdminMenu(){
        try {
            Main.changeScene("Admin Menu", "AdminMenu/AdminMenu.fxml");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void goToSettingsMenu(){
        try {
            Main.changeScene("Settings", "SettingsMenu/SettingsMenu.fxml");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void goToLoginMenu(){
        try {
            Main.getRefresher().setThreadIsAlive(false);
            Main.getRefresher().getThread().join();
            MySql.setUser(null,null);
            MySql.getInstance().getConnect().close();
            MySql.clearInstance();
            Main.changeScene("Login Menu", "LoginMenu/LoginMenu.fxml");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(){
        this.adminTab.setVisible(false);
//        this.setMessageLayout(this.mainPane);
        if(Main.getRefresher()!=null){
            Main.getRefresher().createStatusLabel(this.mainPane);
        }
        if(MySql.getInstance().getStudent() == null && MySql.getInstance().getProwadzacy() == null){
            this.adminTab.setVisible(true);
        }
    }
}
