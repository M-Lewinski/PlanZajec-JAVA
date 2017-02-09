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
import dataBase.subjects.Sala;
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
    private List<Sala> roomsList;
    private List<Wydzial> facultiesList;
    private List<Kierunek> coursesList;

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
    @FXML
    public BorderPane currentPane;

    @FXML
    public AnchorPane mainPane;

    @FXML
    public ChoiceBox<SqlObject> subjectFaculty;
    @FXML
    public ChoiceBox<SqlObject> subjectCourse;
    @FXML
    public ChoiceBox<SqlObject> subjectSemester;

    @FXML
    public TextField subjectName;
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

    @FXML
    public TextField buildingName;
    @FXML
    public TextField roomName;
    @FXML
    public TextField roomSpots;
    @FXML
    public TableView roomsTable;


    @FXML
    public ChoiceBox<SqlObject> coursesFaculty;
    @FXML
    public TextField facultyName;
    @FXML
    public TextField courseName;
    @FXML
    public TableView facultiesTable;
    @FXML
    public TableView coursesTable;


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
        this.clearChoiceBox(this.studentFaculty);
        try {
            this.addChoiceBoxContent(this.studentFaculty,Wydzial.getAllObjects());
            this.setupStudents();
        }
        catch (SQLException e){
            ErrorField.error("Setup error!");
        }
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
        this.clearChoiceBox(coursesFaculty);
        try {
            this.addChoiceBoxContent(this.coursesFaculty,Wydzial.getAllObjects());
            this.setupFaculties();
            this.setupCourses();
        }
        catch (SQLException e){
            ErrorField.error("Setup error!");
        }
    }
    public void actionBuildings(ActionEvent event){
        event.consume();
        this.togglePane(buildings,event,oldToggle);
        this.setupRooms();
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
        Semestr semestr = (Semestr) this.subjectSemester.getValue();
        String name = this.subjectName.getText();
        Color c = this.subjectColor.getValue();
        if (!this.subjectSemester.getSelectionModel().isEmpty() && !name.equals("") && c != null){
            Przedmiot newPrzedmiot = new Przedmiot(name,kierunek.getNazwa(),kierunek.getNazwa_wydzialu(),semestr.getNumer(),c);
            insertSqlObject(newPrzedmiot);
        }
        else {
            WarningField.warning("There are fields which are empty!");
        }
        this.setupSubjects();
    }



    public void setupPanes(){
        this.addListenerChoiceBox(this.subjectFaculty,this.subjectCourse);
        this.addListenerChoiceBox(this.subjectCourse,this.subjectSemester);
        TableColumn<Przedmiot,String> sName = new TableColumn("Name");
        TableColumn<Przedmiot,String> sCourse = new TableColumn("Course");
        TableColumn<Przedmiot,String> sFaculty = new TableColumn("Faculty");
        TableColumn<Przedmiot,String> sSemester = new TableColumn("Semester");
        TableColumn sColor = new TableColumn("Color");
        TableColumn<Przedmiot,String> sRed = new TableColumn("Red");
        TableColumn<Przedmiot,String> sGreen = new TableColumn("Green");
        TableColumn<Przedmiot,String> sBlue = new TableColumn("Blue");
        sColor.getColumns().addAll(sRed,sBlue,sGreen);
        sName.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getNazwa()));
        sCourse.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getKierunek()));
        sFaculty.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getWydzial()));
        sSemester.setCellValueFactory(row -> new SimpleStringProperty((Integer.toString(row.getValue().getSemester()))));


        sRed.setCellValueFactory(row -> new SimpleStringProperty(Integer.toString(row.getValue().getRed())));
        sGreen.setCellValueFactory(row -> new SimpleStringProperty(Integer.toString(row.getValue().getGreen())));
        sBlue.setCellValueFactory(row -> new SimpleStringProperty(Integer.toString(row.getValue().getBlue())));
        this.subjectTable.getColumns().addAll(sName,sCourse,sFaculty,sSemester,sColor);


        TableColumn<Prowadzacy,String> pLogin = new TableColumn("Login");
        TableColumn<Prowadzacy,String> pTitle = new TableColumn("Title");
        TableColumn<Prowadzacy,String> pName = new TableColumn("Name");
        TableColumn<Prowadzacy,String> pSurName = new TableColumn("Surname");
        pLogin.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getLogin()));
        pTitle.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getTytul()));
        pName.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getImie()));
        pSurName.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getNazwisko()));
        this.proffesorTable.getColumns().addAll(pLogin,pTitle,pName,pSurName);
        this.onlyNumber(this.proffesorAmount,0,30);



        this.addListenerChoiceBox(this.studentFaculty,this.studentCourse);
        this.addListenerChoiceBox(this.studentCourse,this.studentSemester);
        TableColumn<Student,String> stLogin = new TableColumn("Login");
        TableColumn<Student,String> stIndex = new TableColumn("Index");
        TableColumn<Student,String> stName = new TableColumn("Name");
        TableColumn<Student,String> stSurName = new TableColumn("Surname");
        TableColumn<Student,String> stCourse= new TableColumn("Course");
        TableColumn<Student,String> stFaculty = new TableColumn("Faculty");
        TableColumn<Student,String> stSemester = new TableColumn("Semester");
        TableColumn<Student,String> stGroup = new TableColumn("Group");
        stLogin.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getLogin()));
        stIndex.setCellValueFactory(row -> new SimpleStringProperty(Integer.toString(row.getValue().getIndeks())));
        stName.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getImie()));
        stSurName.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getNazwisko()));
        stCourse.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getKierunek()));
        stFaculty.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getWydzial()));
        stSemester.setCellValueFactory(row -> new SimpleStringProperty(Integer.toString(row.getValue().getSemestr())));
        stGroup.setCellValueFactory(row -> new SimpleStringProperty(Integer.toString(row.getValue().getGrupa())));
        this.studentTable.getColumns().addAll(stLogin,stIndex,stName,stSurName,stCourse,stFaculty,stSemester,stGroup);

        this.onlyNumber(this.studentGroup,1,5);
        this.onlyNumber(this.studentAmount,0,30);

        TableColumn<Sala,String> bName= new TableColumn("Building");
        TableColumn<Sala,String> rName= new TableColumn("Room");
        TableColumn<Sala,String> rSpots= new TableColumn("Number of spots");
        bName.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getBudynek()));
        rName.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getSala()));
        rSpots.setCellValueFactory(row -> new SimpleStringProperty(Integer.toString(row.getValue().getLiczba_miejsc())));
                    this.roomsTable.getColumns().addAll(bName,rName,rSpots);

        this.onlyNumber(this.roomSpots,1,300);

        TableColumn<Wydzial,String> fName= new TableColumn("Name");
        fName.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getNazwa()));
        this.facultiesTable.getColumns().addAll(fName);

        TableColumn<Kierunek,String> cName = new TableColumn("Name");
        TableColumn<Kierunek,String> cFaculty = new TableColumn("Faculty name");
        cName.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getNazwa()));
        cFaculty.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getNazwa_wydzialu()));
        this.coursesTable.getColumns().addAll(cName,cFaculty);
    }

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
            stmt.execute();
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
        this.setupStudents();
    }


    public void deleteStudent(){
        Student student = (Student) this.studentTable.getSelectionModel().getSelectedItem();
        if(student!=null){
            deleteSqlObject(student);
        }
        this.setupStudents();
    }

    public void generateStudents(){
        Generator generator = new Generator();
        if(!this.studentAmount.equals(""))
            generator.createSqlObjectsFromFiles(new Student(),Integer.parseInt(this.studentAmount.getText()),new String[]{"NameList.txt","SurnameList.txt"});
        this.setupStudents();
    }


    public void setupRooms(){
        try {
            this.roomsList = Sala.getAllObjects();
            setupTable(this.roomsTable,this.roomsList);
        }
        catch (SQLException e){
            ErrorField.error("TableView error");
        }
    }


    public void createRoom(){
        String name = this.roomName.getText();
        String building = this.buildingName.getText();
        String temp = this.roomSpots.getText();
        int spots = 0;
        if (!temp.equals("")){
            spots = Integer.parseInt(temp);
        }
        if (!name.equals("") && !building.equals("") && spots != 0){
            Sala newSala = new Sala(name,building,spots);
            insertSqlObject(newSala);
        }
        else {
            WarningField.warning("There are fields which are empty!");
        }
        this.setupRooms();
    }


    public void deleteRoom(){
        Sala sala = (Sala) this.roomsTable.getSelectionModel().getSelectedItem();
        if(sala!=null){
            deleteSqlObject(sala);
        }
        this.setupRooms();
    }

    public void setupFaculties(){
        try {
            this.facultiesList = Wydzial.getAllObjects();
            setupTable(this.facultiesTable,this.facultiesList);
        }
        catch (SQLException e){
            ErrorField.error("TableView error");
        }
    }


    public void createFaculty(){
        String name = this.facultyName.getText();
        if (!name.equals("")){
            Wydzial newWydzial = new Wydzial(name);
            insertSqlObject(newWydzial);
        }
        else {
            WarningField.warning("There are fields which are empty!");
        }
        this.setupFaculties();
        try {
            this.addChoiceBoxContent(this.coursesFaculty,Wydzial.getAllObjects());
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }


    public void deleteFaculty(){
        Wydzial wydzial = (Wydzial) this.facultiesTable.getSelectionModel().getSelectedItem();
        if(wydzial!=null){
            deleteSqlObject(wydzial);
        }
        this.setupFaculties();
        this.setupCourses();
        try {
            this.addChoiceBoxContent(this.coursesFaculty,Wydzial.getAllObjects());
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }



    public void setupCourses(){
        try {
            this.coursesList = Kierunek.getAllObjects();
            setupTable(this.coursesTable,this.coursesList);
        }
        catch (SQLException e){
            ErrorField.error("TableView error");
        }
    }


    public void createCourses(){
        String name = this.courseName.getText();
        Wydzial wydzial = (Wydzial) this.coursesFaculty.getSelectionModel().getSelectedItem();
        if (!name.equals("") && !this.coursesFaculty.getSelectionModel().isEmpty()){
            Kierunek newKierunek = new Kierunek(name,wydzial.getNazwa());
            insertSqlObject(newKierunek);
        }
        else {
            WarningField.warning("There are fields which are empty!");
        }
        this.setupCourses();
    }


    public void deleteCourses(){
        Kierunek kierunek = (Kierunek) this.coursesTable.getSelectionModel().getSelectedItem();
        if(kierunek!=null){
            deleteSqlObject(kierunek);
        }
        this.setupCourses();
    }

}
