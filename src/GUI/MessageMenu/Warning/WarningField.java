package GUI.MessageMenu.Warning;

import GUI.Controller;
import GUI.MessageMenu.MessageField;

/**
 * Created by lewin on 12/26/16.
 */
public class WarningField extends MessageField{
    public WarningField(Controller controller, String text) {
        super(controller, text);
        this.getStyleClass().add("WarningField");
    }
}
