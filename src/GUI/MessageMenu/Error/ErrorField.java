package GUI.MessageMenu.Error;

import GUI.Controller;
import GUI.Main;
import GUI.MessageMenu.MessageField;
import javafx.application.Platform;

/**
 * Created by lewin on 12/26/16.
 */
public class ErrorField extends MessageField{
    public ErrorField(Controller controller, String text) {
        super(controller, text);
        this.getStyleClass().add("ErrorField");
    }
    public static void error(String text){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                new ErrorField(Main.getController(),text);
            }
        });
    }

}
