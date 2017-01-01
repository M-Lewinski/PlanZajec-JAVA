package GUI.MessageMenu.Info;

import GUI.Controller;
import GUI.Main;
import GUI.MessageMenu.MessageField;

/**
 * Created by lewin on 12/26/16.
 */
public class InfoField extends MessageField{
    public InfoField(Controller controller, String text) {
        super(controller, text);
        this.getStyleClass().add("InfoField");
    }
}
