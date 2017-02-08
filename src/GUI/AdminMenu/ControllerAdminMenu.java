package GUI.AdminMenu;

import GUI.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;

import java.rmi.activation.ActivationID;

/**
 * Created by lewin on 1/1/17.
 */
public class ControllerAdminMenu extends Controller{
    @FXML
    public BorderPane students;
    @FXML
    public BorderPane proffesors;
    @FXML
    public BorderPane subjects;
    @FXML
    public BorderPane lectures;
    @FXML
    public BorderPane faculties;
    @FXML
    public BorderPane buildings;

    public BorderPane currentPane;

    private ToggleButton oldToggle;
    public void changePane(BorderPane newBorderPane){
        if (currentPane!=null){
            currentPane.setVisible(false);
        }
        newBorderPane.setVisible(true);
        currentPane = newBorderPane;
    }

    public void actionStudents(ActionEvent event) {
        event.consume();
        this.togglePane(students,event,oldToggle);
    }
    public void actionProffesors(ActionEvent event){
        event.consume();
        this.togglePane(proffesors,event,oldToggle);
    }
    public void actionSubjects(ActionEvent event){
        event.consume();
        this.togglePane(subjects,event,oldToggle);

    }
    public void actionLectures(ActionEvent event){
        event.consume();
        this.togglePane(lectures,event,oldToggle);
    }
    public void actionFaculties(ActionEvent event){
        event.consume();
        this.togglePane(faculties,event,oldToggle);
    }
    public void actionBuildings(ActionEvent event){
        event.consume();
        this.togglePane(buildings,event,oldToggle);
    }

    public void togglePane(BorderPane newPane, ActionEvent event,ToggleButton oldToggle){
        this.changePane(newPane);
        this.oldToggle = this.toggleHighlight(event,oldToggle);
    }
}
