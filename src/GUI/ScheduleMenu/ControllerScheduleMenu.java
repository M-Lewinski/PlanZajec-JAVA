package GUI.ScheduleMenu;

import GUI.Controller;
import GUI.Main;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 * Created by lewin on 12/26/16.
 */
public class ControllerScheduleMenu extends Controller{

    @FXML
    private AnchorPane mainPane;

    public ControllerScheduleMenu(){

    }
    @FXML
    public void initialize(){
        this.setMessageLayout(this.mainPane);
        if(Main.getRefresher()!=null){
            Main.getRefresher().createStatusLabel(this.mainPane);
        }
    }
}
