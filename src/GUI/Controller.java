package GUI;

import GUI.MessageMenu.Error.ErrorField;
import dataBase.SqlObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
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

    public void addChoiceBoxContent(ChoiceBox<SqlObject> choiceBox, List<? extends SqlObject> list){
        ObservableList<SqlObject> obsList = FXCollections.observableArrayList(list);
//        obsList.add(0,"");
        choiceBox.setItems(obsList);
    }
    public void addListenerChoiceBox(ChoiceBox<SqlObject> mainChoice, ChoiceBox<SqlObject> subChoice){
//        System.out.println("HEJ!");
        mainChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (mainChoice.getSelectionModel().getSelectedItem() == null){
                this.clearChoiceBox(subChoice);
                subChoice.setDisable(true);
            }
            else if (newValue != oldValue && newValue != null){
                try {
                    this.clearChoiceBox(subChoice);
                    List<? extends SqlObject> list = mainChoice.getValue().getRelatedObjects();
                    this.addChoiceBoxContent(subChoice,list);
                    subChoice.setDisable(false);
                }
                catch (SQLException e){
                    ErrorField.error("Failure while loading related objects");
                    e.printStackTrace();
                }
            }
        });
    }

    public void clearChoiceBox(ChoiceBox<SqlObject> choiceBox){
        choiceBox.getSelectionModel().clearSelection();
        choiceBox.getItems().clear();
    }
//    public void facultyCellFactory(ChoiceBox choiceBox){
//    }
}

