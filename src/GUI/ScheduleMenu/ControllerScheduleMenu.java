package GUI.ScheduleMenu;

import GUI.Controller;
import GUI.MessageMenu.Error.ErrorField;
import dataBase.structure.Wydzial;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lewin on 12/26/16.
 */
public class ControllerScheduleMenu extends Controller{

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


    private ToggleButton oldToggle;

    @FXML
    private ChoiceBox faculty;

    @FXML
    private ChoiceBox course;

    @FXML
    private ChoiceBox semestr;

    @FXML
    private ChoiceBox year;

//    private StackPane testPane;
    public ControllerScheduleMenu(){
    }
    @FXML
    public void initialize(){
        this.schedule.getChildren().add(this.subjectBoxes);
        this.setZooming();
        this.setDragging();
        this.mainSchedule.fire();
        this.setupButtons();
//        this.setMessageLayout(this.mainPane);
//        if(Main.getRefresher()!=null){
//            Main.getRefresher().createStatusLabel(this.mainPane);
//        }
//        this.testPane = new StackPane(this.subjectBoxes);
//        ArrayList<String> test = new ArrayList<>();
//        test.add("aad");
//        test.add("test");
//        this.addChoiceBoxContent(faculty,test);
//        this.addListenerChoiceBox(faculty,course);
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
                            scroll.setHvalue(scroll.getHvalue() + 0.03);
                        }
                        else if(deltaX < 0){
                            scroll.setHvalue(scroll.getHvalue() - 0.03);
                        }
                    }

                    double deltaY = previousY - event.getY();
                    if(Math.abs(deltaY) > 20){
                        if(deltaY>0){
                            scroll.setVvalue(scroll.getVvalue() + 0.03);
                        }
                        else if(deltaY < 0){
                            scroll.setVvalue(scroll.getVvalue() - 0.03);
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
        SubjecBox subjecBox = new SubjecBox(fontSize,100,100,100,38);
        subjecBox.setText("TESTAAAAAAAAAAAAAAAAAAAAAAAa");
        this.subjectBoxes.getChildren().add(subjecBox);
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

    public void setupButtons(){
//        try {
//            List<Wydzial> list = Wydzial.getAllObjects();
//            List<String> listString = new ArrayList<String>();
//            for (Wydzial wydzial : list){
//                listString.add(wydzial.getNazwa());
//            }
//            addChoiceBoxContent(this.faculty,listString);
//        }
//        catch (SQLException e){
//            ErrorField.error("Unable to load faculty from DataBase!");
//        }
    }

}
