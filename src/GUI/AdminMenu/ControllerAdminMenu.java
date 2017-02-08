package GUI.AdminMenu;

import GUI.Controller;
import GUI.MessageMenu.Error.ErrorField;
import GUI.MessageMenu.Info.InfoField;
import GUI.MessageMenu.Warning.WarningField;
import dataBase.MySql;
import dataBase.SqlObject;
import dataBase.actors.Prowadzacy;
import dataBase.actors.Student;
import dataBase.generator.Generator;
import dataBase.structure.Kierunek;
import dataBase.structure.Semestr;
import dataBase.structure.Wydzial;
import dataBase.subjects.Przedmiot;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private List<Prowadzacy> proffesorsList;
    private List<Student> studentsList;

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
    public TextField subjectSemester;
    @FXML
    public ColorPicker subjectColor;
    @FXML
    public Button subjectSave;
    @FXML
    public TableView subjectTable;


    @FXML
    public TextField professorLogin;
    @FXML
    public PasswordField professorPassword;
    @FXML
    public TextField professorName;
    @FXML
    public TextField professorSurname;
    @FXML
    public TextField professorTitle;
    @FXML
    public TextField proffesorAmount;
    @FXML
    public TableView proffesorTable;


    @FXML
    public ChoiceBox<SqlObject> studentFaculty;
    @FXML
    public ChoiceBox<SqlObject> studentCourse;
    @FXML
    public ChoiceBox<SqlObject> studentSemester;
    @FXML
    public TextField studentLogin;
    @FXML
    public PasswordField studentPassword;
    @FXML
    public TextField studentName;
    @FXML
    public TextField studentSurname;
    @FXML
    public TextField studentAmount;
    @FXML
    public TextField studentGroup;
    @FXML
    public TableView studentTable;


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
        this.setupProffesors();
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
        try {
            PreparedStatement stmt = sqlObject.addObjectToBase(connection);
            stmt.execute();
            connection.commit();
            InfoField.info("Inserting new object was successful");
        }
        catch (SQLException e){
//            if(save!=null){
                try {
                    connection.rollback();
                }
                catch (SQLException err){
                    err.printStackTrace();
                }
//            }
            ErrorField.error("Failure! Duplicate entry for Primary Key");
            e.printStackTrace();
        }
//        finally {
//            if(save!=null){
//                try {
//                    connection.releaseSavepoint(save);
//                }
//                catch (SQLException e){
//                    ErrorField.error("SAVEPOINT FAILURE");
//                }
//            }
//        }
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


        TableColumn<Prowadzacy,String> pLogin = new TableColumn("Name");
        TableColumn<Prowadzacy,String> pTitle = new TableColumn("Title");
        TableColumn<Prowadzacy,String> pName = new TableColumn("Name");
        TableColumn<Prowadzacy,String> pSurName = new TableColumn("Surname");
        pLogin.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getLogin()));
        pTitle.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getTytul()));
        pName.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getImie()));
        pSurName.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getNazwisko()));
        this.proffesorTable.getColumns().addAll(pLogin,pTitle,pName,pSurName);



        this.onlyNumber(this.proffesorAmount,0,30);
        this.onlyNumber(this.studentGroup,1,5);
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
        else {
            table.getSelectionModel().clearSelection();
            table.getItems().clear();
        }
    }

    public void deleteSubject(){
        Przedmiot przedmiot = (Przedmiot) this.subjectTable.getSelectionModel().getSelectedItem();
        if(przedmiot!=null){
            deleteSqlObject(przedmiot);
        }
        this.setupSubjects();
    }

    public void deleteSqlObject(SqlObject sqlObject){
        Connection connection = MySql.getInstance().getConnect();
        try {
            PreparedStatement stmt = sqlObject.deleteObjectFromBase(connection);
            connection.commit();
            InfoField.info("Deleting new object was successful");
        }
        catch (SQLException e){
                try {
                    connection.rollback();
                }
                catch (SQLException err){
                    err.printStackTrace();
                }
            e.printStackTrace();
        }
    }

    public void setupProffesors(){
        try {
            this.proffesorsList = Prowadzacy.getAllObjects();
            setupTable(this.proffesorTable,this.proffesorsList);
        }
        catch (SQLException e){
            ErrorField.error("TableView error");
        }
    }


    public void createProfesor(){
        String name = this.professorName.getText();
        String surname = this.professorSurname.getText();
        String title = this.professorTitle.getText();
        String login = this.professorLogin.getText();
        String password = this.professorPassword.getText();
        if (!name.equals("") && !surname.equals("") && !login.equals("") && !password.equals("")){
            Prowadzacy newProwadzacy= new Prowadzacy(login,name,surname,password);
            insertSqlObject(newProwadzacy);
        }
        else {
            WarningField.warning("There are fields which are empty!");
        }
        this.setupProffesors();
    }


    public void deleteProfesor(){
        Prowadzacy prowadzacy = (Prowadzacy) this.proffesorTable.getSelectionModel().getSelectedItem();
        if(prowadzacy!=null){
            deleteSqlObject(prowadzacy);
        }
        this.setupProffesors();
    }

    public void generateProfessors(){
        Generator generator = new Generator();
        if(!this.proffesorAmount.equals(""))
            generator.createSqlObjectsFromFiles(new Prowadzacy(),Integer.parseInt(this.proffesorAmount.getText()),new String[]{"NameList.txt","SurnameList.txt"});
        this.setupProffesors();
    }


    public void setupStudents(){
        try {
            this.studentsList= Student.getAllObjects();
            setupTable(this.studentTable,this.studentsList);
        }
        catch (SQLException e){
            ErrorField.error("TableView error");
        }
    }


    public void createStudent(){
        String login = this.studentLogin.getText();
        String password = this.studentPassword.getText();
        String name = this.studentName.getText();
        String surname = this.studentSurname.getText();
        Kierunek kierunek = (Kierunek) this.studentCourse.getSelectionModel().getSelectedItem();
        Semestr semestr = (Semestr) this.studentSemester.getSelectionModel().getSelectedItem();
        String temp = this.studentGroup.getText();
        int group = 0;
        if(!temp.equals("")){
            group = Integer.parseInt(temp);
        }
        if (group != 0 && !name.equals("") && !surname.equals("") && !login.equals("") && !password.equals("") && !this.studentSemester.getSelectionModel().isEmpty()){
            Student newStudent= new Student(login,name,surname,"",kierunek.getNazwa(),kierunek.getNazwa_wydzialu(),semestr.getNumer(),group);
            insertSqlObject(newStudent);
        }
        else {
            WarningField.warning("There are fields which are empty!");
        }
        this.setupProffesors();
    }


    public void deleteProfesor(){
        Prowadzacy prowadzacy = (Prowadzacy) this.proffesorTable.getSelectionModel().getSelectedItem();
        if(prowadzacy!=null){
            deleteSqlObject(prowadzacy);
        }
        this.setupProffesors();
    }

    public void generateProfessors(){
        Generator generator = new Generator();
        if(!this.proffesorAmount.equals(""))
            generator.createSqlObjectsFromFiles(new Prowadzacy(),Integer.parseInt(this.proffesorAmount.getText()),new String[]{"NameList.txt","SurnameList.txt"});
        this.setupProffesors();
    }


}
