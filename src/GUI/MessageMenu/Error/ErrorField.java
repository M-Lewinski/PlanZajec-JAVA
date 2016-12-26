package GUI.MessageMenu.Error;

import GUI.Controller;
import GUI.MessageMenu.MessageField;

/**
 * Created by lewin on 12/26/16.
 */
public class ErrorField extends MessageField{
    public ErrorField(Controller controller, String text) {
        super(controller, text);
        this.getStyleClass().add("ErrorField");
    }
}
