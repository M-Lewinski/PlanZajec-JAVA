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
import dataBase.subjects.Zajecie;
import dataBase.subjects.ZaplanowaneZajecie;
import dataBase.subjects.studentInfo.Miejsce;
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
import java.time.LocalDate;
import java.util.ArrayList;
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
    private List<Zajecie> classesList;

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


    @FXML
    public ChoiceBox<SqlObject> classesFaculty;
    @FXML
    public ChoiceBox<SqlObject> classesCourses;
    @FXML
    public ChoiceBox<SqlObject> classesSemester;
    @FXML
    public ChoiceBox<String> classesType;
    @FXML
    public ChoiceBox<String> classesDay;
    @FXML
    public ChoiceBox<String> classesHour;
    @FXML
    public ChoiceBox<String> classesWeek;
    @FXML
    public ChoiceBox<String> classesYear;
    @FXML
    public ChoiceBox<String> classesNumberClasses;
    @FXML
    public ChoiceBox<SqlObject> classesRoom;
    @FXML
    public ChoiceBox<SqlObject> classesProfessor;
    @FXML
    public ChoiceBox<SqlObject> classesSubject;
    @FXML
    public TextField classesGroup;
    @FXML
    public TextField classesSubGroup;
    @FXML
    public TableView classesTable;
    @FXML
    public DatePicker classesDate;

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
        this.clearChoiceBox(classesFaculty);
        this.clearChoiceBox(classesRoom);
        this.clearChoiceBox(classesProfessor);
        try {
            this.addChoiceBoxContent(this.classesFaculty,Wydzial.getAllObjects());
            this.addChoiceBoxContent(this.classesRoom,Sala.getAllObjects());
            this.addChoiceBoxContent(this.classesProfessor,Prowadzacy.getAllObjects());
        }
        catch (SQLException e){
            ErrorField.error("Setup error!");
        }
        this.setupClasses();
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



    boolean showMessage = true;
    private void insertSqlObject(SqlObject sqlObject) {
        Connection connection = MySql.getInstance().getConnect();
        try {
            PreparedStatement stmt = sqlObject.addObjectToBase(connection);
            stmt.execute();
            connection.commit();
            if(showMessage){
                InfoField.info("Inserting new object was successful");
            }
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
            if(showMessage){
                ErrorField.error("Failure! Duplicate entry for Primary Key");
            }
            e.printStackTrace();
        }
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


        TableColumn<Zajecie,String> zId = new TableColumn("Id");
        TableColumn<Zajecie,String> zYear = new TableColumn("Year");
        TableColumn<Zajecie,String> zSubject= new TableColumn("Subject");
        TableColumn<Zajecie,String> zProfessor= new TableColumn("Professor");
        TableColumn<Zajecie,String> zDay = new TableColumn("Day");
        TableColumn<Zajecie,String> zHour = new TableColumn("Hour");
        TableColumn<Zajecie,String> zWeek = new TableColumn("Week");
        TableColumn<Zajecie,String> zRoom = new TableColumn("Room");
        TableColumn<Zajecie,String> zGroup = new TableColumn("Group");
        TableColumn<Zajecie,String> zSubGroup= new TableColumn("SubGroup");

        zId.setCellValueFactory(row -> new SimpleStringProperty(Integer.toString(row.getValue().getId())));
        zYear.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getRocznik()));
        zSubject.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getPrzedmiot()));
        zProfessor.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getProfessor().getImie() + " " + row.getValue().getProfessor().getNazwisko()));
        zDay.setCellValueFactory(row -> new SimpleStringProperty(Integer.toString(row.getValue().getDzien())));
        zHour.setCellValueFactory(row -> new SimpleStringProperty(Integer.toString(row.getValue().getGodzina())));
        zWeek.setCellValueFactory(row -> new SimpleStringProperty(Integer.toString(row.getValue().getTydzien())));
        zRoom.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getSala() + " " + row.getValue().getBudynek()));
        zGroup.setCellValueFactory(row -> new SimpleStringProperty(Integer.toString(row.getValue().getGrupa())));
        zSubGroup.setCellValueFactory(row -> new SimpleStringProperty(Integer.toString(row.getValue().getPodgrupa())));
        this.classesTable.getColumns().addAll(zId,zYear,zSubject,zProfessor,zDay,zHour,zWeek,zRoom,zGroup,zSubGroup);
        this.onlyNumber(this.classesGroup,0,5);
        this.onlyNumber(this.classesSubGroup,0,2);


        this.addListenerChoiceBox(this.classesFaculty,this.classesCourses);
        this.addListenerChoiceBox(this.classesCourses,this.classesSemester);
        this.addListenerChoiceBox(this.classesSemester,this.classesSubject);
        List<String> types = new ArrayList<String>();
        types.add("Lecture");
        types.add("Laboratory");
        types.add("Practice class");
        this.addChoiceBoxContentString(this.classesType,types);
        List<String> days = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            days.add(Zajecie.convertDay(i));
        }
        this.addChoiceBoxContentString(this.classesDay,days);
        List<String> hours = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            hours.add(Zajecie.convertHour(i));
        }
        this.addChoiceBoxContentString(this.classesHour,hours);
        List<String> weeks = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            weeks.add(Zajecie.convertWeek(i));
        }
        this.addChoiceBoxContentString(this.classesWeek,weeks);
        List<String> years = new ArrayList<>();
        years.add("2016/2017");
        this.addChoiceBoxContentString(this.classesYear,years);
        List<String> numbers =  new ArrayList<>();
        numbers.add("15");
        numbers.add("30");
        this.addChoiceBoxContentString(this.classesNumberClasses,numbers);

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
            if(showMessage){
                InfoField.info("Deleting new object was successful");
            }
        }
        catch (SQLException e){
            ErrorField.error("Deleting new object was unsuccessful");
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
            Student newStudent= new Student(login,name,surname,password,kierunek.getNazwa(),kierunek.getNazwa_wydzialu(),semestr.getNumer(),group);
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



    public void setupClasses(){
        try {
            if(this.classesList!=null){
                for (Zajecie z:
                        this.classesList) {
                    z.delete();
                }
            }
            this.classesList = Zajecie.getAllObjects();
            setupTable(this.classesTable,this.classesList);
        }
        catch (SQLException e){
            ErrorField.error("TableView error");
        }
    }


    public void createClass(){
        Wydzial wydzial = (Wydzial) this.classesFaculty.getSelectionModel().getSelectedItem();
        Kierunek kierunek= (Kierunek) this.classesCourses.getSelectionModel().getSelectedItem();
        Semestr semestr = (Semestr) this.classesSemester.getSelectionModel().getSelectedItem();
        Przedmiot przedmiot = (Przedmiot) this.classesSubject.getSelectionModel().getSelectedItem();
        String typ = this.classesType.getSelectionModel().getSelectedItem();
        String dzien = this.classesDay.getSelectionModel().getSelectedItem();
        String godzina = this.classesHour.getSelectionModel().getSelectedItem();
        String tydzien = this.classesWeek.getSelectionModel().getSelectedItem();
        String rocznik = this.classesYear.getSelectionModel().getSelectedItem();
        String temp = this.classesGroup.getText();
        String temp2 = this.classesSubGroup.getText();
        int grupa = -1;
        if(!temp.equals("")){
            grupa = Integer.parseInt(temp);
        }
        int podGrupa = -1;
        if(!temp2.equals("")){
            podGrupa = Integer.parseInt(temp2);
        }
        Prowadzacy prowadzacy = (Prowadzacy) this.classesProfessor.getSelectionModel().getSelectedItem();
        Sala sala = (Sala) this.classesRoom.getSelectionModel().getSelectedItem();
        LocalDate date = null;
        if(this.classesDate.getValue()!=null){
            date = this.classesDate.getValue();
        }
        String temp3 = this.classesNumberClasses.getSelectionModel().getSelectedItem();
        int numberClasses = -1;
        if(temp3 !=null){
            if(!temp3.equals("")){
                numberClasses = Integer.parseInt(temp3);
            }
        }
        if (!this.classesFaculty.getSelectionModel().isEmpty() &&
                !this.classesCourses.getSelectionModel().isEmpty() &&
                !this.classesSemester.getSelectionModel().isEmpty() &&
                !this.classesSubject.getSelectionModel().isEmpty() &&
                !this.classesProfessor.getSelectionModel().isEmpty() &&
                !this.classesRoom.getSelectionModel().isEmpty() &&
                grupa >= 0 &&
                podGrupa >= 0 &&
                numberClasses >= 0 &&
                date != null &&
                !typ.equals("") &&
                !dzien.equals("") &&
                !godzina.equals("") &&
                !tydzien.equals("") &&
                !rocznik.equals("")
                ){
            Connection connection = MySql.getInstance().getConnect();
            try {
                String SQL = "SELECT COUNT(*) FROM Zajecia WHERE (rocznik = ? AND dzien = ? AND godzina = ? AND sala = ? AND budynek = ?) AND (tydzien = 0 OR tydzien = ?) FOR UPDATE ";
                PreparedStatement checkRoom = connection.prepareStatement(SQL);
                checkRoom.setString(1,rocznik);
                checkRoom.setInt(2,Zajecie.convertDay(dzien));
                checkRoom.setInt(3,Zajecie.convertHour(godzina));
                checkRoom.setString(4,sala.getSala());
                checkRoom.setString(5,sala.getBudynek());
                checkRoom.setInt(6,Zajecie.convertWeek(tydzien));
                ResultSet checkedRoom = checkRoom.executeQuery();
                int test = 0;
                if(checkedRoom.next()){
                    test = checkedRoom.getInt(1);
                }
                if(test!=0){
                    ErrorField.error("There is already class for that specific time and room");
                    connection.commit();
                    return;
                }
                String SQL2 = "SELECT COUNT(*) FROM Zajecia WHERE (rocznik = ? AND dzien = ? AND godzina = ?) AND (tydzien = 0 OR tydzien = ?) AND (login_prowadzacego = ?)";
                PreparedStatement checkProfessor = connection.prepareStatement(SQL2);
                checkProfessor.setString(1,rocznik);
                checkProfessor.setInt(2,Zajecie.convertDay(dzien));
                checkProfessor.setInt(3,Zajecie.convertHour(godzina));
                checkProfessor.setInt(4,Zajecie.convertWeek(tydzien));
                checkProfessor.setString(5,prowadzacy.getLogin());
                ResultSet checkedProfessor = checkProfessor.executeQuery();
                test = 0;
                if(checkedProfessor.next()){
                    test = checkedProfessor.getInt(1);
                }
                if(test!=0){
                    ErrorField.error("Professor already has classes in that specific time");
                    connection.commit();
                    return;
                }
                
                int id = -1;
                Zajecie newZajecie = new Zajecie(id,rocznik,Zajecie.convertDay(dzien),Zajecie.convertHour(godzina),Zajecie.convertWeek(tydzien),typ,numberClasses,grupa,podGrupa,przedmiot.getNazwa(),prowadzacy.getLogin(),sala.getSala(),sala.getBudynek(),prowadzacy,przedmiot);
                insertSqlObject(newZajecie);
                String SQL3 = "SELECT id FROM Zajecia ORDER BY id DESC LIMIT 1";
                PreparedStatement checkId = connection.prepareStatement(SQL3);
                ResultSet checkedID = checkId.executeQuery();
                if(checkedID.next()){
                    id = checkedID.getInt(1);
                }
                showMessage = false;
                if(id > 0){
                    int days = 7;
                    if(Zajecie.convertWeek(tydzien)!=0){
                        days *= 2;
                    }
                    for (int i = 0; i < numberClasses; i++) {
                        ZaplanowaneZajecie newZaplanowaneZajecie = new ZaplanowaneZajecie(Date.valueOf(date.plusDays(days*i)),id,newZajecie);
                        insertSqlObject(newZaplanowaneZajecie);
                    }
                    for (int i = 0; i < sala.getLiczba_miejsc(); i++) {
                        Miejsce newMiejsce = new Miejsce(-1,id,null);
                        insertSqlObject(newMiejsce);
                    }
                }
            }
            catch (SQLException e){
                ErrorField.error("Failure! Couldnt' create new class!");
            }
        }
        else {
            WarningField.warning("There are fields which are empty!");
        }
        showMessage = true;
        this.setupClasses();
    }


    public void deleteClasses() {
        Zajecie zajecie = (Zajecie) this.classesTable.getSelectionModel().getSelectedItem();
        if (zajecie != null) {
            deleteSqlObject(zajecie);
        }
        this.setupClasses();
    }
}
