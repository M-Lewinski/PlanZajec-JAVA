package GUI.AdminMenu;

import GUI.Controller;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

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

    public void changePane(BorderPane newBorderPane){
        if (currentPane!=null){
            currentPane.setVisible(false);
        }
        newBorderPane.setVisible(true);
        currentPane = newBorderPane;
    }

    public void changeStudents() {
        this.changePane(students);
    }
    public void changeProffesors(){
        this.changePane(proffesors);
    }
    public void changeSubjects(){
        this.changePane(subjects);
    }
    public void changeLectures(){
        this.changePane(lectures);
    }
    public void changeFaculties(){
        this.changePane(faculties);
    }
    public void changeBuildings(){
        this.changePane(buildings);
    }
}
