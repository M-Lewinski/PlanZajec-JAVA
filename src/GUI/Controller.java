package GUI;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class Controller {
    private AnchorPane messageLayout;
    private VBox vbox;

    public void setStyleSheets(Scene scene){
        scene.getStylesheets().add("GUI/MessageMenu/Info/InfoFieldStyle.css");
        scene.getStylesheets().add("GUI/MessageMenu/Error/ErrorFieldStyle.css");
        scene.getStylesheets().add("GUI/MessageMenu/Warning/WarningFieldStyle.css");
    }


    public Parent getMessageLayout() {
        return messageLayout;
    }

    public void setMessageLayout(AnchorPane messageLayout) {
        this.messageLayout = messageLayout;
        this.vbox = new VBox();
        this.vbox.setPrefSize(Region.USE_COMPUTED_SIZE,Region.USE_COMPUTED_SIZE);
        this.vbox.setMinSize(Region.USE_COMPUTED_SIZE,Region.USE_COMPUTED_SIZE);
        this.vbox.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        this.messageLayout.getChildren().add(this.vbox);
        AnchorPane.setBottomAnchor(this.vbox,0.0);
        AnchorPane.setLeftAnchor(this.vbox,0.0);
        AnchorPane.setRightAnchor(this.vbox,0.0);
    }

    public VBox getVbox() {
        return vbox;
    }

    public void setVbox(VBox vbox) {
        this.vbox = vbox;
    }
}

