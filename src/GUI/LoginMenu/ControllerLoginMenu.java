package GUI.LoginMenu;

import GUI.Controller;
import GUI.Main;
import GUI.MessageMenu.Error.ErrorField;
import GUI.MessageMenu.Info.InfoField;
import GUI.MessageMenu.MessageField;
import GUI.MessageMenu.Warning.WarningField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Created by lewin on 12/25/16.
 */
public class ControllerLoginMenu extends Controller {
    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonExit;
    @FXML
    private TextField fieldLogin;
    @FXML
    private PasswordField fieldPassword;
    @FXML
    private AnchorPane mainPane;

    public ControllerLoginMenu(){
    }

    @FXML
    public void initialize(){
        this.setMessageLayout(mainPane);
    }

}
