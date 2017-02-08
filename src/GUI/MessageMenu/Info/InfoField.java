package GUI.MessageMenu.Info;

import GUI.Controller;
import GUI.Main;
import GUI.MessageMenu.Error.ErrorField;
import GUI.MessageMenu.MessageField;
import javafx.application.Platform;

/**
 * Created by lewin on 12/26/16.
 */
public class InfoField extends MessageField{
    public InfoField(Controller controller, String text) {
        super(controller, text);
        this.getStyleClass().add("InfoField");
    }

    public static void info(String text){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                new InfoField(Main.getController(),text);
            }
        });
    }

}
