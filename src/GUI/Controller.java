package GUI;

import dataBase.SqlObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;

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

    public ToggleButton toggleHighlight(Event event,ToggleButton oldToggle){
        if (oldToggle!=null){
            oldToggle.setOpacity(0.5);
        }
        ToggleButton newToggle = (ToggleButton) event.getSource();
        newToggle.setOpacity(1.0);
        return newToggle;
    }

    public void addChoiceBoxContent(ChoiceBox choiceBox, List<String> list){
        ObservableList<String> obsList = FXCollections.observableArrayList(list);
//        obsList.add(0,"");
        choiceBox.setItems(obsList);
    }
    public void addListenerChoiceBox(ComboBox<SqlObject> mainChoice, ComboBox<SqlObject> subChoice){
//        System.out.println("HEJ!");
        mainChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (mainChoice.getSelectionModel().getSelectedItem() == null){
                subChoice.getSelectionModel().clearSelection();
                subChoice.getItems().clear();
                subChoice.setDisable(true);
            }
            else if (newValue != oldValue && newValue != null){
                subChoice.getSelectionModel().clearSelection();
                subChoice.getItems().clear();
                subChoice.setDisable(false);
            }
        });
    }

    public void facultyCellFactory(ChoiceBox choiceBox){
    }
}

