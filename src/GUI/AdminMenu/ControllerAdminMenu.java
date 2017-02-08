package GUI.AdminMenu;

import GUI.Controller;
import GUI.MessageMenu.Error.ErrorField;
import GUI.MessageMenu.Info.InfoField;
import GUI.MessageMenu.Warning.WarningField;
import dataBase.MySql;
import dataBase.SqlObject;
import dataBase.structure.Kierunek;
import dataBase.structure.Wydzial;
import dataBase.subjects.Przedmiot;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.sql.*;
import java.util.List;

/**
 * Created by lewin on 1/1/17.
 */
public class ControllerAdminMenu extends Controller{

    private List<Przedmiot> subjectsList;

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


    public AnchorPane mainPane;

    @FXML
    public ChoiceBox<SqlObject> subjectFaculty;
    @FXML
    public ChoiceBox<SqlObject> subjectCourse;
    @FXML
    public TextField subjectName;
    @FXML
    public ColorPicker subjectColor;
    @FXML
    public Button subjectSave;
    @FXML
    public TableView subjectTable;

    private ToggleButton oldToggle;
    public void changePane(BorderPane newBorderPane){
        if (currentPane!=null){
            currentPane.setVisible(false);
        }
        newBorderPane.setVisible(true);
        currentPane = newBorderPane;
    }

    @FXML
    public void initialize(){
        this.setMessageLayout(mainPane);
        this.setupPanes();
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
        this.clearChoiceBox(subjectFaculty);
        try {
            this.addChoiceBoxContent(this.subjectFaculty,Wydzial.getAllObjects());
            this.setupSubjects();
        }
        catch (SQLException e){
            ErrorField.error("Setup error!");
//            e.printStackTrace();
        }
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



    private void insertSqlObject(SqlObject sqlObject) {
        Connection connection = MySql.getInstance().getConnect();
        Savepoint save = null;
        try {
            save = connection.setSavepoint("INSERT");
            PreparedStatement lock = connection.prepareStatement("LOCK TABLES Przedmioty WRITE");
            lock.execute();
            PreparedStatement stmt = sqlObject.addObjectToBase(connection);
            stmt.execute();
            PreparedStatement unlock = connection.prepareStatement("UNLOCK TABLES");
            unlock.execute();
            connection.commit();
            InfoField.info("Inserting new subject was successful");
        }
        catch (SQLException e){
            if(save!=null){
                try {
                    connection.rollback(save);
                }
                catch (SQLException err){
                    err.printStackTrace();
                }
            }
            ErrorField.error("Failure! Duplicate entry for Primary Key");
        }
        finally {
            if(save!=null){
                try {
                    connection.releaseSavepoint(save);
                }
                catch (SQLException e){
                    ErrorField.error("SAVEPOINT FAILURE");
                }
            }
        }
    }

    public void createSubject(){
        Kierunek kierunek = (Kierunek) this.subjectCourse.getValue();
        String name = this.subjectName.getText();
        Color c = this.subjectColor.getValue();
        if (!this.subjectCourse.getSelectionModel().isEmpty() && !name.equals("") && c != null){
            Przedmiot newPrzedmiot = new Przedmiot(name,kierunek.getNazwa(),kierunek.getNazwa_wydzialu(),c);
            insertSqlObject(newPrzedmiot);
        }
        else {
            WarningField.warning("There are fields which are empty!");
        }
        this.setupSubjects();
    }



    public void setupPanes(){
        this.addListenerChoiceBox(this.subjectFaculty,this.subjectCourse);
        TableColumn<Przedmiot,String> sName = new TableColumn("Name");
        TableColumn<Przedmiot,String> sCourse = new TableColumn("Course");
        TableColumn<Przedmiot,String> sFaculty = new TableColumn("Faculty");
        TableColumn sColor = new TableColumn("Color");
        TableColumn<Przedmiot,String> sRed = new TableColumn("Red");
        TableColumn<Przedmiot,String> sGreen = new TableColumn("Green");
        TableColumn<Przedmiot,String> sBlue = new TableColumn("Blue");
        sColor.getColumns().addAll(sRed,sBlue,sGreen);
        sName.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getNazwa()));
        sCourse.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getKierunek()));
        sFaculty.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getWydzial()));

        sRed.setCellValueFactory(row -> new SimpleStringProperty(Integer.toString(row.getValue().getRed())));
        sGreen.setCellValueFactory(row -> new SimpleStringProperty(Integer.toString(row.getValue().getGreen())));
        sBlue.setCellValueFactory(row -> new SimpleStringProperty(Integer.toString(row.getValue().getBlue())));
        this.subjectTable.getColumns().addAll(sName,sCourse,sFaculty,sColor);
    };

    public void setupSubjects(){
        try {
            this.subjectsList = Przedmiot.getAllObjects();
            setupTable(this.subjectTable,this.subjectsList);
        }
        catch (SQLException e){
            ErrorField.error("TableView error");
        }
    }

    public <T extends SqlObject> void setupTable(TableView<T> table, List<T> list){
        ObservableList<T> data = FXCollections.observableArrayList(list);
        if(!data .isEmpty()){
            table.setItems(data);
        }
    }
}
