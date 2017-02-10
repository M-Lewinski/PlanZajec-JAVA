package GUI;

import GUI.MessageMenu.Error.ErrorField;
import dataBase.SqlObject;
import dataBase.subjects.ZaplanowaneZajecie;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class Controller {
    private AnchorPane messageLayout;
    private VBox vbox;

    public void setStyleSheets(Scene scene){
        scene.getStylesheets().add("GUI/MessageMenu/Info/InfoFieldStyle.css");
        scene.getStylesheets().add("GUI/MessageMenu/Error/ErrorFieldStyle.css");
        scene.getStylesheets().add("GUI/MessageMenu/Warning/WarningFieldStyle.css");
        scene.getStylesheets().add("GUI/ScheduleMenu/text-area-background.css");
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

    public void addChoiceBoxContentZajecia(ChoiceBox<ZaplanowaneZajecie> choiceBox, List<ZaplanowaneZajecie> list){
        ObservableList<ZaplanowaneZajecie> obsList = FXCollections.observableArrayList(list);
//        obsList.add(0,"");
        choiceBox.setItems(obsList);
    }

    public void addChoiceBoxContentString(ChoiceBox<String> choiceBox, List<String> list){
        ObservableList<String> obsList = FXCollections.observableArrayList(list);
//        obsList.add(0,"");
        choiceBox.setItems(obsList);
    }
    public void addListenerChoiceBox(ChoiceBox<SqlObject> mainChoice, ChoiceBox<SqlObject> subChoice){
//        System.out.println("HEJ!");
        mainChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (mainChoice.getSelectionModel().getSelectedItem() == null){
                delete(subChoice);
                this.clearChoiceBox(subChoice);
                subChoice.setDisable(true);
            }
            else if (newValue != oldValue && newValue != null){
                try {
                    delete(subChoice);
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

    private void delete(ChoiceBox<SqlObject> subChoice) {
        List<SqlObject> oldList = subChoice.getItems();
        if(oldList!=null){
            for (SqlObject s:
                    oldList) {
                s.delete();
            }
        }
    }

    public void clearChoiceBox(ChoiceBox<SqlObject> choiceBox){
        choiceBox.getSelectionModel().clearSelection();
        choiceBox.getItems().clear();
    }

    public void onlyNumber(TextField textField, int rangeLeft, int rangeRight){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if(!textField.getText().equals("")){
                    int value = Integer.parseInt(textField.getText());
                    if (value > rangeRight){
                        value = rangeRight;
                    }
                    if (value < rangeLeft){
                        value = rangeLeft;
                    }
                    textField.setText(Integer.toString(value));
                }
            }
        });
    }


}

