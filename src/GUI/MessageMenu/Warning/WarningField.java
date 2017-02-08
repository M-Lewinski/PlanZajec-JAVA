package GUI.MessageMenu.Warning;

import GUI.Controller;
import GUI.Main;
import GUI.MessageMenu.Error.ErrorField;
import GUI.MessageMenu.MessageField;
import javafx.application.Platform;

/**
 * Created by lewin on 12/26/16.
 */
public class WarningField extends MessageField{
    public WarningField(Controller controller, String text) {
        super(controller, text);
        this.getStyleClass().add("WarningField");
    }
    public static void warning(String text){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                new WarningField(Main.getController(),text);
            }
        });
    }

}
