package GUI.ScheduleMenu;

import GUI.Controller;
import GUI.MessageMenu.Error.ErrorField;
import GUI.MessageMenu.Info.InfoField;
import GUI.MessageMenu.Warning.WarningField;
import dataBase.MySql;
import dataBase.SqlObject;
import dataBase.actors.Prowadzacy;
import dataBase.structure.Kierunek;
import dataBase.structure.Semestr;
import dataBase.structure.Wydzial;
import dataBase.subjects.Zajecie;
import dataBase.subjects.ZaplanowaneZajecie;
import dataBase.subjects.studentInfo.Miejsce;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.sql.StatementEventListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lewin on 12/26/16.
 */
public class ControllerScheduleMenu extends Controller{

    public List<Zajecie> zajecieList = new ArrayList<>();
    public List<ZaplanowaneZajecie> zaplanowaneZajecieList = new ArrayList<>();
    public double widthSubjectBox = 160.0;
    public double heightSubjectBox = 35.0;

    public List<TextArea> groupList = new ArrayList<>();

    public Zajecie currentZajecie;
    public ZaplanowaneZajecie currentZaplanowaneZajecie;
    @FXML
    private Label name;
    @FXML
    private Label professor;
    @FXML
    private Label time;
    @FXML
    private Label week;
    @FXML
    private Label group;
    @FXML
    private Label room;
    @FXML
    private Label spots;
    @FXML
    private ChoiceBox<ZaplanowaneZajecie> zajecieChoiceBox;

    @FXML
    private TableView<Miejsce> miejscaTableView;

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Button checkMain;
    @FXML
    private Button checkDate;

    @FXML
    private AnchorPane schedule;
//    private StackPane schedule;
    @FXML
    private TabPane scheduleTab;
    @FXML
    public Group subjectBoxes = new Group();
//    @FXML
//    private Timeline scrollTimeline = new Timeline();
//    @FXML
//    private double scrollDirection = 0.0;
    @FXML
    private ScrollPane scroll;

    @FXML
    private ToggleButton mainSchedule;
    @FXML
    private ToggleButton dateSchedule;
    @FXML
    private AnchorPane datePane;

    private double previousX;
    private double previousY;

    private DoubleProperty fontSize = new SimpleDoubleProperty(5);
    private DoubleProperty daySize = new SimpleDoubleProperty(37);
    private DoubleProperty hourSize = new SimpleDoubleProperty(9);
    private DoubleProperty groupSize = new SimpleDoubleProperty(16);


    private ToggleButton oldToggle;

    @FXML
    private ChoiceBox<SqlObject> faculty;

    @FXML
    private ChoiceBox<SqlObject> course;

    @FXML
    private ChoiceBox<SqlObject> semestr;

    @FXML
    private ChoiceBox<String> year;

    @FXML
    private Button absentButton;
    @FXML
    private Button unknownButton;
    @FXML
    private Button presentButton;
    @FXML
    private Button takeSpotButton;
    @FXML
    private Button leaveSpotButton;
//    private StackPane testPane;
    public ControllerScheduleMenu(){
    }
    @FXML
    public void initialize(){
        if(MySql.getInstance().getStudent()==null){
            this.takeSpotButton.setVisible(false);
            this.leaveSpotButton.setVisible(false);
        }
        if(MySql.getInstance().getProwadzacy()==null){
            this.absentButton.setVisible(false);
            this.unknownButton.setVisible(false);
            this.presentButton.setVisible(false);
        }
        this.schedule.getChildren().add(this.subjectBoxes);
        this.setZooming();
        this.setDragging();
        this.mainSchedule.fire();
        this.setMessageLayout(this.mainPane);
//        if(Main.getRefresher()!=null){
//            Main.getRefresher().createStatusLabel(this.mainPane);
//        }
//        this.testPane = new StackPane(this.subjectBoxes);
//        ArrayList<String> test = new ArrayList<>();
//        test.add("aad");
//        test.add("test");
//        this.addChoiceBoxContent(faculty,test);
//        this.addListenerChoiceBox(faculty,course);
        List<String> years = new ArrayList<>();
        years.add("2016/2017");
        try {
            this.addChoiceBoxContent(this.faculty,Wydzial.getAllObjects());
        }
        catch (SQLException e){
            ErrorField.error("Setup failure!");
            e.printStackTrace();
        }
        this.addChoiceBoxContentString(this.year,years);
        this.addListenerChoiceBox(this.faculty,this.course);
        this.addListenerChoiceBox(this.course,this.semestr);

        this.addChoiceBoxContentZajecia(this.zajecieChoiceBox,this.zaplanowaneZajecieList);
        this.zajecieChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != oldValue && newValue != null){
                this.currentZaplanowaneZajecie = (ZaplanowaneZajecie) this.zajecieChoiceBox.getSelectionModel().getSelectedItem();
                this.miejscaTableView.refresh();
            }
        });
        TableColumn<Miejsce,String> student = new TableColumn("Student");
        TableColumn<Miejsce,String> obecnosc= new TableColumn("Attendance");
        obecnosc.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getAttendance(this.currentZaplanowaneZajecie)));
        student.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getStudentName()));
        this.miejscaTableView.getColumns().addAll(student,obecnosc);

    }

    private void setZooming(){
        scroll.viewportBoundsProperty().addListener(((observable, oldValue, newValue) -> {
//            schedule.setPrefSize(newValue.getWidth(),newValue.getHeight());
            schedule.setMinSize(newValue.getWidth(),newValue.getHeight());
        }));

        schedule.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                final double scaleDelta = 1.1;
                final double scaleFont = 1.01;
                event.consume();
                if(event.getDeltaY() == 0){
                    return;
                }
                double scaleFactor;
                double scaleFactorFont;
                if (event.getDeltaY() > 0) {
                    scaleFactor = scaleDelta;
                    scaleFactorFont = scaleFont;
                }
                else {
                    scaleFactor = 1 / scaleDelta;
                    scaleFactorFont = 1/ scaleFont;
                }
                subjectBoxes.setScaleX(subjectBoxes.getScaleX()*scaleFactor);
                subjectBoxes.setScaleY(subjectBoxes.getScaleY()*scaleFactor);
                double size = fontSize.getValue();
//                if((size <= 16 && size >= 3)){
//                    System.out.println("HEJ");
                    fontSize.setValue(fontSize.getValue()*scaleFactorFont);
//                }
                scroll.layout();

            }
        });
//        schedule.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
//            @Override
//            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
//                schedule.setClip(new Rectangle(newValue.getMinX(),newValue.getMinY(),newValue.getWidth(),newValue.getHeight()));
//            }
//        });
    }

    private void setDragging(){
        this.schedule.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY){
                    double deltaX = previousX - event.getX();
                    if(Math.abs(deltaX) > 20){
                        if(deltaX > 0){
                            scroll.setHvalue(scroll.getHvalue() + 0.01);
                        }
                        else if(deltaX < 0){
                            scroll.setHvalue(scroll.getHvalue() - 0.01);
                        }
                    }

                    double deltaY = previousY - event.getY();
                    if(Math.abs(deltaY) > 20){
                        if(deltaY>0){
                            scroll.setVvalue(scroll.getVvalue() + 0.01);
                        }
                        else if(deltaY < 0){
                            scroll.setVvalue(scroll.getVvalue() - 0.01);
                        }
                    }
                }
            }
        });
        this.schedule.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                previousX = event.getX();
                previousY = event.getY();
            }
        });
    }


    @FXML
    public void checkSchedule(ActionEvent actioEvent){
        actioEvent.consume();
//        Rectangle rectangle = new Rectangle();
//        rectangle.setX(1000);
//        rectangle.setY(1000);
//        rectangle.setWidth(40);
//        rectangle.setHeight(40);
//        Rectangle rectangle2 = new Rectangle();
//        rectangle2.setX(0);
//        rectangle2.setY(0);
//        rectangle2.setWidth(40);
//        rectangle2.setHeight(40);
//        this.subjectBoxes.getChildren().add(rectangle);
//        this.subjectBoxes.getChildren().add(rectangle2);
//
        Wydzial wydzial = (Wydzial) this.faculty.getSelectionModel().getSelectedItem();
        Kierunek kierunek = (Kierunek) this.course.getSelectionModel().getSelectedItem();
        Semestr sem = (Semestr) this.semestr.getSelectionModel().getSelectedItem();
        String rok = this.year.getSelectionModel().getSelectedItem();
        if(!this.faculty.getSelectionModel().isEmpty() && !this.course.getSelectionModel().isEmpty() && !this.semestr.getSelectionModel().isEmpty() && !this.year.getSelectionModel().isEmpty()){
            String SQL = "SELECT * FROM Zajecia z JOIN UzytkownicyView u JOIN Przedmioty p WHERE z.login_prowadzacego = u.login AND z.przedmiot = p.nazwa AND rocznik = ? AND p.wydzial = ? AND p.kierunek = ? AND p.semestr = ?";
            Connection connection = MySql.getInstance().getConnect();
            try {
                PreparedStatement stmt = connection.prepareStatement(SQL);
                stmt.setString(1,rok);
                stmt.setString(2,kierunek.getNazwa_wydzialu());
                stmt.setString(3,kierunek.getNazwa());
                stmt.setInt(4,sem.getNumer());
                ResultSet rs = stmt.executeQuery();
                if(!zajecieList.isEmpty()){
                    for (Zajecie z:
                         zajecieList) {
                        z.delete();
                    }
                }
                zajecieList.clear();
                while (rs.next()){
                    Zajecie zajecie = Zajecie.setValuesFromRS(rs,0);
                    SubjecBox subjecBox = this.calculateSubjectBox(zajecie);
                    this.subjectBoxes.getChildren().add(subjecBox);
                    zajecieList.add(zajecie);
                }
                this.setupSchedule();
            }
            catch (SQLException e){
                ErrorField.error("Failure while looking for classes");
                e.printStackTrace();
            }
        }
        else {
            WarningField.warning("There are some fields which are empty!");
        }
        scroll.layout();
    }

    public void actionMainSchedule(ActionEvent event){
        event.consume();
        oldToggle = this.toggleHighlight(event,this.oldToggle);
        this.showMain();
    }



    public void actionDateSchedule(ActionEvent event){
        event.consume();
        oldToggle = this.toggleHighlight(event,this.oldToggle);
        this.showDate();
    }



    public void showMain(){
        datePane.setVisible(false);
        datePane.setManaged(false);
        checkDate.setVisible(false);
        checkMain.setVisible(true);
    }


    public void showDate(){
        datePane.setVisible(true);
        datePane.setManaged(true);
        checkDate.setVisible(true);
        checkMain.setVisible(false);
    }

    public SubjecBox calculateSubjectBox(Zajecie zajecie){
        double width = this.widthSubjectBox;
        int grupa = zajecie.getGrupa();
        int podgrupa = zajecie.getPodgrupa();
        if(grupa == 0){
            width = this.widthSubjectBox*5;
        }
        else {
            if(podgrupa !=0){
                width /= 2.0;
            }
        }
        double height = this.heightSubjectBox;
        double x = 0.0;
        if(grupa == 0){
            x = this.widthSubjectBox/2.0+this.heightSubjectBox;
        }
        else {
            x = this.widthSubjectBox/2.0+this.widthSubjectBox*(grupa-1)+this.heightSubjectBox;
            if(podgrupa == 2){
                x += this.widthSubjectBox/2.0;
            }
        }
        double y = this.heightSubjectBox*zajecie.getGodzina();
        int dzien = zajecie.getDzien();
        y += this.heightSubjectBox*9*dzien;
        SubjecBox subjectBox = new SubjecBox(this.fontSize,x,y,width,height,zajecie);
        Rectangle rectangle = new Rectangle(x,y,width,height);
        rectangle.setFill(zajecie.getSubject().getColor());
        this.subjectBoxes.getChildren().add(rectangle);
        subjectBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    if (subjectBox.getZajecie()!=null){
                        chosenClass(subjectBox.getZajecie());
                        currentZajecie = subjectBox.getZajecie();
                        currentZaplanowaneZajecie = null;
                        miejscaTableView.setItems(null);
                        miejscaTableView.refresh();
                    }

                    currentZajecie.findSpots();
                    spots.setText(Integer.toString(currentZajecie.getMiejsca().size()));
                    ObservableList<Miejsce> data = FXCollections.observableArrayList(currentZajecie.getMiejsca());
                    miejscaTableView.setItems(data);
                }
            }
        });
//        subjectBox.setStyle("text-area-background: "+ "#8a6d3b" +";");

//        subjectBox.setStyle("text-area-background: #8a6d3b" + ";");
//        String s = ".text-area .scroll-pane .content{\n" +
//                   "-fx-background-color: black;\n" + "}";
//        subjectBox.setStyle(s);

        return subjectBox;
    }

    public void setupSchedule(){
        for (int i = 1; i < 6; i++) {
            TextArea groupField = new TextArea();
            double x = this.widthSubjectBox/2.0+this.widthSubjectBox*(i-1)+this.heightSubjectBox;
            double y = 0.0;
            double width = this.widthSubjectBox;
            double height = this.heightSubjectBox;
            groupField.setLayoutX(x);
            groupField.setLayoutY(y);
            groupField.setPrefSize(width,height);
            groupField.styleProperty().bind(Bindings.concat("-fx-font-size: ",groupSize.asString(),";"));
            groupField.setEditable(false);
            groupField.setText("Group " + Integer.toString(i));
            this.subjectBoxes.getChildren().addAll(groupField);
        }
        for (int i = 0; i < 5; i++) {
            TextArea dayField = new TextArea();
            double x = 0.0;
            double y = this.heightSubjectBox + this.heightSubjectBox*9.0*i;
            double width = this.heightSubjectBox*9.0;
            double height = this.heightSubjectBox;
            dayField.setRotate(270.0);
//            textField.setLayoutX(x);
            dayField.setLayoutX(x-(width-height));
            dayField.setLayoutY(y);
//            textField.setLayoutY(y+(width/2-height));
            dayField.setPrefSize(width,height);
            dayField.setMaxSize(width,height);
            dayField.styleProperty().bind(Bindings.concat("-fx-font-size: ",daySize.asString(),";"));
            dayField.setEditable(false);
            dayField.setText(Zajecie.convertDay(i));
            dayField.setTranslateX(this.heightSubjectBox*3.0);
            this.subjectBoxes.getChildren().add(dayField);
//            dayField.setMaxSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
//            dayField.setMinSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);

//            Rectangle rectangle = new Rectangle(x,y,height,width);
//            rectangle.setFill(Color.RED);
//            this.subjectBoxes.getChildren().add(rectangle);
            for (int j = 1; j < 9; j++) {
                TextArea hourField = new TextArea();
                double x2 = this.heightSubjectBox;
                double y2 = this.heightSubjectBox*j+this.heightSubjectBox*9.0*i;
                double width2 = this.widthSubjectBox/2;
                double height2 = this.heightSubjectBox;
                hourField.setLayoutX(x2);
                hourField.setLayoutY(y2);
                hourField.setPrefSize(width2,height2);
//                hourField.setMaxSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
//                hourField.setMinSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
                hourField.styleProperty().bind(Bindings.concat("-fx-font-size: ",hourSize.asString(),";"));
                hourField.setEditable(false);
                hourField.setText(Zajecie.convertHour(j));
                this.subjectBoxes.getChildren().add(hourField);
            }
        }

    }
    public void chosenClass(Zajecie zajecie){
        this.name.setText(zajecie.getPrzedmiot());
        this.professor.setText(zajecie.getProfessor().toString());
        this.time.setText(Zajecie.convertDay(zajecie.getDzien())+ " " + Zajecie.convertHour(zajecie.getGodzina()));
        this.week.setText(Zajecie.convertWeek(zajecie.getTydzien()));
        this.group.setText("Group: " + Integer.toString(zajecie.getGrupa()) + " subgroup: " + Integer.toString(zajecie.getPodgrupa()));
        this.room.setText(zajecie.getSala() + " " + zajecie.getBudynek());
        try {
            if(!this.zaplanowaneZajecieList.isEmpty()){
                for (ZaplanowaneZajecie z:
                     zaplanowaneZajecieList) {
                    z.delete();
                }
            }
            this.zaplanowaneZajecieList.clear();
            zaplanowaneZajecieList = ZaplanowaneZajecie.getAllObjects(zajecie);
            this.addChoiceBoxContentZajecia(this.zajecieChoiceBox,this.zaplanowaneZajecieList);
        }
        catch (SQLException e){
            ErrorField.error("Failure while loading class");
            e.printStackTrace();
        }
    }

    public void takeNewSpot(){
        takeFreeSpot();
    }

    private void takeFreeSpot() {
        if(this.currentZajecie==null){
            ErrorField.error("Please choose class first");
            return;
        }
        Connection connection = MySql.getInstance().getConnect();
        try {
            String SQL = "SELECT COUNT(*) FROM Miejsca WHERE student IS NULL AND Miejsca.id_zajecia = ? FOR UPDATE";
            PreparedStatement stmt = connection.prepareStatement(SQL);
            stmt.setInt(1,this.currentZajecie.getId());
            ResultSet rs = stmt.executeQuery();
            int test = -1;
            if(rs.next()){
                test = rs.getInt(1);
            }
            if(test < 1){
                ErrorField.error("There are no empty spots");
                connection.rollback();
                return;
            }
            test = 1;
            SQL = "SELECT COUNT(*) FROM Miejsca WHERE id_zajecia = ? AND student = (SELECT userName())";
            stmt = connection.prepareStatement(SQL);
            stmt.setInt(1,this.currentZajecie.getId());
            rs = stmt.executeQuery();
            if(rs.next()){
                test = rs.getInt(1);
            }
            if(test > 0){
                ErrorField.error("You already have spot in this class");
                connection.rollback();
                return;
            }
            SQL = "SELECT takeSpot(?)";
            stmt = connection.prepareStatement(SQL);
            stmt.setInt(1,this.currentZajecie.getId());
            rs = stmt.executeQuery();
            boolean check = false;
            if(rs.next()){
                check = rs.getBoolean(1);
            }
            if(check){
                connection.commit();
                InfoField.info("Getting spot was successful");
            }
            else {
                ErrorField.error("You are not a student!");
                connection.rollback();
            }

        }
        catch (SQLException e){
            ErrorField.error("Failure while getting spot");
            e.printStackTrace();
            try {
                connection.rollback();
            }
            catch (SQLException err){
                err.printStackTrace();
            }
        }
        finally {
            currentZajecie.findSpots();
            ObservableList<Miejsce> data = FXCollections.observableArrayList(currentZajecie.getMiejsca());
            miejscaTableView.setItems(data);
            miejscaTableView.refresh();
        }
    }

    public void leaveNewSpot(){
        if(this.currentZajecie==null){
            ErrorField.error("Please choose class first");
            return;
        }
        Connection connection = MySql.getInstance().getConnect();
        try {
            String SQL = "SELECT  COUNT(*) FROM Miejsca WHERE id_zajecia = ? AND student = (SELECT userName()) FOR UPDATE ";
            PreparedStatement stmt = connection.prepareStatement(SQL);
            stmt.setInt(1,this.currentZajecie.getId());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                if(rs.getInt(1)==0){
                    ErrorField.error("You do not have spot in this class");
                    connection.rollback();
                    return;
                }
            }
            SQL = "SELECT leaveSpot(?)";
            stmt = connection.prepareStatement(SQL);
            stmt.setInt(1,this.currentZajecie.getId());
            rs = stmt.executeQuery();
            boolean check = false;
            if(rs.next()){
                check = rs.getBoolean(1);
            }
            if(check){
                connection.commit();
                InfoField.info("Leaving spot was successful");
            }
            else {
                connection.rollback();
                ErrorField.error("You do not have spot in this class");
            }
        }
        catch (SQLException e){
            ErrorField.error("Failure while leaving spot");
            try {
                connection.rollback();
            }
            catch (SQLException err){
                err.printStackTrace();
            }
            e.printStackTrace();

        }
        finally {
            currentZajecie.findSpots();
            ObservableList<Miejsce> data = FXCollections.observableArrayList(currentZajecie.getMiejsca());
            miejscaTableView.setItems(data);
            miejscaTableView.refresh();
        }
    }

    public void presentAttendance(){
        this.changeAttendance("Present");
    }

    public void unknownAttendance(){
        this.changeAttendance("Unknown");
    }
    public void absentAttendance(){
        this.changeAttendance("Absent");
    }

    public void changeAttendance(String type){
        if(this.currentZaplanowaneZajecie!=null && this.currentZajecie !=null){
            if(this.miejscaTableView.getSelectionModel().isEmpty()){
               WarningField.warning("Please choose student");
                return;
            }
            Connection connection = MySql.getInstance().getConnect();
            try {
                String SQL = "SELECT * FROM Obecnosci WHERE id_zajecia = ? AND data = ? FOR UPDATE";
                PreparedStatement stmt = connection.prepareStatement(SQL);
                stmt.setInt(1,this.currentZajecie.getId());
                stmt.setDate(2,this.currentZaplanowaneZajecie.getData());
                stmt.execute();
                String student = this.miejscaTableView.getSelectionModel().getSelectedItem().getStudent();
                if(student == null){
                    connection.rollback();
                    return;
                }
                SQL = "UPDATE Obecnosci SET typ = ? WHERE id_zajecia = ? AND data = ? AND Obecnosci.student = ?";
                stmt = connection.prepareStatement(SQL);
                stmt.setString(1,type);
                stmt.setInt(2,this.currentZajecie.getId());
                stmt.setDate(3,this.currentZaplanowaneZajecie.getData());
                stmt.setString(4,student);

                stmt.execute();
                connection.commit();

                System.out.println("Attendance");
            }
            catch (SQLException e){
                try {
                    connection.rollback();
                }
                catch (SQLException err){
                    err.printStackTrace();
                }
                ErrorField.error("Failure while changing attendance");
                e.printStackTrace();

            }
        }
        else{
            WarningField.warning("Please first choose specific date");
        }
        currentZajecie.findSpots();
        ObservableList<Miejsce> data = FXCollections.observableArrayList(currentZajecie.getMiejsca());
        miejscaTableView.setItems(data);
        miejscaTableView.refresh();
    }
}
