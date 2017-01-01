package GUI.AdminMenu;

import GUI.Controller;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

/**
 * Created by lewin on 12/26/16.
 */
public class ControllerScheduleMenu extends Controller{

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Button check;
    @FXML
    private AnchorPane schedule;
//    private StackPane schedule;
    @FXML
    private TabPane scheduleTab;
    @FXML
    public Group subjectBoxes = new Group();
    @FXML
    private Timeline scrollTimeline = new Timeline();
//    @FXML
//    private double scrollDirection = 0.0;
    @FXML
    private ScrollPane scroll;

    private double previousX;
    private double previousY;

//    private StackPane testPane;
    public ControllerScheduleMenu(){

    }
    @FXML
    public void initialize(){
//        this.setMessageLayout(this.mainPane);
//        if(Main.getRefresher()!=null){
//            Main.getRefresher().createStatusLabel(this.mainPane);
//        }
//        this.testPane = new StackPane(this.subjectBoxes);
        this.schedule.getChildren().add(this.subjectBoxes);
        this.setZooming();
        this.setDragging();
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
                event.consume();
                if(event.getDeltaY() == 0){
                    return;
                }
                double scaleFactor = (event.getDeltaY() > 0)
                        ? scaleDelta
                        : 1/scaleDelta;

                Bounds boxesBounds = subjectBoxes.getLayoutBounds();
                final Bounds viewportBounds = scroll.getViewportBounds();

                subjectBoxes.setScaleX(subjectBoxes.getScaleX()*scaleFactor);
                subjectBoxes.setScaleY(subjectBoxes.getScaleY()*scaleFactor);

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
                            scroll.setHvalue(scroll.getHvalue() + 0.02);
                        }
                        else if(deltaX < 0){
                            scroll.setHvalue(scroll.getHvalue() - 0.02);
                        }
                    }

                    double deltaY = previousY - event.getY();
                    if(Math.abs(deltaY) > 20){
                        if(deltaY>0){
                            scroll.setVvalue(scroll.getVvalue() + 0.02);
                        }
                        else if(deltaY < 0){
                            scroll.setVvalue(scroll.getVvalue() - 0.02);
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
        Rectangle rectangle = new Rectangle();
        rectangle.setX(1000);
        rectangle.setY(1000);
        rectangle.setWidth(40);
        rectangle.setHeight(40);
        Rectangle rectangle2 = new Rectangle();
        rectangle2.setX(0);
        rectangle2.setY(0);
        rectangle2.setWidth(40);
        rectangle2.setHeight(40);
        this.subjectBoxes.getChildren().add(rectangle);
        this.subjectBoxes.getChildren().add(rectangle2);
        scroll.layout();
    }

//    public void zooming(ScrollEvent event){
//        final double scaleDelta = 1.1;
//        event.consume();
//        if(event.getDeltaY() == 0){
//            return;
//        }
//        double scaleFactor = (event.getDeltaY() > 0)
//                ? scaleDelta
//                : 1/scaleDelta;
//        this.subjectBoxes.setScaleX(this.subjectBoxes.getScaleX()*scaleFactor);
//        this.subjectBoxes.setScaleY(this.subjectBoxes.getScaleY()*scaleFactor);
//    }
}
