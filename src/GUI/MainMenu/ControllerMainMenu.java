package GUI.MainMenu;

import GUI.Controller;
import GUI.Main;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 * Created by lewin on 12/26/16.
 */
public class ControllerMainMenu extends Controller{

    @FXML
    private AnchorPane mainPane;

    public ControllerMainMenu(){

    }
    @FXML
    public void initialize(){
        this.setMessageLayout(this.mainPane);
    }
}
